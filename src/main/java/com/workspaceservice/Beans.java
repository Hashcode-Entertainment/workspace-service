package com.workspaceservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workspaceservice.git.GitServer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import javax.servlet.Servlet;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.workspaceservice.utils.UrlUtils.url;

@Component
public class Beans {
    @Bean
    @Qualifier("gitServerRoot")
    public Path provideGitServerRoot() {
        return Paths.get("test_repos/");
    }

    @Bean
    @Qualifier("gitServer")
    public URL provideGitServerUrl() {
        return url("http://localhost:8080/git/");
    }

    @Bean
    public ServletRegistrationBean<Servlet> git(GitServer gitServer) {
        return new ServletRegistrationBean<>(gitServer.getServlet(), "/git/*");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json().build();
    }
}
