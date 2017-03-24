package model.spring;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.XmlViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"controller"})
public class SpringMvcJavaConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private ServletContext application;
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		Resource resource =
				new ServletContextResource(application, "/WEB-INF/views.xml");
		XmlViewResolver viewResolver1 = new XmlViewResolver();
		viewResolver1.setLocation(resource);
		viewResolver1.setOrder(5);
		registry.viewResolver(viewResolver1);
		
		InternalResourceViewResolver viewResolver2 = new InternalResourceViewResolver();
		viewResolver2.setOrder(10);
		registry.viewResolver(viewResolver2);
	}
}
