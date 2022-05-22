package com.workspaceservice.controller;

import com.workspaceservice.dao.Workspace;
import com.workspaceservice.interfaces.IWorkspaceService;
import com.workspaceservice.repositories.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workspace/")
public class WorkspaceController {
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private IWorkspaceService workspaceService;

    // GET Methods
    //  http://localhost:8080/workspace/all
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Workspace> getAllWorkspace() {
        return workspaceRepository.findAll();
    }

    //  http://localhost:8080/workspace/id/1
    @GetMapping("id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Workspace> getWorkspaceById(@PathVariable Long id) {
        return workspaceRepository.findById(id);
    }
}
