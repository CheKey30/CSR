package com.csr.datasearching;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.csr.datasearching.mapper")
public class DatasearchingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatasearchingApplication.class, args);
	}

}
