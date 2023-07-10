package pl.nethos.rekrutacja.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nethos.rekrutacja.model.Kontrahent;

import java.util.List;

@Service
@Transactional
public class KontrahentRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Kontrahent> all() {
        return em.createQuery("SELECT k FROM Kontrahent k", Kontrahent.class).getResultList();
    }

    public String findNipByKontrahentId(Long kontrahentId) {
        return em.createQuery("SELECT nip from Kontrahent where id = :kontrahentId", String.class)
                .setParameter("kontrahentId", kontrahentId)
                .getSingleResult();
    }
}
