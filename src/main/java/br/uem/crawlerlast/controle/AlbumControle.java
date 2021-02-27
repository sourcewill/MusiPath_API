package br.uem.crawlerlast.controle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.uem.crawlerlast.modelo.Album;
import br.uem.crawlerlast.modelo.AlbumSimilar;
import br.uem.crawlerlast.modelo.Musica;
import br.uem.crawlerlast.service.AlbumService;
import br.uem.crawlerlast.utils.SimilarityUtil;

@CrossOrigin
@RestController
@RequestMapping("api/albuns/")
public class AlbumControle {
	
	@Autowired
	private AlbumService albumService;
	
	public Album criar(Album album) {
		return albumService.criar(album);
	}
	
	@GetMapping("buscarpormbid/{mbid}")
	public Album buscarPorMbid(@PathVariable(value = "mbid") String mbid) {
		Album album = albumService.buscaPorMbid(mbid);
		if(album == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		return album;
	}
	
	public List<Album> buscarTodosAlbuns() {
		return albumService.buscarTodosAlbuns();
	}
	
	public Album associarMusica(Album album, Musica musica) {
		musica.setAlbum(album);
		album.getMusicas().add(musica);
		return criar(album);
	}
	
	public void deletarAlbumPorMbid(String mbid) {
		albumService.deletarAlbumPorMbid(mbid);
	}
	
	public void deletarAlbunsVazios() {
		List<Album> albuns = buscarTodosAlbuns();	
		for(Album album : albuns) {			
			if(album.getMusicas().size() == 0) {
				String mbid = album.getMbid();
				deletarAlbumPorMbid(mbid);
			}
		}
	}
	
	public String[] getVetorTags(Album album){
		
		String tags[] = new String[album.getTags().size()];
		
		int i = 0;
		for(String tag : album.getTags()) {
			tags[i] = tag;
			i++;
		}
		return tags;
	}
	
	public void corrigirAlbunsSemTags() {
		for(Album album : this.buscarTodosAlbuns()) {
			if(album.getTags().isEmpty()) {
				for(String tag: album.getArtista().getTags()) {
					album.getTags().add(tag);
				}
			}
			this.criar(album);
		}
	}
	
	
	public Double calcularSimilaridade(Album album1, Album album2) {
		
		String tags1[] = getVetorTags(album1);
		String tags2[] = getVetorTags(album2);
		
		return SimilarityUtil.consineTextSimilarity(tags1, tags2);
	}
	
	
	public void gerarListaDeSimilares(Album album, Integer maximoDeSimilares){
		
		if(album.getTags().isEmpty()) {
			return;
		}
		
		List<AlbumSimilar> similares = new ArrayList<>();

		for(Album a : this.buscarTodosAlbuns()) {
			
			if(a.getArtista().equals(album.getArtista())) {
				continue;
			}
			
			if(a.getTags().isEmpty()) {
				continue;
			}
			
			Double similaridade = this.calcularSimilaridade(album, a);
			if(similaridade.isNaN()) {
				similaridade = 0.0;
			}			
			
			AlbumSimilar albumSimilar = new AlbumSimilar();
			albumSimilar.setAlbum(album);
			albumSimilar.setMbidSimilar(a.getMbid());
			albumSimilar.setNome(a.getNome());
			albumSimilar.setUrlImagem(a.getUrlImagem());
			albumSimilar.setFatorSimilaridade(similaridade);
			
			similares.add(albumSimilar);
			Collections.sort(similares);
			
			if(similares.size() > maximoDeSimilares) {
				similares.remove(similares.size()-1);
			}
		}
		
		album.setAlbunsSimilares(similares);
		this.criar(album);
		
		return;
	}
	
	
	public void criarAlbunsSimilares() {
		this.corrigirAlbunsSemTags();
		for(Album album : this.buscarTodosAlbuns()) {
			this.gerarListaDeSimilares(album, 10);
		}
	}

}
