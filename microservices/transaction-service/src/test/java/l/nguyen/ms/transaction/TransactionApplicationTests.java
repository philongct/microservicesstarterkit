package l.nguyen.ms.transaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionApplication.class)
public class TransactionApplicationTests {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	private Gson gson;

	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();

		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGenerateTransactions() throws Exception {
		MvcResult result = this.mvc.perform(post("/authcode/abc")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(new CreditCardTransaction("123456", "Buy something", 2.0, null))))
				.andReturn();

		System.out.println("ffsfs");
	}
}
