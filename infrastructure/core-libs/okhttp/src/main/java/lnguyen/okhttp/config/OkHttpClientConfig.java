package lnguyen.okhttp.config;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.commons.httpclient.DefaultOkHttpClientFactory;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.cloud.netflix.ribbon.okhttp.OkHttpRibbonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class OkHttpClientConfig {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public OkHttpClientFactory okHttpClientFactory() {
        return new DefaultOkHttpClientFactory() {
            public OkHttpClient.Builder createBuilder(boolean disableSslValidation) {
                // always disable SSL validation
                return super.createBuilder(true);
            }
        };
    }
}
