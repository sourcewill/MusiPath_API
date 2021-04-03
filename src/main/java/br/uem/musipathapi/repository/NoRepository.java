package br.uem.musipathapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uem.musipathapi.modelo.No;

@Repository
public interface NoRepository extends JpaRepository<No, String> {

}
