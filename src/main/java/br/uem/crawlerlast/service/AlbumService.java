package br.uem.crawlerlast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.uem.crawlerlast.modelo.Album;
import br.uem.crawlerlast.repository.AlbumRepository;

@Service
public class AlbumService {
	
	@Autowired
	private AlbumRepository albumRepository;
	
	public Album criar(Album album) {
		return albumRepository.save(album);
	}
	
	public Album buscaPorMbid(String mbid) {
		return albumRepository.findByMbid(mbid);
	}

}
