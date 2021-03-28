package br.uem.musipathapi.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_albuns")
public class Album {

	@Id
	@Column(name = "mbid")
	private String mbid;

	@Column(name = "nome")
	private String nome;

	@Column(name = "url_lastfm")
	private String urlLastFm;

	@Column(name = "url_imagem")
	private String urlImagem;

	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<String> tags;

	@ManyToOne
	@JoinColumn(name = "artista_mbid")
	@JsonIgnore
	private Artista artista;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Musica> musicas;
	
	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<AlbumSimilar> albunsSimilares;
	
	public Album() {
		this.tags = new ArrayList<>();
		this.albunsSimilares = new ArrayList<>();
	}

	// Getters e Setters

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

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public List<Musica> getMusicas() {
		return musicas;
	}

	public void setMusicas(List<Musica> musicas) {
		this.musicas = musicas;
	}

	public List<AlbumSimilar> getAlbunsSimilares() {
		return albunsSimilares;
	}

	public void setAlbunsSimilares(List<AlbumSimilar> albunsSimilares) {
		this.albunsSimilares = albunsSimilares;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mbid == null) ? 0 : mbid.hashCode());
		result = prime * result + ((urlImagem == null) ? 0 : urlImagem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (mbid == null) {
			if (other.mbid != null)
				return false;
		} else if (!mbid.equals(other.mbid))
			return false;
		if (urlImagem == null) {
			if (other.urlImagem != null)
				return false;
		} else if (!urlImagem.equals(other.urlImagem))
			return false;
		return true;
	}
	
	

}
