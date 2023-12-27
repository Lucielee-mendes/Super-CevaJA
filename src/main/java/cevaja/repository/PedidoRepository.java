package cevaja.repository;

import cevaja.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  PedidoRepository extends JpaRepository<Pedido, Long> {
}
