package org.developx.mybatisParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
}
//,excludeAutoConfiguration = SecurityAutoConfiguration.class
)
//@WithMockUser(username = "USER", roles = "USER")
public abstract class WebMvcTestSupport {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;


}
