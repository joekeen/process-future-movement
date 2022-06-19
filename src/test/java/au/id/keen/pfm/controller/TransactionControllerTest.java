package au.id.keen.pfm.controller;

import au.id.keen.pfm.dto.DailySummary;
import au.id.keen.pfm.dto.JobStatusDto;
import au.id.keen.pfm.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebMvcTest(TransactionController.class)
@RunWith(SpringRunner.class)
@AutoConfigureJsonTesters
public class TransactionControllerTest {

    @MockBean
    private TransactionService transactionService;

    private TransactionController transactionController;

    // This object will be initialized thanks to @AutoConfigureJsonTesters
    @Autowired
    private JacksonTester<List<DailySummary>> jsonDailySummaries;
    @Autowired
    private JacksonTester<JobStatusDto> jsonJobStatus;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        transactionController = new TransactionController(transactionService);

        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testGetJobStatus() throws Exception {
        // given
        long pJobId = 1L;
        JobStatusDto expectedStatus = new JobStatusDto(pJobId, "status", "message", "detailedMessage");
        given(transactionService.getJobStatus(pJobId)).willReturn(expectedStatus);

        // when
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/job/1")).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonJobStatus.write(expectedStatus).getJson());
    }



}