package br.com.network.tiever.rests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.network.tiever.entidade.User;
import br.com.network.tiever.repository.UserRepository;

@Component
public class UserService {

	@Autowired
	private UserRepository userRepository;


	public User getAuthenticatedUser() {
		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUsername(authUser.getName());
		return user;
	}
}
