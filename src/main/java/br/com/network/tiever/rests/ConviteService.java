package br.com.network.tiever.rests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.network.tiever.entidade.Convite;
import br.com.network.tiever.entidade.Usuario;
import br.com.network.tiever.repository.ConviteRepository;
import br.com.network.tiever.repository.UsuarioRepository;

@RestController
@RequestMapping("/convite")
public class ConviteService {
	
	@Autowired
	private ConviteRepository repository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> enviarConvite(@RequestParam("usuarioParaId") Long usuarioParaId, @RequestParam("usuarioDeId") Long usuarioDeId) {
		try {
			Usuario usuarioPara = usuarioRepository.findOne(usuarioParaId);
			Convite convite = new Convite(usuarioPara, usuarioRepository.findOne(usuarioDeId));
			repository.save(convite);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> listarMeusConvites(@RequestParam("usuarioDeId") Long usuarioId) {
		try {
			Usuario usuario = usuarioRepository.findOne(usuarioId);
			List<Convite> convites = repository.findByUsuarioDe(usuario);
			return new ResponseEntity<>(convites, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, path="/rejeitar/{idConvite}")
	public ResponseEntity<?> rejeitarConvite(@PathVariable("idConvite") Long idConvite) {
		try {
			repository.delete(idConvite);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, path="/aceitar/{idContive}")
	public ResponseEntity<?> aceitarConvite(@PathVariable("idContive") Long idContive) {
		try {
			Convite convite = repository.findOne(idContive);
			Usuario usuarioPara = convite.getUsuarioPara();
			Usuario usuarioDe = convite.getUsuarioDe();
			usuarioPara.getAmigos().add(usuarioDe);
			usuarioPara.getAmigosDe().add(usuarioDe);
			usuarioRepository.save(usuarioPara);
			repository.delete(idContive);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
