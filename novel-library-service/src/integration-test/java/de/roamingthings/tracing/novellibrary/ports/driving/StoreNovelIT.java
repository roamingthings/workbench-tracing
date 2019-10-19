package de.roamingthings.tracing.novellibrary.ports.driving;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("integrationtest")
@AutoConfigureMockMvc
class StoreNovelIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void should_store_a_novel() throws Exception {
        ResultActions performRequest = mockMvc.perform(put("/novels/534009f2-4a02-49ef-8f07-c0a42e2064b2")
                .contentType(APPLICATION_JSON)
                .content("{\n" +
                        "  \"authored\": \"2019-10-19T23:42:00Z\",\n" +
                        "  \"title\": \"The title\",\n" +
                        "  \"content\": \"The text content.\"\n" +
                        "}\n")
        );

        performRequest.andExpect(status().isOk());
    }
}
