package br.com.network.tiever.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.network.tiever.entidade.Comentario;

public interface ComentarioRepository extends CrudRepository<Comentario, Long> {
	
	List<Comentario> findByAutorId(Long idUsuario);

}
