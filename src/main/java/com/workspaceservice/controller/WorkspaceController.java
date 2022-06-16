package com.workspaceservice.controller;

import com.workspaceservice.dao.WorkspaceDAO;
import com.workspaceservice.dto.AddFilesRequestDTO;
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
import java.util.UUID;

@RestController
@RequestMapping()
public class WorkspaceController {
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private IWorkspaceService workspaceService;


    // GET Methods
    // http://localhost:8080/workspaces
    @GetMapping("workspaces")
    @ResponseStatus(HttpStatus.OK)
    public List<WorkspaceDAO> getAllWorkspaces() {
        return workspaceRepository.findAll();
    }

    // http://localhost:8080/workspace/id/1
    @GetMapping("workspace/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<WorkspaceDAO> getWorkspaceById(@PathVariable String id) {
        return workspaceRepository.findById(UUID.fromString(id));
    }

    // POST Methods
    // http://localhost:8080/workspace

    //    {
    //        "owner": "user6",
    //        "template" : null
    //    }
    @PostMapping("workspace")
    public ResponseEntity<WorkspaceDTO> createWorkspace(@RequestBody NewWorkspaceDTO newWorkspaceDTO) {
        try {
            return new ResponseEntity<>(workspaceService.createWorkspace(newWorkspaceDTO), HttpStatus.CREATED);
        } catch (FileSystemException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/workspace/{workspaceId}/files
    @PostMapping("workspace/{workspaceId}/files")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFiles(@PathVariable String workspaceId, @RequestBody List<AddFilesRequestDTO> addFilesList) throws FileSystemException {
        workspaceService.addFiles(workspaceId, addFilesList);
    }

    // DELETE Methods
    // http://localhost:8080/workspaces
    @DeleteMapping("workspaces")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllWorkspaces() {
        workspaceRepository.deleteAll();
    }

    // http://localhost:8080/workspace/id/1
    @DeleteMapping("workspace/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkspaceById(@PathVariable UUID id) {
        workspaceRepository.deleteById(id);
    }
}
