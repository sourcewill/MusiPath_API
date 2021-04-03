package br.uem.musipathapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uem.musipathapi.modelo.Aresta;


@Repository
public interface ArestaRepository extends JpaRepository<Aresta, String> {

}
