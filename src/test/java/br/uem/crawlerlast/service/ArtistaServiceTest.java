package br.uem.crawlerlast.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.uem.crawlerlast.crawler.CrawlerAPI;
import br.uem.crawlerlast.modelo.Artista;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtistaServiceTest {
	
	@Autowired
	ArtistaService artistaService;
	
	@Test
	public void deveCriarArtista() {
			
		String resposta = CrawlerAPI.requisicaoArtistaPorNome("nx zero");
		Artista artista = CrawlerAPI.lerJsonArtista(resposta);
		
		Artista novo = artistaService.criar(artista);
		assertNotNull(novo);
	}

}
