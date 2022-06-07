package com.workspaceservice.repositories;

import com.workspaceservice.dao.WorkspaceDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkspaceRepository extends JpaRepository<WorkspaceDAO, UUID> {
    Optional<WorkspaceDAO> findById (UUID id);
}
