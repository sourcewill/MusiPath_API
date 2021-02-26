package br.uem.crawlerlast.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tbl_album_similar")
public class AlbumSimilar implements Comparable<AlbumSimilar>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="mbid_album")
	@JsonIgnore
	private Album album;
	
	@Column(name="mbid_similar")
	private String mbidSimilar;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="url_imagem")
	private String urlImagem;
	
	@Column(name="fator_similaridade")
	private Double fatorSimilaridade;

	public Long getId() {
		return id;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public String getMbidSimilar() {
		return mbidSimilar;
	}

	public void setMbidSimilar(String mbidSimilar) {
		this.mbidSimilar = mbidSimilar;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public Double getFatorSimilaridade() {
		return fatorSimilaridade;
	}

	public void setFatorSimilaridade(Double fatorSimilaridade) {
		this.fatorSimilaridade = fatorSimilaridade;
	}

	@Override
	public int compareTo(AlbumSimilar outroAlbumSimilar) {
		
		if (this.fatorSimilaridade > outroAlbumSimilar.getFatorSimilaridade()) {
			return -1; 
		}
		if (this.fatorSimilaridade < outroAlbumSimilar.getFatorSimilaridade()) {
			return 1; 
		}
		return 0; 
	}
	
	
	

}
