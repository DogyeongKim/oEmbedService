package com.kdk.embed;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertyConfig {
	
	@Bean(name="config")
	public PropertiesFactoryBean propertiesFactoryBean() throws Exception{
		
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		ClassPathResource classPathResource = new ClassPathResource("com/properties/config.properties");
		
		propertiesFactoryBean.setLocation(classPathResource);
		
		return propertiesFactoryBean;
		
	}

}
