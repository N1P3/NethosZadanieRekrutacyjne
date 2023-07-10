package pl.nethos.rekrutacja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nethos.rekrutacja.model.KontoBankowe;

public interface KontoBankoweRepository extends JpaRepository<KontoBankowe, Long> {
}
