package br.com.network.tiever.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.network.tiever.entidade.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);

}
