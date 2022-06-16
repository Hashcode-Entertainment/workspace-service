package com.workspaceservice;

import org.eclipse.jgit.http.server.GitServlet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.workspaceservice.git.JGit.resolveRepo;
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
    public ServletRegistrationBean<GitServlet> git(@Qualifier("gitServerRoot") Path gitServerRoot) throws IOException {
        var gitServlet = new GitServlet();
        gitServlet.setRepositoryResolver((req, name) -> resolveRepo(name, gitServerRoot));
        return new ServletRegistrationBean<>(gitServlet, "/git/*");
    }
}
