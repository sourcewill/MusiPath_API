package br.uem.crawlerlast.controle;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.uem.crawlerlast.crawler.CrawlerAPI;
import br.uem.crawlerlast.crawler.CrawlerWeb;
import br.uem.crawlerlast.enums.TipoBusca;
import br.uem.crawlerlast.modelo.Album;
import br.uem.crawlerlast.modelo.Artista;
import br.uem.crawlerlast.modelo.ArtistaSimilar;
import br.uem.crawlerlast.modelo.Musica;

@Controller
public class CrawlerControle {
	
	@Autowired
	private ArtistaControle artistaControle;
	
	@Autowired
	private AlbumControle albumControle;
	
	@Autowired
	private MusicaControle musicaControle;
	
	public void criarArquivosComNomesArtistas() {

		String[] tags = { "electronic", "rock", "hip-hop", "indie", "jazz", "reggae", "british", "punk", "80s", "dance",
				"acoustic", "rnb", "hardcore", "country", "blues", "alternative", "classical", "rap", "metal" };


		try {

			for (String tag : tags) {

				String caminho = "nomes_artistas\\" + tag + ".txt";
				FileWriter arquivo = new FileWriter(caminho);
				PrintWriter gravarArquivo = new PrintWriter(arquivo);

				List<String> nomesArtistas = CrawlerWeb.getNomesArtistasPorTag(tag);
				for (String nome : nomesArtistas) {
					gravarArquivo.printf(nome + "\n");
				}
				arquivo.close();

			}

		} catch (IOException ex) {
			System.out.println("Erro: " + ex);
		}
	}
	
	public void crawlerArtista(TipoBusca tipoBusca, String chaveBusca) {
		String respostaJson = "";
		Artista artista;
		Album album;
		Musica musica;
		switch (tipoBusca) {
		case BUSCA_NOME:
			respostaJson = CrawlerAPI.requisicaoArtistaPorNome(chaveBusca);
			break;
		case BUSCA_MBID:
			respostaJson = CrawlerAPI.requisicaoArtistaPorMbid(chaveBusca);
			break;
		}
		
		artista = CrawlerAPI.lerJsonArtista(respostaJson);
		if(artista == null) {
			System.out.println("Json inválido para leitura de Artista.");
			return;
		}
		if(artistaControle.buscarPorMbid(artista.getMbid()) != null) {
			System.out.println("Artista já existe no banco de dados.");
			return;
		}
		
		String urlImagemArtista = CrawlerWeb.getUrlImagemArtistaByLastFmUrl(artista.getUrlLastFm());
		artista.setUrlImagem(urlImagemArtista);
		
		artista = artistaControle.criar(artista);
		
		respostaJson = CrawlerAPI.requisicaoArtistasSimilaresPorMbid(artista.getMbid(), 3);
		List<ArtistaSimilar> similares= CrawlerAPI.lerJsonArtistasSimilares(respostaJson);
		artista = artistaControle.associarArtistasSimilares(artista, similares);
		
		List<String> listaDeAlbuns = CrawlerWeb.getNomesAlbuns(artista.getUrlLastFm(), 5);
		if(listaDeAlbuns == null) {
			System.out.println("Nenhum album econtrado.");
			return;
		}
		for(String nomeAlbum : listaDeAlbuns) {
			
			respostaJson = CrawlerAPI.requisicaoAlbumPorNome(nomeAlbum, artista.getNome());
			album = CrawlerAPI.lerJsonAlbum(respostaJson);
			if(album == null) {
				System.out.println("Json inválido para leitura de Album.");
				continue;
			}
			album = albumControle.criar(album);
			artista = artistaControle.buscarPorMbid(artista.getMbid());
			artista = artistaControle.associarAlbum(artista, album);
			
			List<String> listaDeMusicas = CrawlerWeb.getNomesMusicas(album.getUrlLastFm());
			for(String nomeMusica : listaDeMusicas) {
				
				respostaJson = CrawlerAPI.requisicaoMusicaPorNome(nomeMusica, artista.getNome());
				musica = CrawlerAPI.lerJsonMusica(respostaJson);
				if(musica == null) {
					System.out.println("Json inválido para leitura de Musica.");
					continue;
				}
				String urlYoutube = CrawlerWeb.getYoutubeUrlByLastFmUrl(musica.getUrlLastFm());
				if(urlYoutube == null) {
					continue;
				}
				musica.setUrlYoutube(urlYoutube);
				musica = musicaControle.criar(musica);
				album = albumControle.buscarPorMbid(album.getMbid());
				album = albumControle.associarMusica(album, musica);
				musica = musicaControle.buscarPorMbid(musica.getMbid());
			}
		}
	}
	
	public void crawlerArtistasPorArquivo(String urlArquivo) {
		
		try {
			
			Scanner in = new Scanner(new FileReader(urlArquivo));
			while (in.hasNextLine()) {
			    String line = in.nextLine();
			    crawlerArtista(TipoBusca.BUSCA_NOME, line);
			}
			in.close();
			
		}catch (IOException ex) {
			System.out.println("Erro: " + ex);
		}
		
	}
	
	public void crawlerArtistasSimilares() {
		
		List<Artista> artistas = artistaControle.buscarTodosArtistas();	
		for(Artista artista : artistas) {			
			for(ArtistaSimilar similar : artista.getArtistasSimilares()) {
				crawlerArtista(TipoBusca.BUSCA_MBID, similar.getMbidSimilar());
			}
		}
	}

}
