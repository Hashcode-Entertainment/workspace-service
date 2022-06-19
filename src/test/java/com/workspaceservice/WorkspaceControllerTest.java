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
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class WorkspaceControllerTest {
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    IWorkspaceService workspaceService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    User user = new User("user");
    private final UUID workspace1Id = UUID.randomUUID();
    private final UUID workspace2Id = UUID.randomUUID();

    WorkspaceDAO workspace1;
    WorkspaceDAO workspace2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        User user1 = new User("user1");
        User user2 = new User("user2");
        workspace1 = new WorkspaceDAO(workspace1Id, user1.id(), UUID.randomUUID(), "https://localhost:8080/" + workspace1Id + ".git", null);
        workspace2 = new WorkspaceDAO(workspace2Id, user2.id(), UUID.randomUUID(), "https://localhost:8080/" + workspace2Id + ".git", null);
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

        assertTrue(result.getResponse().getContentAsString().contains(workspace1.getUrl()));
        assertTrue(result.getResponse().getContentAsString().contains(workspace2.getUrl()));
    }

    @Test
    void getWorkspaceById() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/workspaces/{id}", workspace1.getId()))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(workspace1.getUrl()));
        assertFalse(result.getResponse().getContentAsString().contains(workspace2.getUrl()));
    }

    @Test
    void createWorkspace() throws Exception {
        var newWorkspaceDTO = new NewWorkspaceDTO(user.id(), null, null);
        MvcResult result = mockMvc.perform(post("/workspaces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newWorkspaceDTO)))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(user.id()));
    }
    @Test
    void addFile() throws Exception {

        var newWorkspaceDTO = new NewWorkspaceDTO(user.id(), null, null);
        var workspaceDTO = workspaceService.createWorkspace(newWorkspaceDTO);
        var workspaceId = workspaceDTO.getId().toString();

        List<AddFilesRequestDTO> addFilesList = new ArrayList<>();
        addFilesList.add(new AddFilesRequestDTO("src/file1.txt", "Task 1"));
        addFilesList.add(new AddFilesRequestDTO("task.yaml", "{ ... }"));

        MvcResult result = mockMvc.perform(post("/workspaces/{workspaceId}/files", workspaceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addFilesList)))
                .andExpect(status().isCreated()).andReturn();

        assertEquals(201, result.getResponse().getStatus());

//        var path = "test_repos/" + workspaceId + "/" + addFilesList.get(1).getPath();
//        File file = new File(path);
//        assertTrue(file.exists());
    }

    @Test
    void deleteWorkspaceById() throws Exception {
        mockMvc.perform(delete("/workspaces/{id}", workspace1.getId()))
                .andExpect(status().isNoContent()).andReturn();

        assertNotEquals(2, workspaceRepository.findAll().size());
        assertEquals(1, workspaceRepository.findAll().size());
    }
}