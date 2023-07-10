package pl.nethos.rekrutacja.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class KontoBankowe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "konto_bankowe_seq")
    @SequenceGenerator(name = "konto_bankowe_seq", sequenceName = "konto_bankowe_seq", allocationSize = 1)

    private Long id;

    private Long idKontrahent;

    private String numer;

    private Integer aktywne;

    private Integer domyslne;

    private Integer wirtualne;

    private Integer stanWeryfikacji;

    private Timestamp dataWeryfikacji;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdKontrahent() {
        return idKontrahent;
    }

    public void setIdKontrahent(Long idKontrahent) {
        this.idKontrahent = idKontrahent;
    }

    public String getNumer() {
        return numer;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public Integer getAktywne() {
        return aktywne;
    }

    public void setAktywne(Integer aktywne) {
        this.aktywne = aktywne;
    }

    public Integer getDomyslne() {
        return domyslne;
    }

    public void setDomyslne(Integer domyslne) {
        this.domyslne = domyslne;
    }

    public Integer getWirtualne() {
        return wirtualne;
    }

    public void setWirtualne(Integer wirtualne) {
        this.wirtualne = wirtualne;
    }

    public Integer getStanWeryfikacji() {
        if (stanWeryfikacji==null){
            stanWeryfikacji = 0;
        }
        return stanWeryfikacji;
    }

    public void setStanWeryfikacji(Integer stanWeryfikacji) {
        this.stanWeryfikacji = stanWeryfikacji;
    }


    public void setDataWeryfikacji(Timestamp dataWeryfikacji) {
        this.dataWeryfikacji = dataWeryfikacji;
    }

}
