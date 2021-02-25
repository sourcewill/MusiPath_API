package br.uem.crawlerlast.controle;

import java.util.List;

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
	
	public List<Album> buscarTodosAlbuns() {
		return albumService.buscarTodosAlbuns();
	}
	
	public Album associarMusica(Album album, Musica musica) {
		musica.setAlbum(album);
		album.getMusicas().add(musica);
		return criar(album);
	}
	
	public void deletarAlbumPorMbid(String mbid) {
		albumService.deletarAlbumPorMbid(mbid);
	}
	
	public void deletarAlbunsVazios() {
		List<Album> albuns = buscarTodosAlbuns();	
		for(Album album : albuns) {			
			if(album.getMusicas().size() == 0) {
				String mbid = album.getMbid();
				deletarAlbumPorMbid(mbid);
			}
		}
	}

}
