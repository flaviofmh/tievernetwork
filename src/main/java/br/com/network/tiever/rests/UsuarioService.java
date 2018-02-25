package br.com.network.tiever.rests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.network.tiever.entidade.Convite;
import br.com.network.tiever.entidade.Usuario;
import br.com.network.tiever.handler.Message;
import br.com.network.tiever.repository.ConviteRepository;
import br.com.network.tiever.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private ConviteRepository conviteRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> buscarTodosUsuarios() {
		try {
			return new ResponseEntity<>(repository.findAllByOrderByNomeAsc(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> inserirUsuario(@RequestBody Usuario usuario) {
		try {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			usuario.setUsername(usuario.getEmail());
			return new ResponseEntity<>(repository.save(usuario), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT, path="/{idUsuario}")
	public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable Long idUsuario) {
		try {
			usuario.setId(idUsuario);
			return new ResponseEntity<>(repository.save(usuario), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{idUsuario}")
	public ResponseEntity<?> excluirUsuario(@PathVariable Long idUsuario) {
		try {
			repository.delete(idUsuario);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT, path="/addamigos")
	public ResponseEntity<?> addFriend(@RequestParam("idConvite") Long idConvite) {
		Message msg = new Message();
		try {
			Convite convite = conviteRepository.findOne(idConvite);
			Usuario usuarioPara = convite.getUsuarioPara();
			Usuario usuarioDe = convite.getUsuarioDe();
			usuarioDe.getAmigos().add(usuarioPara);
			usuarioPara.getAmigos().add(usuarioDe);
			repository.save(usuarioDe);
			repository.save(usuarioPara);
			conviteRepository.delete(convite);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/{idUsuario}/amigos", method=RequestMethod.GET)
	public ResponseEntity<?> getFriends(@PathVariable("idUsuario") Long idUsuario) {
		Usuario usuarioAmigo = repository.findOne(idUsuario);
		if(usuarioAmigo == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(usuarioAmigo.getAmigos(), HttpStatus.OK);
		}
	}
	
}
