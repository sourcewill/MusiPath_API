package br.uem.crawlerlast.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.uem.crawlerlast.modelo.Album;
import br.uem.crawlerlast.modelo.Artista;
import br.uem.crawlerlast.modelo.ArtistaSimilar;
import br.uem.crawlerlast.service.ArtistaService;

@CrossOrigin
@RestController
@RequestMapping("api/artistas/")
public class ArtistaControle {

	@Autowired
	private ArtistaService artistaService;

	public Artista criar(Artista artista) {
		return artistaService.criar(artista);
	}
	
	public Artista associarArtistasSimilares(Artista artista, List<ArtistaSimilar> similares) {

		for (ArtistaSimilar similar : similares) {
			similar.setArtista(artista);
			artista.getArtistasSimilares().add(similar);
		}
		return criar(artista);
	}

	public Artista associarAlbum(Artista artista, Album album) {
		album.setArtista(artista);
		artista.getAlbuns().add(album);
		return criar(artista);
	}
	
	@GetMapping("buscartodos")
	public List<Artista> buscarTodosArtistas() {
		return artistaService.buscarTodosArtistas();
	}
	
	@GetMapping("buscarpormbid/{mbid}")
	public Artista buscarPorMbid(@PathVariable(value = "mbid") String mbid) {
		return artistaService.buscaPorMbid(mbid);
	}
	
	@GetMapping("buscarpornome/{nome}")
	public Artista buscarPorNome(@PathVariable(value = "nome") String nome) {
		return artistaService.buscaPorNome(nome);
	}
	
	public void deletarArtistaPorMbid(String mbid) {
		artistaService.deletarArtistaPorMbid(mbid);
	}
	
	public void deletarArtistasSemAlbuns() {
		
		List<Artista> artistas = buscarTodosArtistas();	
		for(Artista artista : artistas) {			
			if(artista.getAlbuns().size() == 0) {
				String mbid = artista.getMbid();
				deletarArtistaPorMbid(mbid);
			}
		}
	}

}
