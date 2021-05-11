package br.uem.musipathapi.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.uem.musipathapi.modelo.Artista;
import br.uem.musipathapi.modelo.Grafo;
import br.uem.musipathapi.service.ArtistaService;

@CrossOrigin
@RestController
@RequestMapping("api/grafo/")
public class GrafoControle {
	
	@Autowired
	private ArtistaService artistaService;
	
	@GetMapping("grafoartista/{tipoBusca}/{chave}/{profundidadeLimite}/{ramificacaoLimite}")
	public Grafo formarGrafoArtista(@PathVariable(value = "tipoBusca") String tipoBusca, @PathVariable(value = "chave") String chave) {
		
		Grafo grafoArtista = new Grafo();
		Artista artista = null;
		
		switch(tipoBusca) {
			case "mbid":
				artista = artistaService.buscaPorMbid(chave);
				break;
			case "nome":
				artista = artistaService.buscaPorNome(chave);
				break;
			default:
				return grafoArtista;
		}
		
		if(artista == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		
		return artista.getGrafo();
		
	}
}
