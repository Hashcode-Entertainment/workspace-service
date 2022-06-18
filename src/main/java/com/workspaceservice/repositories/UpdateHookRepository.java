package com.workspaceservice.repositories;

import com.workspaceservice.dao.RepoFileDAO;
import com.workspaceservice.dao.UpdateHookDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateHookRepository extends JpaRepository<UpdateHookDAO, Long> {
}
