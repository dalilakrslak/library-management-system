package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.User;
import ba.unsa.etf.book.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private List<User> users;

    @BeforeEach
    void setUp() {
        user = new User(
                1L,
                "Vildana",
                "Selmanović",
                "vildana@example.com",
                "securePass123",
                "062-123-456"
        );

        users = List.of(user);
    }

    @Test
    void findAll_ShouldReturnListOfUsers() throws Exception {
        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value("vildana@example.com"));
    }

    @Test
    void findById_ShouldReturnUser() throws Exception {
        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Vildana"));
    }

    @Test
    void delete_ShouldReturnConfirmationMessage() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("User with ID 1 was deleted successfully."));
    }

    @Test
    void findByFirstNameAndLastName_ShouldReturnFilteredUsers() throws Exception {
        when(userService.findByFirstNameAndLastName("Vildana", "Selmanović")).thenReturn(users);

        mockMvc.perform(get("/user/search")
                        .param("firstName", "Vildana")
                        .param("lastName", "Selmanović"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("vildana@example.com"));
    }

    @Test
    void findByEmail_ShouldReturnUser() throws Exception {
        when(userService.findByEmail("vildana@example.com")).thenReturn(user);

        mockMvc.perform(get("/user/email")
                        .param("email", "vildana@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Vildana"));
    }

    @Test
    void findAllPaginated_ShouldReturnPagedUsers() throws Exception {
        Page<User> page = new PageImpl<>(users);
        when(userService.getAllUsers(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/user/paginated")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "lastName", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].email").value("vildana@example.com"));
    }

    @Test
    void create_ShouldReturnCreatedUser() throws Exception {
        when(userService.create(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("vildana@example.com"));
    }

    @Test
    void update_ShouldReturnUpdatedUser() throws Exception {
        user.setPhone("061-987-654");
        when(userService.update(any(User.class))).thenReturn(user);

        mockMvc.perform(put("/user/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("061-987-654"));
    }

    @Test
    void createBatch_ShouldReturnCreatedUsers() throws Exception {
        when(userService.createBatch(anyList())).thenReturn(users);

        mockMvc.perform(post("/user/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(users)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("vildana@example.com"));
    }
}
