package br.com.network.tiever;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import br.com.network.tiever.entidade.Usuario;
import br.com.network.tiever.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UsuarioServiceTeste {

	@Autowired
	private UsuarioRepository repository;
	
	@LocalServerPort
    private int port;
	
	@Autowired
    private MockMvc mockMvc;
	
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
	
	@Test
	public void clienteRepositoryNotNull() {
		assertThat(repository).isNotNull();
	}
	
	@Test
    public void testarSalvarUsuario() throws Exception {
		String usuarioJson = json(new Usuario(UUID.randomUUID().toString(), new Date()));
        this.mockMvc.perform(post("/usuario").contentType(MediaType.APPLICATION_JSON).content(usuarioJson)).andDo(print()).andExpect(status().isCreated());
    }
	
	@Test
    public void buscarTodosUsuariosCadastrados() throws Exception {
        this.mockMvc.perform(get("/usuario")).andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void testarAtualizarUsuario() throws Exception {
		Gson gson = new Gson();
		Usuario usuarioCarregado = repository.findOne(1L);
		usuarioCarregado.setNome(UUID.randomUUID().toString());
		String usuarioJson = gson.toJson(usuarioCarregado);
        this.mockMvc.perform(put("/usuario").contentType(MediaType.APPLICATION_JSON).content(usuarioJson)).andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void testarExcluirUsuario() throws Exception {
        this.mockMvc.perform(delete("/usuario/{idUsuario}", 4L)).andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void adicionarAmigos() throws Exception {
		Gson gson = new Gson();
		Usuario usuarioCarregado = repository.findOne(1L);
		{
			List<Usuario> amigos = new ArrayList<Usuario>();
			amigos.add(repository.findOne(4L));
			amigos.add(repository.findOne(5L));
//			usuarioCarregado.setAmigosDe(amigos);
		}
		String usuarioJson = gson.toJson(usuarioCarregado);
        this.mockMvc.perform(put("/usuario").contentType(MediaType.APPLICATION_JSON).content(usuarioJson)).andDo(print()).andExpect(status().isOk());
    }
	
	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
	
}
