package br.uem.crawlerlast.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.uem.crawlerlast.modelo.Aresta;
import br.uem.crawlerlast.modelo.Artista;
import br.uem.crawlerlast.modelo.ArtistaSimilar;
import br.uem.crawlerlast.modelo.Grafo;
import br.uem.crawlerlast.modelo.No;
import br.uem.crawlerlast.service.ArtistaService;

@CrossOrigin
@RestController
@RequestMapping("api/grafo/")
public class GrafoControle {
	
	@Autowired
	private ArtistaService artistaService;

	@GetMapping("grafoteste")
	public Grafo teste() {
		Grafo grafo = new Grafo();
		No no1 = new No("1", "node 1");
		No no2 = new No("2", "node 1");
		No no3 = new No("3", "node 1");
		Aresta aresta1 = new Aresta("1", "2");
		Aresta aresta2 = new Aresta("2", "3");
		Aresta aresta3 = new Aresta("3", "1");
		grafo.getListaDeNos().add(no1);
		grafo.getListaDeNos().add(no2);
		grafo.getListaDeNos().add(no3);
		grafo.getListaDeArestas().add(aresta1);
		grafo.getListaDeArestas().add(aresta2);
		grafo.getListaDeArestas().add(aresta3);
		return grafo;
	}
	
	@GetMapping("grafoartista/{nome}/{nivelLimite}")
	public Grafo formarGrafoArtista(@PathVariable(value = "nome") String nome, @PathVariable(value = "nivelLimite") String nivelLimite) {
		
		Artista artista = artistaService.buscaPorNome(nome);		
		Grafo grafoArtista = new Grafo();
		
		if(artista == null) {
			return grafoArtista;
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
