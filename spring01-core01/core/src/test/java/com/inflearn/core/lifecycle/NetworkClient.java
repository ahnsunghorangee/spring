package com.inflearn.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {
//        implements InitializingBean, DisposableBean {
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect() {
        System.out.println("connect = " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    // 서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close = " + url);
    }

    /*
    // [인터페이스 InitializingBean, DisposableBean]
    // 1. 싱글톤으로 컨테이너에 빈이 생성, 의존관계 주입된다.
    @Override // implements InitializingBean
    public void afterPropertiesSet() throws Exception { // 2. 의존관계 주입이 끝나면 호출된다.
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    @Override // implements DisposableBean
    public void destroy() throws Exception { // 3. 빈 종료될 때 호출
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
    */

    // [빈 등록 초기화, 소멸 메서드]
    @PostConstruct
    public void init() { // 의존관계 주입이 끝나면 호출된다.
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() { // 빈 종료될 때 호출
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
}
