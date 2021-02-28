package br.uem.musipathapi.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.uem.musipathapi.modelo.Album;
import br.uem.musipathapi.service.AlbumService;


@CrossOrigin
@RestController
@RequestMapping("api/albuns/")
public class AlbumControle {
	
	@Autowired
	private AlbumService albumService;
	
	@GetMapping("buscarpormbid/{mbid}")
	public Album buscarPorMbid(@PathVariable(value = "mbid") String mbid) {
		Album album = albumService.buscaPorMbid(mbid);
		if(album == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		return album;
	}
	
	@GetMapping("buscartodos")
	public List<Album> buscarTodosAlbuns() {
		return albumService.buscarTodosAlbuns();
	}
	
	
}
