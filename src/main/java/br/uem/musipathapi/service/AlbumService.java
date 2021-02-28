package br.uem.musipathapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.uem.musipathapi.modelo.Album;
import br.uem.musipathapi.repository.AlbumRepository;

@Service
public class AlbumService {
	
	@Autowired
	private AlbumRepository albumRepository;
	
	public Album buscaPorMbid(String mbid) {
		return albumRepository.findByMbid(mbid);
	}
	
	public List<Album> buscarTodosAlbuns() {
		return albumRepository.findAll();
	}
	
	public void deletarAlbumPorMbid(String mbid) {
		albumRepository.deleteById(mbid);
	}

}
