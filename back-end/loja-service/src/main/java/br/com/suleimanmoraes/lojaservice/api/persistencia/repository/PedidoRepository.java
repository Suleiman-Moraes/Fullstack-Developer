package br.com.suleimanmoraes.lojaservice.api.persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.suleimanmoraes.lojaservice.api.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
