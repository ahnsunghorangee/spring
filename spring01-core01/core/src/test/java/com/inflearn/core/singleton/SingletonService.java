package com.inflearn.core.singleton;

public class SingletonService {

    // 1. static 영역에 하나만 생성해서 올려놓는다.
    private static final SingletonService instance = new SingletonService();

    // 2. instance 참조를 꺼낼 때는 getInstance() 하는 방법 뿐, 이 메서드를 호출하면 항상 같은 인스턴스를 반환
    public static SingletonService getInstance() {
        return instance;
    }

    // 3. 딱 1개의 인스턴스만 존재해야 하므로, 생성자를 private으로 막아서 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막는다.
    private SingletonService() {}

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}