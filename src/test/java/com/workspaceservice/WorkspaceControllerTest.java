package com.workspaceservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workspaceservice.dao.WorkspaceDAO;
import com.workspaceservice.dto.AddFilesRequestDTO;
import com.workspaceservice.dto.NewWorkspaceDTO;
import com.workspaceservice.interfaces.IWorkspaceService;
import com.workspaceservice.repositories.WorkspaceRepository;
import com.workspaceservice.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class WorkspaceControllerTest {
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    IWorkspaceService workspaceService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    User user = new User("user");
    private UUID workspace1Id = UUID.randomUUID();
    private UUID workspace2Id = UUID.randomUUID();

    WorkspaceDAO workspace1;
    WorkspaceDAO workspace2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        User user1 = new User("user1");
        User user2 = new User("user2");
        workspace1 = new WorkspaceDAO(workspace1Id, user1.id(), UUID.randomUUID(), "https://localhost:8080/" + workspace1Id + ".git");
        workspace2 = new WorkspaceDAO(workspace2Id, user2.id(), UUID.randomUUID(), "https://localhost:8080/" + workspace1Id + ".git");
        workspaceRepository.saveAll(List.of(workspace1, workspace2));
    }

    @AfterEach
    void tearDown() {
        workspaceRepository.deleteAll();
    }

    @Test
    void getAllWorkspaces() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/workspaces"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("https://localhost:8080/" + workspace1Id + ".git"));
        assertTrue(result.getResponse().getContentAsString().contains("https://localhost:8080/" + workspace2Id + ".git"));
    }

    @Test
    void getWorkspaceById() throws Exception {
        MvcResult result = mockMvc.perform(get("/workspace/id/{id}", workspace1.getId()))
                .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("https://localhost:8080/" + workspace1Id + ".git"));
        assertFalse(result.getResponse().getContentAsString().contains("https://localhost:8080/" + workspace2Id + ".git"));
    }

    @Test
    void createWorkspace() throws Exception {
        var template = UUID.randomUUID().toString();
        var newWorkspaceDTO = new NewWorkspaceDTO(user.id(), template);
        MvcResult result = mockMvc.perform(post("/workspace")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newWorkspaceDTO)))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(user.id()));
        assertTrue(result.getResponse().getContentAsString().contains(template));
    }

    //Spróbować przetestować poprzez dodanie workspace'ów poprzez RESTa, a nie bezpośrednio przez dodanie do DB
    @Test
    void addFile() throws Exception {
        var workspaceId = workspace1Id.toString();
        List<AddFilesRequestDTO> addFilesList = new ArrayList<>();
        addFilesList.add(new AddFilesRequestDTO("src/file1.txt", "Task 1"));
        addFilesList.add(new AddFilesRequestDTO("task.yaml", "{ ... }"));

        MvcResult result = mockMvc.perform(post("/workspace/{workspaceId}/files", workspaceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addFilesList)))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getStatus() == 201);

        var path = "test_repos/" + workspaceId + "/" + addFilesList.get(1).getPath();
        File file = new File(path);

        assertTrue(file.exists());
    }

    @Test
    void deleteAllWorkspaces() throws Exception {
        MvcResult result = mockMvc.perform(delete("/workspaces"))
                .andExpect(status().isNoContent()).andReturn();

        assertFalse(result.getResponse().getContentAsString().contains("cljdbd"));
        assertFalse(result.getResponse().getContentAsString().contains("aVsdva"));
    }

    @Test
    void deleteWorkspaceById() throws Exception {
        MvcResult result = mockMvc.perform(delete("/workspace/id/{id}", workspace1.getId()))
                .andExpect(status().isNoContent()).andReturn();

        assertNotEquals(2, workspaceRepository.findAll().size());
        assertEquals(1, workspaceRepository.findAll().size());
    }
}
