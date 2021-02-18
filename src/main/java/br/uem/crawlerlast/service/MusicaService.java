package br.uem.crawlerlast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.uem.crawlerlast.modelo.Musica;
import br.uem.crawlerlast.repository.MusicaRepository;

@Service
public class MusicaService {
	
	@Autowired
	private MusicaRepository musicaRepository;
	
	public Musica criar(Musica musica) {
		return musicaRepository.save(musica);
	}
	
	public Musica buscaPorMbid(String mbid) {
		return musicaRepository.findByMbid(mbid);
	}

}
