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
import br.uem.musipathapi.modelo.Artista;
import br.uem.musipathapi.service.AlbumService;
import br.uem.musipathapi.service.ArtistaService;

@CrossOrigin
@RestController
@RequestMapping("api/artistas/")
public class ArtistaControle {

	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private AlbumService albumService;
	
	@GetMapping("buscartodos")
	public List<Artista> buscarTodosArtistas() {
		return artistaService.buscarTodosArtistas();
	}
	
	@GetMapping("buscarpormbid/{mbid}")
	public Artista buscarPorMbid(@PathVariable(value = "mbid") String mbid) {
		Artista artista = artistaService.buscaPorMbid(mbid);
		if(artista == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		return artista;
	}
	
	@GetMapping("buscarpornome/{nome}")
	public Artista buscarPorNome(@PathVariable(value = "nome") String nome) {
		Artista artista = artistaService.buscaPorNome(nome);
		if(artista == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		return artista;
	}
	
	@GetMapping("buscarporalbummbid/{mbid}")
	public Artista getArtistaByAlbumMbid(@PathVariable(value = "mbid") String mbid) {
		Album album = albumService.buscaPorMbid(mbid);
		if(album == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		return artistaService.buscaPorMbid(album.getArtista().getMbid());
	}
	

}
