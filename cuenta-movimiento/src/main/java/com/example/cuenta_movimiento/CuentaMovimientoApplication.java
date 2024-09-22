package com.example.cuenta_movimiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CuentaMovimientoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuentaMovimientoApplication.class, args);
	}

}
