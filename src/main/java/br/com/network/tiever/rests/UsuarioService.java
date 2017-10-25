package br.com.network.tiever.rests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.network.tiever.entidade.Usuario;
import br.com.network.tiever.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
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
			return new ResponseEntity<>(repository.save(usuario), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuario) {
		try {
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
	
}
