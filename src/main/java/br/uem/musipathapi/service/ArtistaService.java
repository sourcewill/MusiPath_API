package br.uem.musipathapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.uem.musipathapi.modelo.Artista;
import br.uem.musipathapi.repository.ArtistaRepository;

@Service
public class ArtistaService {

	@Autowired
	private ArtistaRepository artistaRepository;
	
	public Artista buscaPorMbid(String mbid) {
		return artistaRepository.findByMbid(mbid);
	}
	
	public Artista buscaPorNome(String nome) {
		return artistaRepository.findByNome(nome);
	}
	
	public List<Artista> buscarTodosArtistas() {
		return artistaRepository.findAll();
	}
	
	public void deletarArtistaPorMbid(String mbid) {
		artistaRepository.deleteById(mbid);
	}
	
}
