package com.workspaceservice.controller;

import com.workspaceservice.dao.WorkspaceEntity;
import com.workspaceservice.dto.NewWorkspaceDTO;
import com.workspaceservice.dto.WorkspaceDTO;
import com.workspaceservice.exceptions.FileSystemException;
import com.workspaceservice.interfaces.IWorkspaceService;
import com.workspaceservice.repositories.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workspace")
public class WorkspaceController {
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private IWorkspaceService workspaceService;

    // GET Methods
    // http://localhost:8080/workspace/all
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<WorkspaceEntity> getAllWorkspaces() {
        return workspaceRepository.findAll();
    }

    // http://localhost:8080/workspace/id/1
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<WorkspaceEntity> getWorkspaceById(@PathVariable Long id) {
        return workspaceRepository.findById(id);
    }

    // POST Methods
    // http://localhost:8080/workspace
    @PostMapping()
    public ResponseEntity<WorkspaceDTO> createWorkspace(@RequestBody NewWorkspaceDTO newWorkspaceDTO) {
        try {
            return new ResponseEntity<>(workspaceService.createWorkspace(newWorkspaceDTO), HttpStatus.CREATED);
        } catch (FileSystemException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/workspace/user
//    @PostMapping("/{template}/files")
//    @ResponseStatus(HttpStatus.CREATED)
//    public WorkspaceEntity addFile(@PathVariable String template, @RequestBody AddFilesRequestDTO addFilesRequestDTO) {
//        return gitService.addFileToWorkspace(template, addFilesRequestDTO);
//    }

    // DELETE Methods
    // http://localhost:8080/workspace/all
    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllWorkspaces() {
        workspaceRepository.deleteAll();
    }

    // http://localhost:8080/workspace/id/1
    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkspaceById(@PathVariable Long id) {
        workspaceRepository.deleteById(id);
    }
}
