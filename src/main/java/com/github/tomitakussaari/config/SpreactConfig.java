package com.github.tomitakussaari.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.script.ScriptTemplateConfigurer;
import org.springframework.web.servlet.view.script.ScriptTemplateViewResolver;

@Configuration
@EnableWebMvc
public class SpreactConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        ScriptTemplateViewResolver viewResolver = new ScriptTemplateViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".jsx");
        registry.viewResolver(viewResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public ScriptTemplateConfigurer configureScript() {
        ScriptTemplateConfigurer configurer = new ScriptTemplateConfigurer();
        configurer.setEngineName("nashorn");
        configurer.setScripts(
                "/META-INF/resources/webjars/react/15.0.0/react.min.js",
                "/META-INF/resources/webjars/react/15.0.0/react-dom-server.min.js",
                "/META-INF/resources/webjars/babel-standalone/6.7.7/babel.min.js",
                "/js/nashorn-polyfill.js");
        configurer.setRenderFunction("renderJsx");
        configurer.setSharedEngine(false);
        return configurer;
    }
}
