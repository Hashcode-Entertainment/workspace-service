package com.workspaceservice.repositories;

import com.workspaceservice.dao.RepoFileDAO;
import com.workspaceservice.dao.WorkspaceDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepoFileRepository extends JpaRepository<RepoFileDAO, Long> {
}
