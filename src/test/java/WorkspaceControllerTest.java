import com.fasterxml.jackson.databind.ObjectMapper;
import com.workspaceservice.dao.WorkspaceDAO;
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

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    WorkspaceDAO workspace1;
    WorkspaceDAO workspace2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        User user1 = new User("user1");
        User user2 = new User("user2");
        workspace1 = new WorkspaceDAO(UUID.randomUUID(), user1.id(), UUID.randomUUID(), "https://jsdafjsdjklfasd.com/adjfadsj");
        workspace2 = new WorkspaceDAO(UUID.randomUUID(), user2.id(), UUID.randomUUID(), "https://sdvsdvsdvsdv.com/oiajscbh");
        workspaceRepository.saveAll(List.of(workspace1, workspace2));
    }

    @AfterEach
    void tearDown() {
        workspaceRepository.deleteAll();
    }

    @Test
    void getAllWorkspaces() throws Exception {
        MvcResult result = mockMvc.perform(get("/workspace/all"))
                .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("cljdbd"));
        assertTrue(result.getResponse().getContentAsString().contains("aVsdva"));
    }

    @Test
    void getWorkspaceById() throws Exception {
        MvcResult result = mockMvc.perform(get("/workspace/id/{id}", workspace1.getId()))
                .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("cljdbd"));
        assertFalse(result.getResponse().getContentAsString().contains("aVsdva"));
    }

    @Test
    void createWorkspace() throws Exception {
        NewWorkspaceDTO newWorkspaceDTO = new NewWorkspaceDTO(user.id(), "jbancj");
        MvcResult result = mockMvc.perform(post("/workspace")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newWorkspaceDTO)))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("jbancj"));
    }

    @Test
    void addFile() {
        //TODO
    }

    @Test
    void deleteAllWorkspaces() throws Exception {
        MvcResult result = mockMvc.perform(delete("/workspace/all"))
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
