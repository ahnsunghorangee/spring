# 인터넷 프로토콜 4계층

    1. 프로그램이 Hello, World! 메시지 생성
        - 전송 데이터 (HTTP 메시지)

(App) 애플리케이션 계층 - HTTP, FTP

    2. (App) Socket 라이브러리를 통해 전달
        - TCP/IP 연결 (IP, Port)
        - 데이터 전달

(OS) 전송 계층 - TCP, UDP

    3. TCP 정보 생성, 메시지 데이터 포함
        - TCP 세그먼트: 출발지 Port, 목적지 Port, 전송 제어, 순서, 검증 정보 등

(OS) 인터넷 계층 - IP

    4. IP 패킷 생성, TCP 데이터 포함
        - IP 패킷: 출발지 IP, 목적지 IP, 기타 등

(HW) 네트워크 인터페이스 계층

    5. 인터넷 -> 서버

# IP (인터넷 프로토콜)

- 역할

  - 지정한 IP 주소(IP Address)에 데이터 전달
  - 패킷(Packet) 통신 단위로 데이터 전달

- 한계

  - 비연결성: 패킷 받을 대상 불능
  - 비신뢰성: 패킷 사라짐, 순서 안 맞음
  - 프로그램 구분: 같은 IP가 둘 이상

- 해결방법

  ## TCP (Transmission Control Protocol, 전송 제어 프로토콜)

  - 연결지향 - TCP 3 way handshake (가상 연결)
    - SYN > SYN + ACK > ACK
    - SYN: 접속 요청
    - ACK: 요청 수락
  - 데이터 전달 보증
  - 순서 보장
  - 신뢰할 수 있는 프로토콜
  - 현재 대부분은 TCP 사용

  ## UDP (User Datagram Protocol, 사용자 데이터그램 프로토콜)

  - 데이터 전달 및 순서가 보장되지 않지만 단순하고 빠름
  - IP와 거의 같다. **PORT** 체크섬 정도만 추가되어 있다.
    - 여러 애플리케이션 데이터가 들어올 때, 포트로 구분할 수 있다.
  - 애플리케이션에서 추가 작업 필요

  ## PORT

  - IP는 목적지 서버를 찾는 것, PORT는 서버 안에서 프로세스(애플리케이션)을 분류

  ## DNS (Domain Name Ssytem)

  - 도메인 명을 IP 주소로 변환

# URI (Uniform Resource Identifier)

- URI는 URL, URN 모두를 포함
- URL (Uniform Resource Locator): 리소스가 있는 위치를 지정
- URN (Uniform Resource Name): 리소스에 이름을 부여

# HTTP (HyperText Transfer Protocol)

- HTTP 메시지에 모든 것을 전송 (HTML, Text, Image, JSON 등)
- HTTP/1.1(RFC7230~7235): 우리가 사용하는 버전 (TCP 기반)
- 클라이언트-서버 구조 (Request-Response 구조)
- **무상태(Stateless) 프로토콜** (<-> Stateful, 상태 유지)
  - 서버가 클라이언트의 상태를 보존하지 않는다.
  - 장점
    - 서버 확장성 높음(무한한 서버 증설 가능) (스케일 아웃: 수평 확장 유리)
  - 단점
    - 클라이언트가 추가 데이터 전송
  - 상태 유지를 사용하는 경우
    - 로그인한 사용자의 경우 로그인 했다는 상태를 서버에 유지
    - 일반적으로 브라우저 쿠키와 서버 세션 등을 사용해서 상태 유지
    - 상태 유지는 최소한만 사용
- **비연결성(connectionless)**
  - 서버와 클라이언트는 연결을 유지하지 않는다. (최소한의 자원 유지)
    1. TCP/IP 연결
    2. 클라이언트: 요청
    3. 서버: 응답
    4. TCP/IP 연결 종료
  - 단점
    - TCP/IP 연결을 매번 새로 맺어야 함 (3 way handshake 시간 추가)
    - HTML뿐 아니라 JS, CSS, 이미지 등 함께 다운로드 해야함.
  - 지속 연결(Persistent Connections)로 단점 해결
- HTTP 메시지 구조
  - start-line, 시작 라인
    - Request: HTTP 메서드(GET, POST 등), 요청 대상, HTTP Version
    - Response: HTTP 버전, HTTP 상태 코드(200, 400, 500 등)
  - header, 헤더
    - HTTP 전송에 필요한 모든 부가정보
  - empty line, 공백 라인 (CRLF)
  - message body
    - 실제 전송할 데이터
    - HTML, 이미지, 영상 등 byte로 표현할 수 있는 모든 데이터 전송 가능
- 단순함, 확장가능

# HTTP 메서드

- API URI는 리소스로 식별
- 계층 구조상 상위를 컬렉셕으로 보고 복수단어 사용 권장
- 리소스만으로 안 되는 경우: 컨트롤 URI

  - 예) POST /orders/{orderId}/start-delivery (리소스/리소스 식별자/동사)

- 주요 메서드

  - GET: 리소스 조회
    - 서버에 전달하고 싶은 데이터는 query(쿼리 파라미터, 쿼리 스트링)을 통해 전달
    - 메시지 바디를 사용해서 데이터를 전달할 수 있지만, 지원하지 않는 곳이 많아 권장하지 않음
  - POST: 요청 데이터 처리, 주로 등록에 사용
    - 메시지 바디를 통해 서버로 요청 데이터 전달
    - 서버는 요청 데이터를 처리: 메시지 바디를 통해 들어온 데이터를 처리하는 모든 기능을 수행한다.
    - 처리 후 응답 데이터로 **Location**: /members/100 처럼 서버가 새로 등록된 리소스 URI를 생성해준다.
    - 단순히 데이터를 생성하거나, 변경하는 것을 넘어서 **프로세스를 처리해야 하는 경우 사용**
    - GET으로 조회데이터를 넘기기 어려운 경우도 가능 (특수한 경우)
      - 단, 조회는 GET으로 사용하는 것이 좋다. (서버끼리 약속하고 캐싱을 한다.)
    - 결론, POST는 모든 것이 가능
  - PUT: 리소스가 있으면 대체, 없으면 생성
    - POST와 차이점: 클라이언트가 리소스를 식별할 줄 안다. (/members/{memberId})
  - PATCH: 리소스 부분 변경
    - PUT과 차이점: PUT은 리소스를 대체하는 것으로, 이전에 존재했지만, 값을 안 주면 빈칸으로 업데이트 되지만, PATCH는 값을 안 주면 이전에 존재한 값을 유지하고 받은 값만 업데이트한다.
  - DELETE: 리소스 삭제

- 기타 메서드

  - HEAD: GET과 동일하지만 메시지 부분을 제외하고, 상태 줄과 헤더만 반환
  - OPTIONS: 대상 리소스에 대한 통신 가능 옵션(메서드)을 설명 (주로 CORS에서 사용)
  - CONNECT: 대상 자원으로 식별되는 서버에 대한 터널을 설정
  - TRACE: 대상 리소스에 대한 경로를 따라 메시지 루프백 테스트를 수행

- HTTP 메서드의 속성

<center>
	<p align="center"><img src="./_asset/image01.png"></p>
</center>

- 안전(Safe Methods)

  - 호출해도 리소스를 변경하지 않는다.

- 멱등(Idempotent Methods)

  - 같은 요청을 한 번 호출하든 100번 호출하든 결과가 똑같다.
  - POST는 멱등이 아니다. 예) 결제 요청을 여러번 날리면 중복 결제된다.
  - 활용
    - 자동 복구 메커니즘
    - Response가 없을 때 클라이언트가 다시 요청해도 되는지 판단 근거

- 캐시가능(Cacheable Methods)

  - 실제로는 GET, HEAD 정도만 가능하다. POST, PATCH는 캐시 키를 고려해야하는데 구현이 쉽지 않다.

# HTTP 메서드 활용

- 클라이언트에서 서버로 데이터 전송 방법

  - 방법1) 쿼리 파라미터

    - GET, 정렬 필터(검색어) 등

  - 방법2) 메시지 바디
    - POST, PUT, PATCH, 회원 가입, 상품 주문, 리소스 등록, 리소스 변경 등

- 클라이언트에서 서버로 데이터 전송 상황

  - 상황1) 정적 데이터 조회 (이미지, 텍스트 등)

    - GET
    - 예) /static/star.jpg

  - 상황2) 동적 데이터 조회 (검색, 정렬 필터 등)

    - GET
    - 예) /search?q=hello&hl=ko

  ```html
  <form action="/save" method="post">
    <input type="text" name="username" />
    <input type="text" name="age" />
  </form>
  ```

  - 상황3) HTML Form을 통한 데이터 전송

    - POST
    - GET도 사용가능 하지만 X (사용할 경우 URI에 자동으로 쿼리 스트링으로 넣어준다.)
    - Content-Type: application/x-www-form-urlencoded
      - default로 사용
      - Form 데이터를 메시지 바디에 넣어준다.
    - Content-Type: multipart/form-data
      - 바이너리 데이터를 전송할 때 사용

  - 상황4) HTTP API를 통한 데이터 전송

    - Content-Type: application/json을 주로 사용 (사실상 표준)
    - TEXT, XML, JSON 등

- HTTP API 설계 예시

  - 예시1) HTTP API - 컬렉션

    - POST 기반 등록
    - 서버가 관리하는 리소스 디렉토리
    - 서버가 리소스의 URI를 생성하고 관리

  - 예시2) HTTP API - 스토어

    - PUT 기반 등록
    - 클라이언트가 관리하는 리소스 저장소
    - 클라이언트가 리소스의 URI를 알고 관리

  - 예시3) HTML Form 사용

    - GET, POST만 지원
    - 컨트롤 URI (동사)

# HTTP Status Code

상태코드: 클라이언트가 보낸 요청의 처리 상태를 응답에서 알려주는 기능

- 1xx(Informational): 요청이 수신되어 처리중
- 2xx(Successful): 요청 정상 처리
  - 200 OK: 요청 성공
  - 201 Created: 요청 성공해서 새로운 리소스가 생성됨
  - 202 Accepted: 요청이 접수되었으나 처리가 완료되지 않았음, 예시로 배치
  - 204 No Content: 서버가 요청을 성공적으로 수행했지만, 응답 페이로드 본문에 보낼 데이터가 없음
- 3xx(Redirection): 요청을 완료하기 위해 유저 에이전트(웹 브라우저)의 추가 조치 필요

  - Redirection이란? 웹 브라우저는 3xx 응답의 결과에 Location 헤더가 있으면, Location 위치로 자동 이동
  - 영구 리다이렉션: 특정 리소스의 URI가 영구적으로 이동
    - 301 Moved Permanently: 리다이렉트 시 요청 메서드가 GET으로 변하고, 본문이 제거될 수 있음(MAY)
    - 308 Permanent Redirect: 리다이렉트 시 요청 메서드와 본문 유지(처음 POST를 보내면 리다이렉트도 POST 유지)
  - 일시 리다이렉션: 일시적인 변경
    - 302 Found: 리다이렉트 시 요청 메서드가 GET으로 변하고, 본문이 제거될 수 있음(MAY)
    - 307 Temporary Redirect: 리다이렉트 시 요청 메서드와 본문 유지(요청 메서드를 변경하면 안된다. MUST NOT)
    - 303 See Other: 리다이렉트 시 요청 메서드가 GET으로 변경
    - PRG: Post/Redirect/Get:
  - 특수 리다이렉션: 결과 대신 캐시를 사용
    - 300 Multiple Choices: 안 씀
    - 304 Not Modified: 캐시를 목적으로 사용
      - 클라이언트에게 리소스가 수정되지 않았음을 알려준다. 따라서 클라이언트는 로컬PC에 저장된 캐시를 재사용한다. (캐시로 리다이렉트 한다.)
      - 304 응답은 응답에 메시지 바디를 포함하면 안된다. (로컬 캐시를 사용해야 하므로)
      - 조건부 GET, HEAD 요청 시 사용

- 4xx(Client Error): 클라이언트 오류, 잘못된 문법 등으로 서버가 요청을 수행할 수 없음 (오류의 원인이 클라이언트에 있음)
  - 클라이언트가 이미 잘못된 요청
  - 데이터를 보내고 있기 때문에 똑같은 재시도가 실패함
  - 400 Bad Request: 클라이언트가 잘못된 요청을 해서 서버가 요청을 처리할 수 없음
  - 401 Unauthorized: 클라이언트가 해당 리소스에 대한 인증이 필요함
  - 403 Forbidden: 서버가 요청을 이해했지만 승인을 거부함
  - 404 Not Found: 요청 리소스를 찾을 수 없음
- 5xx(Server Error): 서버 오류, 서버가 정상 요청을 처리하지 못함
  - 500 Internal Server Error: 서버 문제로 오류 발생, 애매하면 500 오류
  - 503 Service Unavailable: 서비스 이용 불가
- 4xx vx 5xx의 차이
  > 4xx는 복구 불가능(요청 자체에 문제가 있어서 요청을 수정해서 보내야 하고), 5xx는 클라이언트가 똑같은 요청을 보냈어도 성공할 가능성이 있다(서버가 내려가 있을 수도 있어서).

# HTTP Header

용도

- HTTP 전송에 필요한 모든 부가정보

Representation(표현) = Representation Metadata(표현 메타데이터) + Representation Data(표현 데이터)

HTTP Body

- 메시지 본문을 통해 표현 데이터 전달
- 메시지 본문 = 페이로드(payload)
- 표현(표현 헤더 + 표현 데이터)은 요청이나 응답에서 전달할 실제 데이터
- 표현 헤더는 표현 데이터를 해석할 수 있는 정보 제공

표현(Representation) 헤더

- Content-Type: 표현 데이터의 형식
  - 미디어 타입, 문자 인코딩 등
- Content-Encoding: 표현 데이터의 압축 방식
  - 표현 데이터를 압축하기 위해 사용
  - 데이터를 전달하는 곳에서 압축 후 인코딩 헤더 추가
  - 데이터를 읽는 쪽에서 인코딩 헤더의 정보로 압축 해제
- Content-Language: 표현 데이터의 자연 언어
  - 표현 데이터의 자연 언어를 표현
- Content-Length: 표현 데이터의 길이

  - 바이트 단위

- 표현 헤더는 전송, 응답 모두 사용

협상(콘텐츠 네고시에이션) 헤더

> 협상 헤더란? 클라이언트가 선호하는 표현 요청

- Accept: 클라이언트가 선호하는 미디어 타입 전달
- Accept-Charset: 클라이언트가 선호하는 문자 인코딩
- Accept-Encoding: 클라이언트가 선호하는 압축 인코딩
- Accept-Language: 클라이언트가 선호하는 자연 언어

- 협상 헤더는 요청시에만 사용

- 협상과 우선순위 (Quality Values(q))

  - 0~1, 클수록 높은 우선순위
  - 생략하면 1
  - 예) Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7

    1. ko-KR;q=1 (q생략)
    2. ko;q=0.9
    3. en-US;q=0.8
    4. en:q=0.7

  - 구체적인 것이 우선한다.
  - 예) Accept: text/\*, text/plain, text/plain;format=flowed, \*/\*
    1. text/plain;format=flowed
    2. text/plain
    3. text/\*
    4. \*/\*

전송 방식

- Content-Length, 단순 전송: Content-Length 포함해서 전달
- Content-Encoding, 압축 전송: Content-Encoding 포함해서 전달
- Transfer-Encoding, 분할 전송: Transfer-Encoding: chunked (단, Content-Length는 보내면 안된다. 분할해서 총 길이를 몰라서)
- Range, Content-Range, 범위 전송: Content-Range 포함해서 전달

일반 정보

- From: 유저 에이전트의 이메일 정보
- Referer: 이전 웹 페이지 주소
- User-Agent: 유저 에이전트 애플리케이션 정보 (웹 브라우저 정보 등)
- Server: 요청을 처리하는 ORIGIN 서버(Proxy서버 말고 나의 요청을 실제로 응답해주는 서버)의 소프트웨어 정보
- Date: 메시지가 생성된 날짜

특별한 정보

- HOST: 요청한 호스트 정보(도메인)
- Location: 페이지 리다이렉션
- Allow: 허용 가능한 HTTP 메서드
- Retry-After: 유저 에이전트가 다음 요청을 하기까지 기다려야 하는 시간

인증

- Authorization: 클라이언트 인증 정보를 서버에 전달
- WWW-Authenticate: 리소스 접근 시 필요한 인증 방법 정의

쿠키

- Set-Cookie: 서버에서 클라이언트로 쿠키 전달(응답)
  - 브라우저에는 **쿠키 저장소**가 있는데 그곳에 저장한다.
  - 브라우저는 서버에 요청을 보낼 때 쿠키를 뒤져서 쿠키를 함께 서버로 보낸다. (모든 요청에 쿠키 정보 자동 포함)
- Cookie: 클라이언트가 서버에서 받은 쿠키를 저장하고, HTTP 요청 시 서버로 전달

  - HTTP는 무상태(Statelee) 프로토콜로 클라이언트와 서버가 요청과 응답을 주고 받으면 연결이 끊어진다. 클라이언트가 다시 요청하면 서버는 이전 요청을 기억하지 못한다.

- 쿠키는 생명주기, 사용할 도메인(하위 도메인), 경로(하위 경로), 보안 유무를 판별하여 쿠키 사용 범위를 설정할 수 있다.
