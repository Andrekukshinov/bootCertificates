package com.epam.esm.web.controller;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.certificate.GiftCertificateTagDto;
import com.epam.esm.service.dto.certificate.GiftCertificatesNoTagDto;
import com.epam.esm.service.service.GiftCertificateService;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


class GiftCertificateControllerTest {
    private static final LocalDateTime DATE = LocalDateTime.parse("2021-04-05T13:45:21");

    private static final GiftCertificateTagDto OBJECT = GiftCertificateTagDto.getBuilder()
            .setId(1L)
            .setCreateDate(DATE)
            .setLastUpdateDate(DATE)
            .setName("name")
            .setDescription("desc")
            .setPrice(new BigDecimal(5))
            .setDuration(3)
            .setTags(new HashSet<>())
            .build();

    private static final GiftCertificatesNoTagDto OBJECT_NO_TAGS = GiftCertificatesNoTagDto.getBuilder()
            .setId(1L)
            .setCreateDate(DATE)
            .setLastUpdateDate(DATE)
            .setName("name")
            .setDescription("desc")
            .setPrice(new BigDecimal(5))
            .setDuration(3)
            .build();

    private static final JavaTimeModule MODULE = new JavaTimeModule();
    private static final ObjectMapper MAPPER = Jackson2ObjectMapperBuilder.json()
            .modules(MODULE)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .build();
    private static final MediaType APPLICATION_JSON = MediaType.APPLICATION_JSON;

    @Mock
    private GiftCertificateService certificateService;

    @InjectMocks
    private GiftCertificateController controller;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(controller)
                .build();
    }

    @Test
    void getGiftCertificateById() throws Exception {
        when(certificateService.getCertificateWithTagsById(1L)).thenReturn(OBJECT);

        mockMvc.perform(get("/api/v1/certificates/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("desc")))
                .andExpect(jsonPath("$.price", is(5)))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.duration", is(3)))
                .andExpect(jsonPath("$.tags", is(new ArrayList<TagDto>())))
                .andExpect(jsonPath("$.createDate", is(List.of(2021, 04, 05, 13, 45, 21))))
                .andExpect(jsonPath("$.lastUpdateDate", is(List.of(2021, 04, 05, 13, 45, 21))));

        verify(certificateService, times(1)).getCertificateWithTagsById(1L);

    }


    @Test
    void testSaveGiftCertificateShouldSaveAndReturnStatusOk() throws Exception {
        byte[] bytes = MAPPER.writeValueAsBytes(OBJECT);
        doNothing().when(certificateService).save(any());

        mockMvc.perform(post("/api/v1/certificates").content(bytes).contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
        verify(certificateService, times(1)).save(OBJECT);

    }

    @Test
    void testDeleteCertificateAndExpectStatusOk() throws Exception {
        mockMvc.perform(delete("/api/v1/certificates/1"))
                .andExpect(status().is2xxSuccessful());

        verify(certificateService, times(1)).deleteCertificate(1L);
    }


    @Test
    void testUpdateCertificateShouldReturnStatusOk() throws Exception {
        byte[] bytes = MAPPER.writeValueAsBytes(OBJECT);

        mockMvc.perform(put("/api/v1/certificates/1").content(bytes).contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
        verify(certificateService, times(1)).updateCertificate(OBJECT, 1L);
    }

    //fixme
//    @Test
//    void testGetByParamShouldReturnListDto() throws Exception {
//        DeprecatedSpecificationDto deprecatedSpecificationDto =
//                new DeprecatedSpecificationDto(SortDirection.ASC, null, "q", "q", "q");
//        when(certificateService.getBySpecification(deprecatedSpecificationDto)).thenReturn(List.of(OBJECT_NO_TAGS, OBJECT_NO_TAGS));
//        MultiValueMap<String, String> map = getRequestSpecificationMap();
//
//        mockMvc.perform(get("/api/v1/certificates")
//                .params(map)
//        )
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$.[0].id", is(1)))
//                .andExpect(jsonPath("$.[0].description", is("desc")))
//                .andExpect(jsonPath("$.[0].price", is(5)))
//                .andExpect(jsonPath("$.[0].name", is("name")))
//                .andExpect(jsonPath("$.[0].duration", is(3)))
//                .andExpect(jsonPath("$.[0].createDate", is(List.of(2021, 04, 05, 13, 45, 21))))
//                .andExpect(jsonPath("$.[0].lastUpdateDate", is(List.of(2021, 04, 05, 13, 45, 21))))
//                .andExpect(jsonPath("$.[1].id", is(1)))
//                .andExpect(jsonPath("$.[1].description", is("desc")))
//                .andExpect(jsonPath("$.[1].price", is(5)))
//                .andExpect(jsonPath("$.[1].name", is("name")))
//                .andExpect(jsonPath("$.[1].duration", is(3)))
//                .andExpect(jsonPath("$.[1].createDate", is(List.of(2021, 04, 05, 13, 45, 21))))
//                .andExpect(jsonPath("$.[1].lastUpdateDate", is(List.of(2021, 04, 05, 13, 45, 21))));
//        verify(certificateService, times(1)).getBySpecification(deprecatedSpecificationDto);
//
//    }

    private MultiValueMap<String, String> getRequestSpecificationMap() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("certificateDescription", List.of("q"));
        map.put("tagName", List.of("q"));
        map.put("certificateName", List.of("q"));
        map.put("createDateSortDir", List.of("ASC"));
        return map;
    }
}
