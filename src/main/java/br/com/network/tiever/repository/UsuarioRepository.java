package br.com.network.tiever.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.network.tiever.entidade.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	List<Usuario> findAllByOrderByNomeAsc();
	
}
