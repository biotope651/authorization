package io.common.authorization.resource.program.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.resource.program.dto.request.ReqProgramDTO;
import io.common.authorization.resource.program.dto.response.ResCreateProgramDTO;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProgramControllerTest {

    private String URL = "/resource/program";

    private long programId;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Logger log = (Logger) LoggerFactory.getLogger(ProgramControllerTest.class);

    @Test
    @Order(0)
    @DisplayName("프로그램 생성 - 성공")
    void createProgram() throws Exception {
        ReqProgramDTO.CreateProgramDTO reqProgramDTO = ReqProgramDTO.CreateProgramDTO.builder()
                .programNameKr("테스트")
                .programNameEn("Test Program")
                .programCategory("TP")
                .programStatus(ActiveStatus.ACTIVE)
                .etc("테스트 프로그램")
                .build();

        String contents = objectMapper.writeValueAsString(reqProgramDTO);

        MvcResult result = this.mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(contents))
                .andExpect(status().isOk())
                .andReturn();
        ResCreateProgramDTO resCreateProgramDTO = objectMapper.readValue(result.getResponse().getContentAsString(), ResCreateProgramDTO.class);
        programId = resCreateProgramDTO.getProgramId();
        assertTrue(programId > 0L);
    }
}