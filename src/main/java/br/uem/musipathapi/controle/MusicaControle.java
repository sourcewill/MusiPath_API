package br.uem.musipathapi.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.uem.musipathapi.modelo.Musica;
import br.uem.musipathapi.service.MusicaService;

@Controller
public class MusicaControle {
	
	@Autowired
	private MusicaService musicaService;
	
	public Musica buscarPorMbid(String mbid) {
		return musicaService.buscaPorMbid(mbid);
	}

}
