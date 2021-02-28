package br.uem.musipathapi.modelo;

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
@Table(name="tbl_artista_similar")
public class ArtistaSimilar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="mbid_artista")
	@JsonIgnore
	private Artista artista;
	
	@Column(name="mbid_similar")
	private String mbidSimilar;
	
	@Column(name="fator_similaridade")
	private String fatorSimilaridade;
	
	// Getters e Setters

	public Long getId() {
		return id;
	}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public String getMbidSimilar() {
		return mbidSimilar;
	}

	public void setMbidSimilar(String mbidSimilar) {
		this.mbidSimilar = mbidSimilar;
	}

	public String getFatorSimilaridade() {
		return fatorSimilaridade;
	}

	public void setFatorSimilaridade(String fatorSimilaridade) {
		this.fatorSimilaridade = fatorSimilaridade;
	}

}
