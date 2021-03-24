package com.example.project;

import com.example.project.controller.UserRestController;
import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest()
@EnableConfigurationProperties
@EnableAutoConfiguration
@ContextConfiguration(classes = ProjectApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @InjectMocks
    private UserRestController userRestController;
    @Autowired
    private UserRepository userRepository;
    User us = new User();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
    }

    @Test
    public void insertUser() throws Exception {
        us.setFiscalCode("dfg345edg6");
        us.setUsername("Lucio");
        us.setEmail("lucio@gmail.com");
        us.setName("luciano");
        us.setCreation(LocalDateTime.now());
        us.setSurname("violino");
        userRepository.insert(us);
        Assert.assertEquals(userRepository.findByUsername("Lucio").get().getUsername(), "Lucio");
    }

    @Test
    public void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getUsername/Dionis"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAll() throws Exception {
        int i = userRepository.findAll().size();
        Assert.assertEquals(i, 1);
    }

    @Test
    public void delete() throws Exception{
        userRepository.deleteByUsername("Lucio");
        Assert.assertEquals(userRepository.findByUsername("Lucio"), Optional.empty());
    }
}
