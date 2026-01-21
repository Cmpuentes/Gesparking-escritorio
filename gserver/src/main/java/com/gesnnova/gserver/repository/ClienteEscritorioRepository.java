package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteEscritorioRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByPlaca(String placa);

    @Query("SELECT DISTINCT c.cliente FROM Cliente c")
    List<String> obtenerClientesUnicos();

    List<Cliente> findAllByOrderByIdclienteDesc();
}
