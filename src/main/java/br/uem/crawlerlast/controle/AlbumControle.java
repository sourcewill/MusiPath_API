package br.uem.crawlerlast.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.uem.crawlerlast.modelo.Album;
import br.uem.crawlerlast.modelo.Musica;
import br.uem.crawlerlast.service.AlbumService;

@Controller
public class AlbumControle {
	
	@Autowired
	private AlbumService albumService;
	
	public Album criar(Album album) {
		return albumService.criar(album);
	}
	
	public Album buscarPorMbid(String mbid) {
		return albumService.buscaPorMbid(mbid);
	}
	
	public Album associarMusica(Album album, Musica musica) {
		musica.setAlbum(album);
		album.getMusicas().add(musica);
		return criar(album);
	}

}
