package com.workspaceservice.repositories;

import com.workspaceservice.dao.WorkspaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkspaceRepository extends JpaRepository<WorkspaceEntity, Long> {
    WorkspaceEntity findById (UUID id);
}
