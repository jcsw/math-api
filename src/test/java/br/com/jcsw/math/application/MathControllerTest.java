package br.com.jcsw.math.application;

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

  private static final String URL = "/math/operation";
  private static final String REQUEST = "{\"operation\": \"%s\", \"parameters\": [%s, %s]}";
  private static final String RESPONSE = "{\"result\":%s}";

  private static final String PATH_DOCS = "math";

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnSumOperationResult() throws Exception {

    String operation = "SUM";
    String firstParameter = "1.0";
    String secondParameter = "1.5";

    String expectedResponse = "2.5";

    mockMvc.perform(post(URL)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(String.format(REQUEST, operation, firstParameter, secondParameter)))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo(String.format(RESPONSE, expectedResponse))))
        .andDo(document(PATH_DOCS));
  }

}
