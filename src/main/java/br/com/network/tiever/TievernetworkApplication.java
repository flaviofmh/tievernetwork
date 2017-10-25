package br.com.network.tiever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"br.com.network.tiever.rests", "br.com.network.tiever"})
@EntityScan("br.com.network.tiever.entidade")
@EnableJpaRepositories("br.com.network.tiever.repository")
public class TievernetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TievernetworkApplication.class, args);
	}
}
