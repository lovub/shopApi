package org.shop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/*@SpringBootApplication
public class ShopApiApplication extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ShopApiApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(ShopApiApplication.class, args);
    }
}*/


@SpringBootApplication
public class ShopApiApplication{

	public static void main(String[] args) {
		SpringApplication.run(ShopApiApplication.class, args);
	}

}
