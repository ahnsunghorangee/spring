package com.inflearn.apigatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApigatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayServiceApplication.class, args);
	}

	/* Actuator 의 httptrace 사용하기 위해선 Bean 등록이 필수 */
	@Bean
	public HttpTraceRepository httpTraceRepository(){
		// 클라이언트가 요청한 트레이스 정보를 메모리에 담아서 우리가 필요할 때 엔드포인트로 httptrace를 호출하면 해당 값을 사용할 수 있다.
		return new InMemoryHttpTraceRepository();
	}
}
