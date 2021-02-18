package br.uem.crawlerlast.modelo;

import java.util.ArrayList;
import java.util.List;


public class Grafo {
	
	private List<No> listaDeNos;
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
			this.listaDeNos.add(novoNo);
			return;
		}
		if(remover != null) {
			this.listaDeNos.remove(remover);
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
		this.listaDeArestas.add(novaAresta);
	}

}
