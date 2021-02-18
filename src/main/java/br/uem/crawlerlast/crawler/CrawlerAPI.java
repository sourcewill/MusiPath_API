package br.uem.crawlerlast.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.uem.crawlerlast.modelo.Album;
import br.uem.crawlerlast.modelo.Artista;
import br.uem.crawlerlast.modelo.ArtistaSimilar;
import br.uem.crawlerlast.modelo.Musica;

public abstract class CrawlerAPI {

	private final static String APIKey = "e84c26363e5e67bf8ee7fc4cc90d19e8";

	/*
	 * REQUISIÇÕES API
	 */

	public static String requisicaoURL(String requisicaoUrl) {

		System.out.println("\nRequisição: " + requisicaoUrl);

		URL url = null;
		try {
			url = new URL(requisicaoUrl);
		} catch (MalformedURLException ex) {
			System.err.println("Erro: " + ex.getMessage());
		}

		try {
			HttpURLConnection conexao;
			conexao = (HttpURLConnection) url.openConnection();
			conexao.setRequestMethod("GET");
			int codigoResposta = conexao.getResponseCode();
			System.out.println("Código de Resposta: " + codigoResposta + "\n");
			BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
			String linha;
			StringBuilder resposta = new StringBuilder();
			while ((linha = in.readLine()) != null) {
				resposta.append(linha);
			}
			in.close();
			return resposta.toString();

		} catch (IOException ex) {
			System.err.println("Erro: " + ex.getMessage());
			return null;
		}

	}

	public static String requisicaoArtistaPorNome(String nomeArtista) {

		nomeArtista = nomeArtista.replace(" ", "+");

		String requisicao = "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=" + nomeArtista
				+ "&api_key=" + APIKey + "&format=json";

		String respostaJson = requisicaoURL(requisicao);

		return respostaJson;
	}

	public static String requisicaoArtistaPorMbid(String mbidArtista) {

		String requisicao = "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&mbid=" + mbidArtista + "&api_key="
				+ APIKey + "&format=json";

		String respostaJson = requisicaoURL(requisicao);

		return respostaJson;
	}

	public static String requisicaoArtistasSimilaresPorMbid(String mbidArtista, Integer limite) {

		String requisicao = "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&mbid=" + mbidArtista + "&limit="
				+ limite.toString() + "&api_key=" + APIKey + "&format=json";

		String respostaJson = requisicaoURL(requisicao);

		return respostaJson;
	}

	public static String requisicaoAlbumPorNome(String nomeAlbum, String nomeArtista) {

		nomeAlbum = nomeAlbum.replace(" ", "+");
		nomeArtista = nomeArtista.replace(" ", "+");

		String requisicao = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + APIKey + "&artist="
				+ nomeArtista + "&album=" + nomeAlbum + "&format=json";

		String respostaJson = requisicaoURL(requisicao);

		return respostaJson;
	}

	public static String requisicaoAlbumPorMbid(String mbidAlbum) {

		String requisicao = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + APIKey + "&mbid="
				+ mbidAlbum + "&format=json";

		String respostaJson = requisicaoURL(requisicao);

		return respostaJson;
	}

	public static String requisicaoMusicaPorNome(String nomeMuscia, String nomeArtista) {

		nomeMuscia = nomeMuscia.replace(" ", "+");
		nomeArtista = nomeArtista.replace(" ", "+");

		String requisicao = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=" + APIKey + "&artist="
				+ nomeArtista + "&track=" + nomeMuscia + "&format=json";

		String respostaJson = requisicaoURL(requisicao);

		return respostaJson;
	}

	public static String requisicaoMusicaPorMbid(String mbidAlbum) {

		String requisicao = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=" + APIKey + "&mbid="
				+ mbidAlbum + "&format=json";

		String respostaJson = requisicaoURL(requisicao);

		return respostaJson;
	}

	/*
	 * LEITURA DE JSON
	 */

	public static Artista lerJsonArtista(String respostaJson) {

		Artista artista = null;

		try {
			JSONObject jsonObject = new JSONObject(respostaJson);
			JSONObject artist = jsonObject.getJSONObject("artist");
			// JSONObject bio = artist.getJSONObject("bio");
			JSONObject tags = artist.getJSONObject("tags");
			JSONArray tag = tags.getJSONArray("tag");
			JSONArray image = artist.getJSONArray("image");

			String mbid = artist.getString("mbid");
			String nome = artist.getString("name");
			String urlLastFm = artist.getString("url");
			// String biografiaResumo = bio.getString("summary");
			// String biografiaCompleta = bio.getString("content");

			ArrayList<String> tagsArtista = new ArrayList<>();
			int numeroDeTags = tag.length();
			for (int i = 0; i < numeroDeTags; i++) {
				JSONObject indexTag = tag.getJSONObject(i);
				String nomeTag = indexTag.getString("name");
				tagsArtista.add(nomeTag);
			}

			String urlImagem = image.getJSONObject(5).getString("#text");

			artista = new Artista();
			artista.setMbid(mbid);
			artista.setNome(nome);
			artista.setUrlLastFm(urlLastFm);
			// artista.setBiografiaResumo(biografiaResumo);
			// artista.setBiografiaCompleta(biografiaCompleta);
			artista.setTags(tagsArtista);
			artista.setUrlImagem(urlImagem);

		} catch (JSONException ex) {
			System.err.println("Erro: " + ex.getMessage());
		}

		return artista;
	}

	public static List<ArtistaSimilar> lerJsonArtistasSimilares(String respostaJson) {

		ArrayList<ArtistaSimilar> artistasSimilares = null;

		try {
			JSONObject jsonObject = new JSONObject(respostaJson);
			JSONObject similarartists = jsonObject.getJSONObject("similarartists");
			JSONArray artist = similarartists.getJSONArray("artist");
			int numeroDeSimilares = artist.length();

			artistasSimilares = new ArrayList<>();

			for (int i = 0; i < numeroDeSimilares; i++) {
				try {
					ArtistaSimilar artistaSimilar = new ArtistaSimilar();
					String mbidSimilar = artist.getJSONObject(i).getString("mbid");
					artistaSimilar.setMbidSimilar(mbidSimilar);
					String fatorSimilaridade = artist.getJSONObject(i).getString("match");
					artistaSimilar.setFatorSimilaridade(fatorSimilaridade);

					artistasSimilares.add(artistaSimilar);
				} catch (JSONException ex) {
					continue;
				}
			}

		} catch (JSONException ex) {
			System.err.println("Erro: " + ex.getMessage());
		}

		return artistasSimilares;
	}

	public static Album lerJsonAlbum(String respostaJson) {

		Album album = null;

		try {

			JSONObject jsonObject = new JSONObject(respostaJson);
			JSONObject albumJson = jsonObject.getJSONObject("album");
			JSONArray image = albumJson.getJSONArray("image");
			JSONObject tags = albumJson.getJSONObject("tags");
			JSONArray tag = tags.getJSONArray("tag");

			String mbid = albumJson.getString("mbid");
			String nome = albumJson.getString("name");
			String urlLastFm = albumJson.getString("url");
			String urlImagem = image.getJSONObject(5).getString("#text");

			ArrayList<String> tagsAlbum = new ArrayList<>();
			int numeroDeTags = tag.length();
			for (int i = 0; i < numeroDeTags; i++) {
				JSONObject indexTag = tag.getJSONObject(i);
				String nomeTag = indexTag.getString("name");
				tagsAlbum.add(nomeTag);
			}

			album = new Album();
			album.setMbid(mbid);
			album.setNome(nome);
			album.setUrlLastFm(urlLastFm);
			album.setUrlImagem(urlImagem);
			album.setTags(tagsAlbum);

		} catch (JSONException ex) {
			System.err.println("Erro: " + ex.getMessage());
		}

		return album;
	}

	public static Musica lerJsonMusica(String respostaJson) {
		Musica musica = null;

		try {

			JSONObject jsonObject = new JSONObject(respostaJson);
			JSONObject track = jsonObject.getJSONObject("track");
			JSONObject toptags = track.getJSONObject("toptags");
			JSONArray tag = toptags.getJSONArray("tag");

			String mbid = track.getString("mbid");
			String nome = track.getString("name");
			String urlLastFm = track.getString("url");

			ArrayList<String> tagsMusica = new ArrayList<>();
			int numeroDeTags = tag.length();
			for (int i = 0; i < numeroDeTags; i++) {
				JSONObject indexTag = tag.getJSONObject(i);
				String nomeTag = indexTag.getString("name");
				tagsMusica.add(nomeTag);
			}

			musica = new Musica();
			musica.setMbid(mbid);
			musica.setNome(nome);
			musica.setUrlLastFm(urlLastFm);
			musica.setTags(tagsMusica);

		} catch (JSONException ex) {
			System.err.println("Erro: " + ex.getMessage());
		}

		return musica;
	}

}
