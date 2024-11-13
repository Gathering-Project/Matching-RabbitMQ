//package nbc_final.matching_service.config;
////
////import org.apache.kafka.clients.consumer.ConsumerConfig;
////import org.apache.kafka.clients.producer.ProducerConfig;
////import org.apache.kafka.common.serialization.StringDeserializer;
////import org.apache.kafka.common.serialization.StringSerializer;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.kafka.annotation.EnableKafka;
////import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
////import org.springframework.kafka.core.*;
////
////import java.util.HashMap;
////import java.util.Map;
////
////@Configuration
////@EnableKafka
////public class KafkaConfig {
////
////    @Bean
////    public KafkaTemplate<String, Object> kafkaTemplate(){
////        return new KafkaTemplate<String, Object>(producerFactory());
////    };
////
////    @Bean
////    public ProducerFactory<String, Object> producerFactory() {
////        Map<String, Object> myconfig = new HashMap<>();
////        myconfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
////                "127.0.0.1:19092, 127.0.0.1:19093, 127.0.0.1:19094");
////        myconfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
////        myconfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
////
////        return new DefaultKafkaProducerFactory<>(myconfig);
////    }
////
////    @Bean
////    public ConsumerFactory<String, Object> consumerFactory() {
////        Map<String, Object> myConfig = new HashMap<>();
////        myConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
////                "127.0.0.1:19092, 127.0.0.1:19093, 127.0.0.1:19094");
////        myConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
////        myConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
////        return new DefaultKafkaConsumerFactory<>(myConfig);
////    }
////
////    @Bean
////    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
////        ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
////        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
////        return kafkaListenerContainerFactory;
////    }
////}
//
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.*;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableKafka
//public class KafkaConfig {
//
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean
//    public ProducerFactory<String, Object> producerFactory() {
//        Map<String, Object> myconfig = new HashMap<>();
//        myconfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:19092,127.0.0.1:19093,127.0.0.1:19094");
////        myconfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9092,kafka2:9093,kafka3:9094");
//        myconfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        myconfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // MatchingRequestDto의 직렬화를 위한 JsonSerializer 추가
//        return new DefaultKafkaProducerFactory<>(myconfig);
//    }
//
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        Map<String, Object> myConfig = new HashMap<>();
//        myConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:19092,127.0.0.1:19093,127.0.0.1:19094");
////        myConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9092,kafka2:9093,kafka3:9094");
//        myConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        myConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // MatchingRequestDto의 역직렬화를 위한 JsonDeserializer 추가
//        myConfig.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // 모든 패키지 신뢰 (또는 특정 패키지 명시)
//        return new DefaultKafkaConsumerFactory<>(myConfig, new StringDeserializer(), new JsonDeserializer<>(Object.class));
//
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
//        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
//        return kafkaListenerContainerFactory;
//    }
//
//}