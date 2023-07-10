package pl.nethos.rekrutacja.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Kontrahent {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kontrahent_gen")
	@SequenceGenerator(name = "kontrahent_gen", sequenceName = "kontrahent_seq", allocationSize = 1)
	private Long id;

	private String nazwa;

	private String nip;

	public Long getId() {
		return id;
	}

	public String getNazwa() {
		return nazwa;
	}

	public String getNip() {
		return nip;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

}
