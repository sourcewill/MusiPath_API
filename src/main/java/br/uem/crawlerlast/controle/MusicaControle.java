package br.uem.crawlerlast.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.uem.crawlerlast.modelo.Musica;
import br.uem.crawlerlast.service.MusicaService;

@Controller
public class MusicaControle {
	
	@Autowired
	private MusicaService musicaService;
	
	public Musica criar(Musica musica) {
		return musicaService.criar(musica);
	}
	
	public Musica buscarPorMbid(String mbid) {
		return musicaService.buscaPorMbid(mbid);
	}

}
