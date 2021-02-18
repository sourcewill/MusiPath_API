package br.uem.crawlerlast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uem.crawlerlast.modelo.Artista;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, String> {
	
	Artista findByMbid(String mbid);
	
	Artista findByNome(String nome);	

}
