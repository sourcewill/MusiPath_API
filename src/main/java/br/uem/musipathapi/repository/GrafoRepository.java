package br.uem.musipathapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uem.musipathapi.modelo.Grafo;

@Repository
public interface GrafoRepository extends JpaRepository<Grafo, String> {

}
