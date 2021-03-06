package com.workspaceservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHookDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RepoFileDAO> files = new ArrayList<>();
    private String url;

    public UpdateHookDAO(List<RepoFileDAO> files, String url) {
        this.files = files;
        this.url = url;
    }
}
