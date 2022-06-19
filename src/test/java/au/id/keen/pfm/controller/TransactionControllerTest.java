package au.id.keen.pfm.controller;

import au.id.keen.pfm.dto.DailySummary;
import au.id.keen.pfm.dto.DailySummaryDto;
import au.id.keen.pfm.dto.JobStatusDto;
import au.id.keen.pfm.enums.DownloadFormatEnum;
import au.id.keen.pfm.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
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

    private static final String TRANSACTION1 = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private JacksonTester<List<DailySummary>> jsonDailySummaries;
    @Autowired
    private JacksonTester<JobStatusDto> jsonJobStatus;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        TransactionController transactionController = new TransactionController(transactionService);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testUploadFile() throws Exception {
        // given
        MockMultipartFile inputFile = new MockMultipartFile("file", "input.txt", "text/plain", TRANSACTION1.getBytes());
        JobStatusDto completedStatus = new JobStatusDto(1L, "COMPLETED", null, null);
        given(transactionService.processFile(inputFile)).willReturn(completedStatus);

        // when
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/upload")
                .file(inputFile)).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonJobStatus.write(completedStatus).getJson());
    }

    @Test
    public void testGetSummaryCSV() throws Exception {
        // given
        List<DailySummary> summaries = List.of(new DailySummaryDto("clientInfo1", "productInfo1", "100"));
        given(transactionService.getSummaryRecords(1L)).willReturn(summaries);

        // when
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/summary/1")
            .param("format", "csv")).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(DownloadFormatEnum.csv.getContentType());
        assertThat(response.getHeader(HttpHeaders.CONTENT_DISPOSITION)).isEqualTo("attachment; filename=Output.csv");
        assertThat(response.getContentAsString())
                .isEqualTo("Client_Information,Product_Information,Total_Transaction_Amount\n" +
                        "clientInfo1,productInfo1,100");
    }

    @Test
    public void testGetSummaryJSON() throws Exception {
        // given
        List<DailySummary> summaries = List.of(new DailySummaryDto("clientInfo1", "productInfo1", "100"));
        given(transactionService.getSummaryRecords(1L)).willReturn(summaries);

        // when
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/summary/1")).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonDailySummaries.write(summaries).getJson());
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