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
