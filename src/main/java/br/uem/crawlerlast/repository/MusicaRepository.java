package br.uem.crawlerlast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uem.crawlerlast.modelo.Musica;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, String> {

	Musica findByMbid(String mbid);

}
