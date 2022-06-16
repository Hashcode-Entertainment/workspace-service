package com.workspaceservice.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class UpdateHookDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<RepoFileDAO> files = new ArrayList<>();

    private String workspaceUrl;

    public UpdateHookDAO(List<Path> files, String workspaceUrl) {
    }

}
