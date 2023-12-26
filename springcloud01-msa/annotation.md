- @Configuration

  > 다른 빈들보다 우선순위가 먼저 등록된다.

  > 안에 있는 생성자에 @Autowied가 없어도 자동으로 Bean으로 등록돤다.

- @Autowired

  > 생성자로 빈 주입하는게 더 좋다.
  > 사용자에 의해 인스턴스가 만들어지는게 아니라, 스프링 컨텍스트가 기동이 되면서 자동으로 등록할 수 있는 빈을 찾아 메모리에 객체를 자동으로 생성해주는 과정이다.

- @Bean

- @EnableConfigServer
  - Configuration 서버 만들 시 사용
