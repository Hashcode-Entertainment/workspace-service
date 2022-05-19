package com.workspaceservice;

import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;


@SpringBootApplication
public class WorkspaceServiceApplication {

    @Bean
    public ServletRegistrationBean<GitServlet> git() throws IOException {
        var localPath = new File("repos", "repo1.git");
        localPath.deleteOnExit();
        var repository = FileRepositoryBuilder.create(localPath);
        repository.create(true);
        var gitServlet = new GitServlet();
        gitServlet.setRepositoryResolver((req, name) -> {
            repository.incrementOpen();
            return repository;
        });

        System.out.println("Repository name: " + repository.getDirectory().getAbsolutePath());

        return new ServletRegistrationBean<>(gitServlet, "/git/*");
    }

    public static void main(String[] args) {
        SpringApplication.run(WorkspaceServiceApplication.class, args);
    }
}
