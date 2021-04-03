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
@Table(name = "tbl_nos")
public class No{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_no;
	
	@Column(name="id_artista")
	private String id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="url_imagem")
	private String urlImagem;
	
	@Column(name="nivel")
	private Integer nivel;
	
	@ManyToOne
	@JoinColumn(name = "grafo_id")
	@JsonIgnore
	private Grafo grafo;
	
	public No() {
		
	}
	
	public No(String id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
		this.urlImagem = "";
		this.nivel = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public Long getId_no() {
		return id_no;
	}

	public void setId_no(Long id_no) {
		this.id_no = id_no;
	}

	public Grafo getGrafo() {
		return grafo;
	}

	public void setGrafo(Grafo grafo) {
		this.grafo = grafo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		No other = (No) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
