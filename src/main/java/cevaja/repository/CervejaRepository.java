package cevaja.repository;

import cevaja.model.TipoCerveja;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CervejaRepository extends JpaRepository<TipoCerveja, Long> {

   public TipoCerveja findByNome(String nome);

}
