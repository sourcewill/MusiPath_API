package br.uem.crawlerlast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.uem.crawlerlast.controle.AlbumControle;
import br.uem.crawlerlast.controle.ArtistaControle;
import br.uem.crawlerlast.controle.CrawlerControle;
import br.uem.crawlerlast.controle.MusicaControle;
import br.uem.crawlerlast.enums.TipoBusca;
import br.uem.crawlerlast.modelo.Album;

@SpringBootApplication
public class CrawlerLastApplication implements CommandLineRunner{
	
	@Autowired
	private ArtistaControle artistaControle;
	
	@Autowired
	private AlbumControle albumControle;
	
	@Autowired
	private MusicaControle musicaControle;
	
	@Autowired
	private CrawlerControle crawlerControle;

	public static void main(String[] args) {

		SpringApplication.run(CrawlerLastApplication.class, args);		

	}

	@Override
	public void run(String... args) throws Exception {
		
		//albumControle.criarAlbunsSimilares();
		
		//artistaControle.deletarArtistasSemAlbuns();
		//albumControle.deletarAlbunsVazios();
		
		//artistaControle.deletarArtistaPorMbid("012151a8-0f9a-44c9-997f-ebd68b5389f9");
		//crawlerControle.crawlerArtista(TipoBusca.BUSCA_MBID, "012151a8-0f9a-44c9-997f-ebd68b5389f9");
		
		//crawlerControle.crawlerArtistasSimilares();
		//crawlerControle.crawlerArtistasPorArquivo("nomes_artistas_teste\\teste.txt");
		
		//crawlerControle.crawlerArtista(TipoBusca.BUSCA_NOME, "Bruno Mars");
		//crawlerControle.crawlerArtista(TipoBusca.BUSCA_MBID, "db36a76f-4cdf-43ac-8cd0-5e48092d2bae");
		
	}

}
