package br.uem.crawlerlast.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.uem.crawlerlast.modelo.Artista;


@Entity
@Table(name="tbl_artistas")
public class Artista {
	
	@Id
	@Column(name="mbid")
	private String mbid;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="url_lastfm")
	private String urlLastFm;
	
	@Column(name="bio_resumo", length = 1000)
	private String biografiaResumo;
	
	@Column(name="bio_completa", length = 1500)
	private String biografiaCompleta;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<String> tags;
	
	@Column(name="url_imagem")
	private String urlImagem;
	
	@OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Album> albuns;
	
	@OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<ArtistaSimilar> artistasSimilares;
	
	public Artista() {
		this.tags = new ArrayList<>();
		this.albuns = new ArrayList<>();
		this.artistasSimilares = new ArrayList<>();
	}

	//Getters e Setters
	
	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrlLastFm() {
		return urlLastFm;
	}

	public void setUrlLastFm(String urlLastFm) {
		this.urlLastFm = urlLastFm;
	}

	public String getBiografiaResumo() {
		return biografiaResumo;
	}

	public void setBiografiaResumo(String biografiaResumo) {
		this.biografiaResumo = biografiaResumo;
	}

	public String getBiografiaCompleta() {
		return biografiaCompleta;
	}

	public void setBiografiaCompleta(String biografiaCompleta) {
		this.biografiaCompleta = biografiaCompleta;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public List<Album> getAlbuns() {
		return albuns;
	}

	public void setAlbuns(List<Album> albuns) {
		this.albuns = albuns;
	}
	
	public List<ArtistaSimilar> getArtistasSimilares() {
		return artistasSimilares;
	}

	public void setArtistasSimilares(List<ArtistaSimilar> artistasSimilares) {
		this.artistasSimilares = artistasSimilares;
	}


}
