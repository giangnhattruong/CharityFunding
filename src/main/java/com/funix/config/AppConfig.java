/*
 * AppConfig.java    1.00    2022-04-05
 */

package com.funix.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Web application configuration for Spring MVC framework
 * @author Giang_Nhat_Truong
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.funix")
public class AppConfig implements WebMvcConfigurer {
	
	/**
	 * Inject RequestMappingHandlerAdapter instance which
	 * can help setting ignore default model on redirect.
	 */
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    /**
     * Set ignore default model on redirect so as we can
     * use the RedirectAttributes object to pass data
     * on redirecting view.
     */
    @PostConstruct
    public void init() {
       requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
    }

    /**
     * Set view resolver for Spring view.
     * @return
     */
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = 
				new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	/**
	 * Set CommonsMultipartResolver bean to handle multipart.
	 * from client.
	 * @return
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver =
				new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(5242880);
		multipartResolver.setDefaultEncoding("UTF-8");
		return multipartResolver;
	}
	
	/**
	 * Set PasswordEncoder bean to encode password.
	 * @return
	 */
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Add resource handler so as we can use static resources
	 * like css, js,... files in view.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
        		.addResourceLocations("/resources/");	
	}
	
}
