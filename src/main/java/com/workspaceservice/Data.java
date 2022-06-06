package com.workspaceservice;

import com.workspaceservice.dao.WorkspaceEntity;
import com.workspaceservice.repositories.WorkspaceRepository;
import com.workspaceservice.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Data {
    @Autowired
    private WorkspaceRepository workspaceRepository;

    public void populate() {
        workspaceRepository.save(new WorkspaceEntity(UUID.randomUUID(), new User("user1"), "cljdbd"));
        workspaceRepository.save(new WorkspaceEntity(UUID.randomUUID(), new User("user2"), "aVsdva"));
        workspaceRepository.save(new WorkspaceEntity(UUID.randomUUID(), new User("user3"), "sadvaf"));
        workspaceRepository.save(new WorkspaceEntity(UUID.randomUUID(), new User("user4"), "mghmfg"));
        workspaceRepository.save(new WorkspaceEntity(UUID.randomUUID(), new User("user5"), "sbhsbd"));
    }
}
