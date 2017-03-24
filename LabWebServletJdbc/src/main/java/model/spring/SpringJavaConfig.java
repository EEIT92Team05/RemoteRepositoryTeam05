package model.spring;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import model.CustomerService;
import model.ProductService;
import model.dao.CustomerDAOJdbc;
import model.dao.ProductDAOJdbc;

@Configuration
public class SpringJavaConfig {
	@Bean
	public DataSource dataSource() {
		System.out.println("calling dataSource()");
		try {
			Context ctx = new InitialContext();
			return (DataSource) ctx.lookup("java:comp/env/jdbc/xxx");
		} catch(NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Bean
	public CustomerService customerService() {
		System.out.println("calling customerService()");
		CustomerService result = new CustomerService(new CustomerDAOJdbc(dataSource()));
		return result;
	}
	@Bean
	public ProductService productService() {
		System.out.println("calling productService()");
		ProductService result = new ProductService(new ProductDAOJdbc(dataSource()));
		return result;
	}
}
