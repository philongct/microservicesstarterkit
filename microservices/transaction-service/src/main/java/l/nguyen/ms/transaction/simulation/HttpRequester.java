package l.nguyen.ms.transaction.simulation;

import java.io.IOException;
import java.util.function.Function;

import okhttp3.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;

public class HttpRequester {

    private static final String URL = "http://localhost:6000/transaction/authcode/";

    private OkHttpClient httpClient;
    private String authHeader;

    private ObjectMapper objectMapper = new ObjectMapper();

    public HttpRequester(OkHttpClient httpClient, String authHeader) {
        this.httpClient = httpClient;
        this.authHeader = authHeader;
    }

    public void request(String bank, CreditCardTransaction transaction, Function<Object, Void> callback) throws IOException {
        byte[] content = objectMapper.writeValueAsBytes(transaction);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), content);
        Request req = new Request.Builder()
                .url(URL + bank)
                .post(body)
                .header("Authorization", authHeader)
                .build();

        httpClient.newCall(req).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                callback.apply(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.apply(response);
            }
        });
    }
}
