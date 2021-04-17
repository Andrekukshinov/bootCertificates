package com.epam.esm.web.controller;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class TagControllerTest {

    private static final TagDto TAG_DTO = new TagDto(1L, "name");
    private static final MediaType APPLICATION_JSON = MediaType.APPLICATION_JSON;
    private static final JavaTimeModule MODULE = new JavaTimeModule();
    private static final ObjectMapper MAPPER = Jackson2ObjectMapperBuilder.json()
            .modules(MODULE)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .build();

    @Mock
    private TagService service;

    @InjectMocks
    private TagController controller;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(controller)
                .build();
    }

    @Test
    void testSaveTagReturnOkStatus() throws Exception {
        byte[] bytes = MAPPER.writeValueAsBytes(TAG_DTO);

        mockMvc.perform(post("/api/v1/tags").content(bytes).contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
        verify(service, times(1)).saveTag(TAG_DTO);
    }

    @Test
    void testGetTagByIdShouldReturnTagDto() throws Exception {
        when(service.getTag(1L)).thenReturn(TAG_DTO);

        mockMvc.perform(get("/api/v1/tags/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("name")));
        verify(service, times(1)).getTag(1L);
    }

    @Test
    void testDeleteTagShouldReturnOkStatus() throws Exception {
        mockMvc.perform(delete("/api/v1//tags/1"))
                .andExpect(status().is2xxSuccessful());

        verify(service, times(1)).deleteTag(1L);
    }
}
