package br.uem.musipathapi.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "tbl_musicas")
public class Musica {

	@Id
	@Column(name = "mbid")
	private String mbid;

	@Column(name = "nome")
	private String nome;

	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<String> tags;

	@Column(name = "url_lastfm")
	private String urlLastFm;

	@Column(name = "url_youtube")
	private String urlYoutube;

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

	public String getUrlYoutube() {
		return urlYoutube;
	}

	public void setUrlYoutube(String urlYoutube) {
		this.urlYoutube = urlYoutube;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
