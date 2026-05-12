package rado.alberto.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rado.alberto.org.config.JwtProperties;


@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class PedidosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PedidosApplication.class, args);
    }

}
