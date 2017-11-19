package br.com.network.tiever.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity(name="user_sec")
@SequenceGenerator(name="user_sequence", sequenceName="sequence_user", initialValue=1, allocationSize=1)
public class User {

	@Id
	@GeneratedValue(generator="user_sequence", strategy=GenerationType.SEQUENCE)
	private Long id;

	@NotBlank(message= "{NotBlank.user.username}")
	@Column(unique=true)
	private String username;

	@NotBlank(message= "{NotBlank.user.password}")
	private String password;

	@OneToOne(cascade=CascadeType.ALL, optional=false)
	@NotNull(message="{NotNull.user.usuario}")
	private Usuario usuario;


	public User(String username, String password, Usuario usuario) {
		super();
		this.username = username;
		this.password = password;
		this.usuario = usuario;
	}

	public User() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


}
