package br.uem.crawlerlast.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class CrawlerWeb {
	
	/*
	 * CRAWLER WEB
	 */

	public static String getYoutubeUrlByLastFmUrl(String lastFmUrl) {

		String youtubeUrl = null;

		try {

			Document conteudoPagina = Jsoup.connect(lastFmUrl).get();
			Elements dataYoutubeUrl = conteudoPagina.getElementsByAttribute("data-youtube-url");
			youtubeUrl = dataYoutubeUrl.get(0).attr("data-youtube-url").toString();

		} catch (Exception ex) {
			System.err.println("Erro: " + ex.getMessage());
		}

		return youtubeUrl;
	}
	
	public static String getUrlImagemArtistaByLastFmUrl(String lastFmUrl) {
		String urlImagemArtista = "";
		
		try {

			Document conteudoPagina = Jsoup.connect(lastFmUrl).get();
			Elements backgroundImg = conteudoPagina.getElementsByClass("header-new-background-image");
			urlImagemArtista = backgroundImg.get(0).attr("content").toString();

		} catch (Exception ex) {
			System.err.println("Erro: " + ex.getMessage());
		}
		
		return urlImagemArtista;
	}

	public static List<String> getNomesAlbuns(String urlArtistaLastFm, Integer numeroMaximo) {

		String urlAlbum = urlArtistaLastFm + "/+albums";

		List<String> nomesAlbuns = null;

		try {

			nomesAlbuns = new ArrayList<>();

			Document conteudoPagina = Jsoup.connect(urlAlbum).get();
			Element pagination = conteudoPagina.getElementsByClass("pagination-list").get(0);
			Integer numLi = pagination.getElementsByTag("li").size();
			Element penultimoLi = pagination.getElementsByTag("li").get(numLi-2);
			Integer numPaginas = Integer.parseInt(penultimoLi.getElementsByTag("a").get(0).text());

			for (int i = 1; i <= numPaginas; i++) {

				String urlPagina = urlAlbum + "?page=" + i;
				conteudoPagina = Jsoup.connect(urlPagina).get();

				Elements listItem = conteudoPagina.getElementsByClass("resource-list--release-list-item-name");

				for (int j = 0; j < listItem.size(); j++) {
					Element item = listItem.get(j);
					String nomeAlbum = item.getElementsByTag("a").get(0).text();
					nomesAlbuns.add(nomeAlbum);
					if(nomesAlbuns.size() >= numeroMaximo) {
						return nomesAlbuns;
					}
				}
			}

		} catch (Exception ex) {
			System.err.println("Erro: " + ex.getMessage());
		}

		return nomesAlbuns;
	}

	public static List<String> getNomesMusicas(String urlAlbumLastFm) {

		List<String> nomesMusicas = null;

		try {

			nomesMusicas = new ArrayList<>();

			Document conteudoPagina = Jsoup.connect(urlAlbumLastFm).get();
			Elements listItem = conteudoPagina.getElementsByClass("chartlist-name");

			for (int i = 0; i < listItem.size(); i++) {
				Element item = listItem.get(i);
				String nomeMusica = item.getElementsByTag("a").get(0).text();
				nomesMusicas.add(nomeMusica);
			}

		} catch (IOException ex) {
			System.err.println("Erro: " + ex.getMessage());
		}

		return nomesMusicas;
	}
	
	public static List<String> getNomesArtistasPorTag(String tag){
		
		List<String> nomesArtistas = null;
		
		try {
			
			nomesArtistas = new ArrayList<>();
			
			String url = "https://www.last.fm/tag/" + tag + "/artists";
			Document conteudoPagina = Jsoup.connect(url).get();
			Element pagination = conteudoPagina.getElementsByClass("pagination-list").get(0);
			Integer numLi = pagination.getElementsByTag("li").size();
			Element penultimoLi = pagination.getElementsByTag("li").get(numLi-2);
			Integer numPaginas = Integer.parseInt(penultimoLi.getElementsByTag("a").get(0).text());


			for(int i=1; i<=numPaginas; i++) {
				
				System.out.println(tag + ": pagina " + i);
				String urlPagina = url + "?page=" + i;
				conteudoPagina = Jsoup.connect(urlPagina).get();
				Elements artistList = conteudoPagina.getElementsByClass("big-artist-list-title");
				
				for(int j = 0; j<artistList.size(); j++) {
					String nomeArtista = artistList.get(j).getElementsByTag("a").text();
					nomesArtistas.add(nomeArtista);
				}
			}

		} catch (IOException ex) {
			System.err.println("Erro: " + ex.getMessage());
		}
		
		return nomesArtistas;
	}

}
