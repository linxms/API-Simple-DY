package org.example.apisimple_dy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.apisimple_dy.mapper")
public class ApiSimpleDyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSimpleDyApplication.class, args);
	}

}
