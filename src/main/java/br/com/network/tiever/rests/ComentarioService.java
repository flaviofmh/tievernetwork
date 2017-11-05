package br.com.network.tiever.rests;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.network.tiever.entidade.Comentario;
import br.com.network.tiever.repository.ComentarioRepository;
import br.com.network.tiever.repository.UsuarioRepository;

@RestController
@RequestMapping("/comentario")
public class ComentarioService {

	@Autowired
	private ComentarioRepository repository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping(method=RequestMethod.GET, path="/{idUsuario}")
	public ResponseEntity<?> buscarComentariosUsuarios(@PathVariable Long idUsuario) {
		try {
			return new ResponseEntity<>(repository.findByAutorId(idUsuario), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, path="/{idUsuarioCriador}/{idComentarioPai}")
	public ResponseEntity<?> inserirComentario(@RequestBody Comentario comentario, @PathVariable("idUsuarioCriador") Long idUsuarioCriador, @PathVariable("idComentarioPai") Long idComentarioPai) {
		try {
			comentario.setDataCriacao(new Date());
			comentario.setAutor(usuarioRepository.findOne(idUsuarioCriador));
			if(idComentarioPai != null && idComentarioPai != 0L) {
				Comentario comentarioPai = repository.findOne(idComentarioPai);
				if(comentarioPai.getRespostas() == null) {
					comentarioPai.setRespostas(new ArrayList<>());
				}
				comentarioPai.getRespostas().add(comentario);
				return new ResponseEntity<>(repository.save(comentarioPai), HttpStatus.CREATED);
			}
			return new ResponseEntity<>(repository.save(comentario), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
