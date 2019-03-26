package com.wmeimob.custom.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.ui.freemarker.SpringTemplateLoader;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * Created by Shinez on 2017/2/3.
 */
@Configuration
public class FreeMarkerConfig {

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding("utf-8");
        configurer.setPreTemplateLoaders(
                new SpringTemplateLoader(new PathMatchingResourcePatternResolver() {
                    @Override
                    public Resource getResource(String location) {
                        try {
                            Resource[] resources = getResources(location);
                            if (resources.length > 0) return resources[0];
                        } catch (IOException e) {
                        }
                        return super.getResource(location);
                    }
                }, "classpath*:/templates")
        );
        return configurer;
    }
}

