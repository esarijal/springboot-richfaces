package com.bustanil;

import com.sun.faces.config.FacesInitializer;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@Configuration
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class).bannerMode(Banner.Mode.OFF);
	}

	@Bean
	ServletRegistrationBean jsfServlet() {
	    return new ServletRegistrationBean(){

            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                FacesInitializer facesInitializer = new FacesInitializer();

                Set<Class<?>> clazz = new HashSet<>();
                clazz.add(Application.class);
                facesInitializer.onStartup(clazz, servletContext);
            }
        };
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
