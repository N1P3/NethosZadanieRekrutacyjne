package pl.nethos.rekrutacja.views;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nethos.rekrutacja.model.Kontrahent;
import pl.nethos.rekrutacja.repository.KontrahentRepository;
import pl.nethos.rekrutacja.repository.KontoBankoweRepositoryImpl;

import java.util.List;

@PageTitle("Zadanie")
@Route("")
public class KontrahentView extends VerticalLayout {

    private final KontrahentRepository kontrahentRepository;
    private final KontoBankoweRepositoryImpl kontoBankoweRepository;

    public KontrahentView(@Autowired KontrahentRepository kontrahentRepository,
                          @Autowired KontoBankoweRepositoryImpl kontoBankoweRepositoryImpl) {
        this.kontrahentRepository = kontrahentRepository;
        this.kontoBankoweRepository = kontoBankoweRepositoryImpl;

        setSpacing(false);

        List<Kontrahent> kontrahentList = kontrahentRepository.all();

        Grid<Kontrahent> grid = new Grid<>();
        grid.setItems(kontrahentList);

        grid.getStyle().set("text-align", "center");
        grid.addColumn(Kontrahent::getNazwa).setHeader("Nazwa");
        grid.addColumn(Kontrahent::getNip).setHeader("NIP");

        grid.addItemClickListener(event -> {
            Kontrahent kontrahent = event.getItem();
            UI.getCurrent().navigate("konta-bankowe/" + kontrahent.getId());
        });

        add(grid);
    }
}
