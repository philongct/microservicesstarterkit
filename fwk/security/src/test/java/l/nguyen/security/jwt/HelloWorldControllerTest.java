package l.nguyen.security.jwt;

import l.nguyen.security.config.BasicJwtWebSecurityConfigurer;
import l.nguyen.security.support.jwt.JwtTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JwtTestConfig.class)
public class HelloWorldControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldGetUnauthorized() throws Exception {

        this.mvc.perform(get("/api/hello"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldGetForbidden() throws Exception {
        String bearer = login("user", "user");
        this.mvc.perform(get("/api/hello")
                    .header(JwtTokenUtil.HEADER_STRING, bearer)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldGetSuccess() throws Exception {
        String bearer = login("user_abc", "user_abc");
        this.mvc.perform(get("/api/hello")
                .header(JwtTokenUtil.HEADER_STRING, bearer)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    private String login(String username, String password) throws Exception {
        MvcResult result = this.mvc.perform(post("/login").param("username", username).param("password", password)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON))
                .andReturn();
        return result.getResponse().getHeader(JwtTokenUtil.HEADER_STRING);
    }
}

