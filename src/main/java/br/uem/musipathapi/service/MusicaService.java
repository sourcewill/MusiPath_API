package br.uem.musipathapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.uem.musipathapi.modelo.Musica;
import br.uem.musipathapi.repository.MusicaRepository;

@Service
public class MusicaService {
	
	@Autowired
	private MusicaRepository musicaRepository;
	
	public Musica buscaPorMbid(String mbid) {
		return musicaRepository.findByMbid(mbid);
	}

}
