package br.uem.musipathapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uem.musipathapi.modelo.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String>{
	
	Album findByMbid(String mbid);	

}
