package model.spring;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletRegister extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {SpringMvcJavaConfig.class};
	}
	@Override
	protected String[] getServletMappings() {
		return new String[] {"*.controller"};
	}
}
