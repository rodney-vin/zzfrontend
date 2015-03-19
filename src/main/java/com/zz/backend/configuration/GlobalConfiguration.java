package com.zz.backend.configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import com.zz.backend.util.RunningProfile;
import com.zz.backend.web.sitemesh.SitemeshFilter;

@Configuration
public class GlobalConfiguration {

	@Autowired
	private ErrorAttributes attr;
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer(){
	    return new ErrorCustomizer();
	}

	private static class ErrorCustomizer implements EmbeddedServletContainerCustomizer {

	    @Override
	    public void customize(ConfigurableEmbeddedServletContainer container) {
	        container.addErrorPages(new ErrorPage("/error/error.html"));
	    }

	}
	
	@Bean
	public FilterRegistrationBean sitemesh3() {
	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(new SitemeshFilter());
	    registration.setOrder(2);
	    registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
	    return registration;
	}
	
	@Bean
	@Profile(RunningProfile.Production)
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addAdditionalTomcatConnectors(createSslConnector());
		return tomcat;
	}

	@Value("${my.server.port:8443}")
	private int port;
	
	@Value("${my.server.ssl.key-store-password:changeit}")
	private String key_store_password;
	
	@Value("${my.server.ssl.key-alias:apitestor}")
	private String key_password;
	
	private Connector createSslConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
		try {
			File keystore = getKeyStoreFile();
			File truststore = keystore;
			connector.setScheme("https");
			connector.setSecure(true);
			connector.setPort(port);
			protocol.setSSLEnabled(true);
			protocol.setKeystoreFile(keystore.getAbsolutePath());
			protocol.setKeystorePass(key_store_password);
			protocol.setTruststoreFile(truststore.getAbsolutePath());
			protocol.setTruststorePass(key_store_password);
			protocol.setKeyAlias(key_password);
			return connector;
		}
		catch (IOException ex) {
			throw new IllegalStateException("cant access keystore: [" + "keystore"
					+ "] or truststore: [" + "keystore" + "]", ex);
		}
	}

	private File getKeyStoreFile() throws IOException {
		ClassPathResource resource = new ClassPathResource("keystore");
		try {
			return resource.getFile();
		}
		catch (Exception ex) {
			File temp = File.createTempFile("keystore", ".tmp");
			FileCopyUtils.copy(resource.getInputStream(), new FileOutputStream(temp));
			return temp;
		}
	}
}
