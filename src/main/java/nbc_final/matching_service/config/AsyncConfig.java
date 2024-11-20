package nbc_final.matching_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

public class AsyncConfig {
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 기본 스레드 풀 크기
        executor.setMaxPoolSize(10); // 최대 스레드 풀 크기
        executor.setQueueCapacity(100); // 작업 큐 크기
        executor.initialize();
        return executor;
    }
}
