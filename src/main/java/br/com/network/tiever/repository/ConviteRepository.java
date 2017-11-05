package br.com.network.tiever.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.network.tiever.entidade.Convite;
import br.com.network.tiever.entidade.Usuario;

public interface ConviteRepository extends CrudRepository<Convite, Long> {
	
	List<Convite> findByUsuarioDe(Usuario usuario);

}
