package pl.nethos.rekrutacja.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.nethos.rekrutacja.model.KontoBankowe;
import pl.nethos.rekrutacja.repository.KontoBankoweRepository;
import pl.nethos.rekrutacja.repository.KontoBankoweRepositoryImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.nethos.rekrutacja.repository.KontrahentRepository;
import pl.nethos.rekrutacja.vatApi.VatApiResponse;


import java.sql.Timestamp;
import java.util.List;

@PageTitle("Konta Bankowe")
@Route("konta-bankowe")
public class KontoBankoweView extends VerticalLayout implements HasUrlParameter<String> {

    private final KontoBankoweRepositoryImpl kontoBankoweRepositoryImpl;
    private final KontrahentRepository kontrahentRepository;
    private List<KontoBankowe> kontoBankoweList;
    private String nip;

    public KontoBankoweView(@Autowired KontoBankoweRepositoryImpl kontoBankoweRepositoryImpl, KontrahentRepository kontrahentRepository) {
        this.kontoBankoweRepositoryImpl = kontoBankoweRepositoryImpl;
        this.kontrahentRepository = kontrahentRepository;
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            Long kontrahentId = Long.parseLong(parameter);
            kontoBankoweList = kontoBankoweRepositoryImpl.findByKontrahentId(kontrahentId);
            if (kontoBankoweList.isEmpty()) {
                Notification.show("Brak kont bankowych dla kontrahenta o podanym ID");
            } else {
                initializeGrid();
            }
        } else {
            Notification.show("Nieprawidłowe parametry widoku KontoBankoweView");
        }
    }

    private void initializeGrid() {

        Grid<KontoBankowe> grid = new Grid<>();
        grid.setItems(kontoBankoweList);

        grid.getStyle().set("text-align", "center");
        grid.addColumn(KontoBankowe::getNumer).setHeader("Numer").setWidth("275px");
        grid.addColumn(KontoBankowe::getAktywne).setHeader("Aktywne").setWidth("80px");
        grid.addColumn(KontoBankowe::getDomyslne).setHeader("Domyślne").setWidth("80px");
        grid.addColumn(KontoBankowe::getWirtualne).setHeader("Wirtualne").setWidth("80px");

        add(grid);

        grid.addComponentColumn(this::createButton).setHeader("Stan").setWidth("120px");

        add(grid);
    }

    private Button createButton(KontoBankowe kontoBankowe) {
        Button button = new Button(getStanLabel(kontoBankowe.getStanWeryfikacji()));
        button.addClickListener(event -> handleButtonClick(kontoBankowe));
        button.getElement().setAttribute("title", getStanTooltip(kontoBankowe.getStanWeryfikacji()));
        return button;
    }

    private String getStanLabel(Integer stanWeryfikacji) {
        switch (stanWeryfikacji) {
            case 1:
                return "Błędne konto";
            case 2:
                return "Zweryfikowany";
            default:
                return "Nieokreślony";
        }
    }

    private Integer convStanWeryfikacji(String stanWeryfikacji){
        switch (stanWeryfikacji){
            case "NIE":
                return 1;
            case "TAK":
                return 2;
            default:
                return 0;
        }
    }

    private String getStanTooltip(Integer stanWeryfikacji) {
        switch (stanWeryfikacji) {
            case 1:
                return "Stan: Błędne konto\nKliknij, aby sprawdzić";
            case 2:
                return "Stan: Zweryfikowany\nKliknij, aby sprawdzić";
            default:
                return "Stan: Nieokreślony\nKliknij, aby sprawdzić";
        }
    }

    @Autowired
    private KontoBankoweRepository kontoBankoweRepository;
    @Transactional
    public void saveKontoBankowe(KontoBankowe kontoBankowe) {
        kontoBankoweRepository.save(kontoBankowe);
        UI.getCurrent().navigate("konta-bankowe/" + kontoBankowe.getIdKontrahent());
    }



    private void handleButtonClick(KontoBankowe kontoBankowe) {
        String apiUrl = "https://wl-test.mf.gov.pl/api/check/nip/{nip}/bank-account/{bankAccount}";
        String nip = kontrahentRepository.findNipByKontrahentId(kontoBankowe.getIdKontrahent());
        String bankAccount = kontoBankowe.getNumer();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<VatApiResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                VatApiResponse.class,
                nip,
                bankAccount
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            VatApiResponse vatApiResponse = response.getBody();
            VatApiResponse.Result result = vatApiResponse.getResult();
            if (vatApiResponse != null) {
                String accountAssigned = result.getAccountAssigned();
                long currentTimeMillis = System.currentTimeMillis();
                Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
                currentTimestamp.setNanos(0);
                Integer checkStanWeryfikacji=convStanWeryfikacji(accountAssigned);
                kontoBankowe.setStanWeryfikacji(checkStanWeryfikacji);
                kontoBankowe.setDataWeryfikacji(currentTimestamp);


                saveKontoBankowe(kontoBankowe);
            } else {
                Notification.show("Błąd podczas komunikacji z API");
            }
        }
    }
}

