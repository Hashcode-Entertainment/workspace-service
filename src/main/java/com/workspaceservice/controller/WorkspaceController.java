package com.workspaceservice.controller;

import com.workspaceservice.dao.WorkspaceDAO;
import com.workspaceservice.dto.AddFilesRequestDTO;
import com.workspaceservice.dto.NewWorkspaceDTO;
import com.workspaceservice.dto.WorkspaceDTO;
import com.workspaceservice.exceptions.FileSystemException;
import com.workspaceservice.exceptions.NoSuchWorkspaceException;
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
@RequestMapping("/workspaces")
public class WorkspaceController {
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private IWorkspaceService workspaceService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<WorkspaceDAO> getAllWorkspaces() {
        return workspaceRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<WorkspaceDAO> getWorkspaceById(@PathVariable String id) {
        return workspaceRepository.findById(UUID.fromString(id));
    }
    @PostMapping("")
    public ResponseEntity<WorkspaceDTO> createWorkspace(@RequestBody NewWorkspaceDTO newWorkspaceDTO) {
        try {
            var workspace = workspaceService.createWorkspace(newWorkspaceDTO);
            return new ResponseEntity<>(workspace, HttpStatus.CREATED);
        } catch (FileSystemException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchWorkspaceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/files")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFiles(@PathVariable String id, @RequestBody List<AddFilesRequestDTO> addFilesList) throws FileSystemException {
        workspaceService.addFiles(id, addFilesList);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkspaceById(@PathVariable UUID id) {
        workspaceRepository.deleteById(id);
    }
}
