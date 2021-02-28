package br.uem.musipathapi.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.uem.musipathapi.modelo.Aresta;
import br.uem.musipathapi.modelo.Artista;
import br.uem.musipathapi.modelo.ArtistaSimilar;
import br.uem.musipathapi.modelo.Grafo;
import br.uem.musipathapi.modelo.No;
import br.uem.musipathapi.service.ArtistaService;

@CrossOrigin
@RestController
@RequestMapping("api/grafo/")
public class GrafoControle {
	
	@Autowired
	private ArtistaService artistaService;
	
	@GetMapping("grafoartista/{tipoBusca}/{chave}/{nivelLimite}")
	public Grafo formarGrafoArtista(@PathVariable(value = "tipoBusca") String tipoBusca, @PathVariable(value = "chave") String chave, @PathVariable(value = "nivelLimite") String nivelLimite) {
		
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
		
		formarGrafoPorRecursao(grafoArtista, artista, 0, Integer.parseInt(nivelLimite));
		
		return grafoArtista;
		
	}
	
	public Grafo formarGrafoPorRecursao(Grafo grafo, Artista artista, Integer nivelatual, Integer nivelLimite) {
		
		if(nivelatual == nivelLimite) {
			No no = new No(artista.getMbid(), artista.getNome());
			no.setNivel(nivelatual);
			no.setUrlImagem(artista.getUrlImagem());
			grafo.addNo(no);
			return grafo;
		}
		
		nivelatual++;
		
		System.out.println("Nivel atual: " + nivelatual);
		System.out.println("Nivel limite: " + nivelLimite);
		System.out.println("Artista: " + artista.getNome() + "\n");
		
		No no = new No(artista.getMbid(), artista.getNome());
		no.setNivel(nivelatual);
		no.setUrlImagem(artista.getUrlImagem());
		grafo.addNo(no);
		
		for(ArtistaSimilar similar : artista.getArtistasSimilares()) {
			String mbidSimilar = similar.getMbidSimilar();
			Artista artistaSImilar = artistaService.buscaPorMbid(mbidSimilar);
			if(artistaSImilar != null) {
				Aresta aresta = new Aresta(artista.getMbid(), mbidSimilar);
				grafo.addAresta(aresta);
				formarGrafoPorRecursao(grafo, artistaSImilar, nivelatual, nivelLimite);
			}
			
		}
		
		return grafo;
	}
}
