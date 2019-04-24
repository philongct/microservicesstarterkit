/**
 * Copyright (c) 2018 Absolute Software Corporation. All rights reserved.
 * Reproduction or transmission in whole or in part, in any form or by any means,
 * electronic, mechanical or otherwise, is prohibited without the prior written
 * consent of the copyright owner.
 */
package l.nguyen.ms.transactionreporting.config;

import org.springframework.cloud.netflix.feign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import l.nguyen.security.config.oath2.FeignOAuth2Config;

@Configuration
public class TransactionReportingAppConfig extends FeignOAuth2Config {
    @Bean
    public FeignFormatterRegistrar localDateFeignFormatterRegistrar() {
        return formatterRegistry -> formatterRegistry.addFormatter(new Formatter<Date>() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public Date parse(String text, Locale locale) throws ParseException {
                return formatter.parse(text);
            }

            @Override
            public String print(Date object, Locale locale) {
                return formatter.format(object);
            }
        });
    }
}
