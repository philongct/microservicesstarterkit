package lnguyen.okhttp;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import lnguyen.okhttp.config.OkHttpClientConfig;
import okhttp3.*;
import org.junit.Test;

public class OkHttpTest {
    @Test
    public void test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url("https://www.google.com.vn")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(call);
                latch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response);
                latch.countDown();
            }
        });

        latch.await();
    }
}
