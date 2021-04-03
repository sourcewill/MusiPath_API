package br.uem.musipathapi.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "tbl_grafos")
public class Grafo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "grafo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<No> listaDeNos;
	
	@OneToMany(mappedBy = "grafo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Aresta> listaDeArestas;
	
	public Grafo() {
		listaDeNos = new ArrayList<No>();
		listaDeArestas = new ArrayList<Aresta>();
	}

	public List<No> getListaDeNos() {
		return listaDeNos;
	}

	public List<Aresta> getListaDeArestas() {
		return listaDeArestas;
	}
	
	public void addNo(No novoNo) {
		
		No remover = null;
		boolean jaExiste = false;
		
		for(No no : this.listaDeNos) {
			if(novoNo.getId().equals(no.getId()) ) {
				jaExiste = true;
				if(novoNo.getNivel() < no.getNivel()) { //Garante adicionar o nó com menor nivel na volta recursiva
					remover = no;
				}
				break;
			}
		}
		
		if(!jaExiste) {
			novoNo.setGrafo(this);
			this.listaDeNos.add(novoNo);
			return;
		}
		if(remover != null) {
			this.listaDeNos.remove(remover);
			novoNo.setGrafo(this);
			this.listaDeNos.add(novoNo);
		}
		
	}
	
	public void addAresta(Aresta novaAresta) {
		
		for(Aresta aresta : this.listaDeArestas) {
			
			if(novaAresta.getDe().equals(aresta.getDe()) && novaAresta.getPara().equals(aresta.getPara())) {
				return;
			}
			if(novaAresta.getDe().equals(aresta.getPara()) && novaAresta.getPara().equals(aresta.getDe())) {
				return;
			}
		}
		novaAresta.setGrafo(this);
		this.listaDeArestas.add(novaAresta);
	}

}
