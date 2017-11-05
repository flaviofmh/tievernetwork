package br.com.network.tiever.jpa;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.network.tiever.entidade.Comentario;
import br.com.network.tiever.entidade.Usuario;
import br.com.network.tiever.repository.ComentarioRepository;
import br.com.network.tiever.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes={Comentario.class, ComentarioRepository.class, Usuario.class, UsuarioRepository.class})
public class ComentarioJPATeste {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test
	public void insertTest() {
		this.entityManager.persist(new Comentario(usuarioRepository.findOne(2L), "Ola, esse é meu post!!!", new Date()));
		List<Comentario> comentario = (List<Comentario>) this.comentarioRepository.findAll();
		assertNotNull(comentario);
		assertTrue(comentario.size() > 0);
	}
	
}
