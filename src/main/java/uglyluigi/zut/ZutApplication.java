package uglyluigi.zut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class ZutApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZutApplication.class, args);
	}

}
