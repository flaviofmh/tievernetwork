package br.com.network.tiever;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import br.com.network.tiever.entidade.Comentario;
import br.com.network.tiever.entidade.Usuario;
import br.com.network.tiever.repository.ComentarioRepository;
import br.com.network.tiever.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ComentarioServiceTeste {

	@Autowired
	private ComentarioRepository repository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@LocalServerPort
    private int port;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void conviteRepositoryNotNull() {
		assertThat(repository).isNotNull();
	}
	
	@Test
	public void testarPostarComentario() throws Exception {
		Gson gson = new Gson();
		Usuario usuario = usuarioRepository.findOne(2L);
		String comentarioJson = gson.toJson(new Comentario(null, "Oi, esse é meu segundo post para teste!!!", null));
		this.mockMvc.perform(post("/comentario/{idUsuarioCriador}/{idComentarioPai}", usuario.getId(), 0L).contentType(MediaType.APPLICATION_JSON_UTF8).content(comentarioJson)).andDo(print()).andExpect(status().isCreated());
	}
	
	@Test
	public void testarPostarComentarioDoComentario() throws Exception {
		Gson gson = new Gson();
		Usuario usuario = usuarioRepository.findOne(2L);
		Comentario comentario = new Comentario(null, "Eu quero mais é que voces se lasquem!!!", null);
		String comentarioJson = gson.toJson(comentario);
		this.mockMvc.perform(post("/comentario/{idUsuarioCriador}/{idComentarioPai}", usuario.getId(), 1L).contentType(MediaType.APPLICATION_JSON_UTF8).content(comentarioJson)).andDo(print()).andExpect(status().isCreated());
	}
	
}
