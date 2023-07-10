package pl.nethos.rekrutacja.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nethos.rekrutacja.model.KontoBankowe;

import java.util.List;

@Service
@Transactional
public class KontoBankoweRepositoryImpl {
    @PersistenceContext
    private EntityManager em;

    public List<KontoBankowe> findByKontrahentId(Long kontrahentId) {
        return em.createQuery("SELECT kb FROM KontoBankowe kb WHERE kb.idKontrahent = :kontrahentId", KontoBankowe.class)
                .setParameter("kontrahentId", kontrahentId)
                .getResultList();
    }
}