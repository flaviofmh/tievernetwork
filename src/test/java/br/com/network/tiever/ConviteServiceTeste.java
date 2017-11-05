package br.com.network.tiever;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.ResultActions;

import br.com.network.tiever.repository.ConviteRepository;
import br.com.network.tiever.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ConviteServiceTeste {
	
	@Autowired
	private ConviteRepository repository;
	
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
    public void criarConviteTeste() throws Exception {
        this.mockMvc.perform(post("/convite").param("usuarioParaId", "3").param("usuarioDeId", "9")).andDo(print()).andExpect(status().isCreated());
    }
	
	@Test
    public void listarConvitesTeste() throws Exception {
        ResultActions andExpect = this.mockMvc.perform(get("/convite").contentType(MediaType.APPLICATION_JSON).param("usuarioDeId", "2")).andDo(print()).andExpect(status().isOk());
        assertNotNull(andExpect);
        System.out.println(andExpect.andReturn().getResponse());
        assertNotNull(andExpect.andReturn());
    }
	
}
