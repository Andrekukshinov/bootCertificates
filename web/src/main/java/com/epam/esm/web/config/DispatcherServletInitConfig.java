package com.epam.esm.web.config;

import com.epam.esm.persistence.config.PersistenceConfig;
import com.epam.esm.service.config.ServiceConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;

public class DispatcherServletInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    public static final String ALL_REQUESTS = "/";

    @Override
    protected void registerDispatcherServlet(ServletContext servletContext) {
        servletContext.setInitParameter("spring.profiles.active", "dev");
        super.registerDispatcherServlet(servletContext);
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{PersistenceConfig.class, ServiceConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{ALL_REQUESTS};
    }
}
