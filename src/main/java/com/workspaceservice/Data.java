package com.workspaceservice;

import com.workspaceservice.dao.WorkspaceDAO;
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

        User user1 = new User("user1");
        User user2 = new User("user2");
        User user3 = new User("user3");
        User user4 = new User("user4");
        User user5 = new User("user5");

        workspaceRepository.save(new WorkspaceDAO(UUID.randomUUID(), user1.id(), UUID.randomUUID(), "https://jsdafjsdjklfasd.com/adjfadsj"));
        workspaceRepository.save(new WorkspaceDAO(UUID.randomUUID(), user2.id(), UUID.randomUUID(), "https://jsdafjsdjklfasd.com/adjfadsj"));
        workspaceRepository.save(new WorkspaceDAO(UUID.randomUUID(), user3.id(), UUID.randomUUID(), "https://jsdafjsdjklfasd.com/adjfadsj"));
        workspaceRepository.save(new WorkspaceDAO(UUID.randomUUID(), user4.id(), UUID.randomUUID(), "https://jsdafjsdjklfasd.com/adjfadsj"));
        workspaceRepository.save(new WorkspaceDAO(UUID.randomUUID(), user5.id(), UUID.randomUUID(), "https://jsdafjsdjklfasd.com/adjfadsj"));
    }
}
