package org.duyhung.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Locale;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean("messageSource")
    public MessageSource loadMessageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();

        source.setBasenames(
                "classpath:i18n/home"
        );

        source.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return source;
    }

    @Bean("localeResolver")
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setCookieMaxAge(Duration.ofSeconds(60 * 60));
        cookieLocaleResolver.setCookiePath("/");
        cookieLocaleResolver.setDefaultLocale(new Locale("vi"));
        return cookieLocaleResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor changeInterceptor  = new LocaleChangeInterceptor();
        changeInterceptor.setParamName("language");
        registry.addInterceptor(changeInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/**");
    }
}
