package com.workspaceservice.dao;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class RepoFileDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String filePath;
}
