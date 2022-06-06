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
        workspaceRepository.save(new WorkspaceEntity(UUID.randomUUID(), new User("user1"), UUID.randomUUID(), "https://jsdafjsdjklfasd.com/adjfadsj"));
        workspaceRepository.save(new WorkspaceEntity(UUID.randomUUID(), new User("user2"), UUID.randomUUID(), "https://jsdafjsdjklfasd.com/adjfadsj"));
        workspaceRepository.save(new WorkspaceEntity(UUID.randomUUID(), new User("user3"), UUID.randomUUID(), "https://jsdafjsdjklfasd.com/adjfadsj"));
        workspaceRepository.save(new WorkspaceEntity(UUID.randomUUID(), new User("user4"), UUID.randomUUID(), "https://jsdafjsdjklfasd.com/adjfadsj"));
        workspaceRepository.save(new WorkspaceEntity(UUID.randomUUID(), new User("user5"), UUID.randomUUID(), "https://jsdafjsdjklfasd.com/adjfadsj"));
    }
}
