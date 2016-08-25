package com.github.tomitakussaari.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.script.ScriptTemplateConfigurer;
import org.springframework.web.servlet.view.script.ScriptTemplateView;
import org.springframework.web.servlet.view.script.ScriptTemplateViewResolver;

import javax.script.Invocable;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebMvc
@EnableCaching
public class SpreactConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        ScriptTemplateViewResolver viewResolver = new ScriptTemplateViewResolver() {
            @Override
            protected Class<?> getViewClass() {
                return CachingAndCompilingScriptTemplateView.class;
            }
        };
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
                "/js/render-with-nashorn.js");
        configurer.setRenderFunction("renderWithWithJSONModel");
        configurer.setSharedEngine(false);
        return configurer;
    }

    static class CachingAndCompilingScriptTemplateView extends ScriptTemplateView {

        private static final Map<String, String> templateCache = new ConcurrentHashMap<>();

        @Override
        protected String getTemplate(String path) throws IOException {
            return templateCache.computeIfAbsent(path, p -> {
                try {
                    return ((Invocable) getEngine()).invokeFunction("renderJsx", super.getTemplate(p)).toString();
                } catch (ScriptException | NoSuchMethodException | IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
