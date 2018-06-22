package br.com.jcsw.math.ws;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(MathController.class)
@ContextConfiguration(classes = TestConfiguration.class)
@AutoConfigureRestDocs(outputDir = "build/snippets")
public class MathControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnSumOperationResult() throws Exception {

    String operation = "SUM";
    String firstParameter = "1.0";
    String secondParameter = "1.5";

    mockMvc.perform(post("/math/operation")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(String
            .format("{\"operation\": \"%s\", \"parameters\": [%s, %s]}", operation, firstParameter, secondParameter)))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo("{\"result\":2.5}")))
        .andDo(document("math"));
  }

}
