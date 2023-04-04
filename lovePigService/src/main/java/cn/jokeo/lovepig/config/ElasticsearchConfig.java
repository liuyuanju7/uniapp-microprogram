package cn.jokeo.lovepig.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author joke
 */
@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        //使用本地es配置
        return RestClients.create(ClientConfiguration.localhost()).rest();
    }


    // @Bean
    // @Override
    // public ElasticsearchCustomConversions elasticsearchCustomConversions() {
    //     List<Converter> converters= new ArrayList<>();
    //     converters.add(DateToLocalDateTimeConverter.INSTANCE);
    //     converters.add(LongToLocalDateTimeConverter.INSTANCE);
    //     return new ElasticsearchCustomConversions(converters);
    // }
    //
    // @ReadingConverter
    // enum LongToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {
    //
    //     INSTANCE;
    //
    //     @Override
    //     public java.time.LocalDateTime convert(Long source) {
    //         return Instant.ofEpochMilli(source).atZone(ZoneId.systemDefault()).toLocalDateTime();
    //     }
    // }
    //
    // @WritingConverter
    // enum DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {
    //
    //     INSTANCE;
    //
    //     @Override
    //     public LocalDateTime convert(Date date) {
    //         Instant instant = date.toInstant();
    //         return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    //     }
    // }

}