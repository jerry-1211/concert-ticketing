# Concert Ticketing 프로젝트
많은 양의 트래픽을 받아 문제를 해결해보고자 만든 프로젝트입니다. <br>
다량의 콘서트 티켓을 예약하는 과정에서 발생할 수 있는 문제들을 해결해보았습니다. 


목차 : 내용이 많다면 목차를 추가하여 사용자가 필요한 정보를 빠르게 찾을 수 있도록 한다.







# 콘서트 티켓팅 프로젝트 (Concert Ticketing)

콘서트 티켓을 예약하는 시스템을 구현한 프로젝트입니다. <br> 
콘서트 티켓처럼 동시에 많은 예약 요청이 몰리는 상황을 시뮬레이션하고,<br> 
이 과정에서 발생하는 문제들을 찾아 해결하여 안정적인 예매 시스템을 구축하는 것을 목표로 합니다.


## 📜 목차
1. [프로젝트 개요](https://www.google.com/search?q=%23-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EA%B0%9C%EC%9A%94)
2. [주요 기능](https://www.google.com/search?q=%23-%EC%A3%BC%EC%9A%94-%EA%B8%B0%EB%8A%A5)
3. [트러블 슈팅](https://www.google.com/search?q=%23-%EC%84%A4%EC%B9%98-%EB%B0%8F-%EC%8B%A4%ED%96%89-%EB%B0%A9%EB%B2%95)
4. [시스템 아키텍처](https://www.google.com/search?q=%23%EF%B8%8F-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98)
5. [사용법](https://www.google.com/search?q=%23-%EC%82%AC%EC%9A%A9%EB%B2%95)
6. [테스트](https://www.google.com/search?q=%23-%ED%85%8C%EC%8A%A4%ED%8A%B8)
7. [프로젝트 상태 및 로드맵](https://www.google.com/search?q=%23-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%83%81%ED%83%9C-%EB%B0%8F-%EB%A1%9C%EB%93%9C%EB%A7%B5)
8. [기여 방법](https://www.google.com/search?q=%23-%EA%B8%B0%EC%97%AC-%EB%B0%A9%EB%B2%95)
9. [라이선스](https://www.google.com/search?q=%23-%EB%9D%BC%EC%9D%B4%EC%84%A0%EC%8A%A4)
10. [작성자](https://www.google.com/search?q=%23-%EC%9E%91%EC%84%B1%EC%9E%90)

## 프로젝트 개요

이 프로젝트는 대규모 트래픽이 발생하는 콘서트 예매 시스템에서 발생할 수 있는 복잡한 기술적 문제들을 해결하는 데 중점을 둡니다.<br> 
콘서트 티켓처럼 동시에 많은 예약 요청이 몰리는 상황을 시뮬레이션하고,<br> 
이 과정에서 발생하는 문제들을 찾아 해결하여 안정적인 예매 시스템을 구축하는 것을 목표로 합니다.<br> 

또한, **헥사고날 아키텍처(Hexagonal Architecture)**를 데이터 영역에 도입하여 외부 의존성을 낮추고,<br> 
테스트 커버리지 50% 이상을 유지하며 객체지향 원칙에 따라 테스트하기 좋은 코드를 작성하는 것을 목표로 삼았습니다. <br> 
Spring Security (OAuth2, JWT)를 통한 인증 시스템과 Docker, ngrok, EC2를 활용한 개발 및 테스트 환경을 구축하여 안정성과 신뢰성을 확보했습니다.


## 주요 기능

- **좌석 예약**: 사용자는 좌석 현황을 확인하며 원하는 좌석을 선점하고 예약할 수 있습니다.
- **안전한 결제**: Toss Payments API와 연동하여 결제를 처리하고, 웹훅(Webhook)을 통해 결제 상태를 안정적으로 동기화합니다.
- **사용자 인증**: OAuth 2.0 및 JWT를 통해 Google 계정으로 간편하고 안전하게 로그인할 수 있습니다.


## 트러블 슈팅

| Category | Topic | Detailed Wiki Link |
| :--- | :--- | :--- |
| **Memory** | **대규모 메모리 누수** | **[메인 문서로 이동](https://github.com/jerry-1211/concert-ticketing/wiki/1.-%EB%8C%80%EB%9F%89%EC%9D%98-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EB%88%84%EC%88%98)** |
| | └ Native Memory & FD 소켓 누수 추적 | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/1%E2%80%901.-Native-Memory-&-FD-%EC%86%8C%EC%BC%93-%EB%88%84%EC%88%98-%EC%B6%94%EC%A0%81) |
| | └ Execution (Interpreter, C1/C2, GraalVM) | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/1%E2%80%902.--Execution-(Interpreter,-C1-C2-Complier,-GraalVM)) |
| | └ Java 네이티브 메모리 튜닝 | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/1%E2%80%903.-%EC%9E%90%EB%B0%94-%EB%84%A4%EC%9D%B4%ED%8B%B0%EB%B8%8C-%EB%A9%94%EB%AA%A8%EB%A6%AC-%ED%8A%9C%EB%8B%9D) |
| | └ JVM 튜닝 및 구현체 변경 | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/1%E2%80%904.-JVM-%ED%8A%9C%EB%8B%9D-%EB%B0%8F-JVM-%EA%B5%AC%ED%98%84%EC%B2%B4-%EB%B3%80%EA%B2%BD) |
| | └ Code Level에서의 접근 | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/1%E2%80%905.-Code-Level-%EC%97%90%EC%84%9C-%EC%8B%9C%EB%8F%84) |
| | └ `jemalloc` 프로파일링 & `strace` 추적 | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/1%E2%80%906.-jemalloc-%ED%94%84%EB%A1%9C%ED%8C%8C%EC%9D%BC%EB%A7%81-&-strace-%EC%B6%94%EC%A0%81) |
| | └ `async-profiler` 추적 | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/1%E2%80%907.-async%E2%80%90profiler-%EC%B6%94%EC%A0%81) |
| **Concurrency** | **동시성 문제 (좌석 선점 및 결제)** | **[자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/2.-%EB%8F%99%EC%8B%9C%EC%84%B1-%EB%AC%B8%EC%A0%9C-(%EC%A2%8C%EC%84%9D-%EC%84%9C%EC%A0%90---%EA%B2%B0%EC%A0%9C-%EC%B2%98%EB%A6%AC))** |
| **Performance**| **부하 테스트 병목 현상 (VU↑ RPS↓)** | **[자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/3.-VU-%EC%A6%9D%EA%B0%80-%EC%8B%9C-RPS%EA%B0%80-%EA%B0%90%EC%86%8C%ED%95%98%EB%8A%94-%EB%B6%80%ED%95%98-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EB%B3%91%EB%AA%A9-%ED%98%84%EC%83%81)** |
| **Infra** | **CPU 부하 (DB 레플리케이션)** | **[자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/4.-CPU-%EB%B6%80%ED%95%98-(DB-%EB%A0%88%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98))** |


## 시스템 아키텍처

- **언어**: Java 17
- **프레임워크**: Spring Boot 3.4.5
- **데이터베이스**: MySQL
- **캐시**: Redis
- **메시지 큐**: RabbitMQ
- **인증**: Spring Security, OAuth 2.0, JWT
- **컨테이너화**: Docker, Docker Compose
- **부하 테스트**: k6, InfluxDB, Grafana

## 프로젝트 핵심 로직
| 구분 | 주요 역할 
| :--- | :--- |
| **좌석 선점** | [관련 Wiki로 이동](https://github.com/jerry-1211/concert-ticketing/wiki/%5BFlow%5D-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%95%B5%EC%8B%AC-%EB%A1%9C%EC%A7%81-%ED%94%8C%EB%A1%9C%EC%9A%B0#%EC%A2%8C%EC%84%9D-%EC%84%A0%EC%A0%90-%ED%94%8C%EB%A1%9C%EC%9A%B0) |
| **예약** | [관련 Wiki로 이동](https://github.com/jerry-1211/concert-ticketing/wiki/%5BFlow%5D-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%95%B5%EC%8B%AC-%EB%A1%9C%EC%A7%81-%ED%94%8C%EB%A1%9C%EC%9A%B0#%EC%98%88%EC%95%BD-%ED%94%8C%EB%A1%9C%EC%9A%B0) 
| **결제** | [관련 Wiki로 이동](https://github.com/jerry-1211/concert-ticketing/wiki/%5BFlow%5D-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%95%B5%EC%8B%AC-%EB%A1%9C%EC%A7%81-%ED%94%8C%EB%A1%9C%EC%9A%B0#%EA%B2%B0%EC%A0%9C-%ED%94%8C%EB%A1%9C%EC%9A%B0) 


## 프로젝트 구조
``` yaml
├── TicketingApplication.java
├── concert
│   ├── api
│   ├── application
│   ├── domain
│   └── infrastructure
├── global
│   ├── auth
│   ├── controller
│   ├── exception
│   ├── fake
│   ├── infrastructure
│   └── validation
├── member
│   ├── api
│   ├── application
│   ├── domain
│   └── infrastructure
├── payment
│   ├── api
│   ├── application
│   ├── domain
│   ├── infrastructure
│   └── util
├── reservation
│   ├── api
│   ├── application
│   ├── domain
│   ├── infrastructure
│   └── util
└── seat
    ├── api
    ├── application
    ├── domain
    ├── infrastructure
    └── util
```



## 🚀 설치 및 실행 방법

### 설치 과정

1. **프로젝트 클론**
    
    ```
    git clone [https://github.com/jerry-1211/concert-ticketing.git](https://github.com/jerry-1211/concert-ticketing.git)
    cd concert-ticketing
    ```
    
2. **환경 변수 설정**
프로젝트 루트 디렉터리에 `.env` 파일을 생성하고 아래 예시를 참고하여 내용을 채워주세요.
    
    ```
    # MySQL 데이터베이스 설정
    MYSQL_ROOT_PASSWORD=my-secure-password-123
    MYSQL_DATABASE=myapp-database
    SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/myapp-database
    SPRING_DATASOURCE_PRIMARY_URL=jdbc:mysql://mysql-primary:3306/myapp-database
    SPRING_DATASOURCE_REPLICA_1_URL=jdbc:mysql://mysql-replica:3306/myapp-database
    SPRING_DATASOURCE_REPLICA_2_URL=jdbc:mysql://mysql-replica-sub:3306/myapp-database
    SPRING_DATASOURCE_USERNAME=root
    SPRING_DATASOURCE_PASSWORD=my-secure-password-123
    
    # RabbitMQ 설정
    RABBITMQ_USERNAME=admin
    RABBITMQ_PASSWORD=rabbitmq-secure-123
    
    # 포트 설정
    SERVER_PORT=8080
    MYSQL_PORT=3306
    MYSQL_PRIMARY_PORT=3306
    MYSQL_REPLICA_PORT=3307
    MYSQL_REPLICA_SUB_PORT=3308
    
    NGROK_PORT=4040
    CADVISOR_PORT=8090
    REDIS_PORT=6379
    RABBITMQ_PORT=5672
    RABBITMQ_PORT_UI=15672
    
    # ngrok 설정
    NGROK_AUTHTOKEN=2abcDef3XYZwpi7zpeuclTC47dG_4LR2u3PXRFtzuHk4VP9ik
    NGROK_DOMAIN=myapp.ngrok.app
    
    # Toss Payment 설정 (테스트 환경)
    TOSS_PAYMENTS_SECRET_KEY=test_sk_24xLea5zVAzwY1OOjGExrQAMYNwW
    TOSS_SUCCESS_URL=https://myapp.ngrok.app/payment/success
    TOSS_FAIL_URL=https://myapp.ngrok.app/payment/fail
    TOSS_BASE_URL=https://api.tosspayments.com/v1/payments
    
    # Google OAuth2
    GOOGLE_CLIENT_ID=123456789012-abcdefghijklmnopqrstuvwxyz123456.apps.googleusercontent.com
    GOOGLE_CLIENT_SECRET=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Yz
    GOOGLE_REDIRECT_URI=https://myapp.ngrok.app/login/oauth2/code/google
    GOOGLE_AUTHORIZATION_URI=https://accounts.google.com/o/oauth2/auth?prompt=select_account
    
    # 성공 리다이렉트
    GOOGLE_AUTHORIZED_REDIRECT_URI=https://myapp.ngrok.app/member/oauth2/callback
    
    # JWT 설정
    JWT_SECRET=MyApp-JWT-Secret-Key-Random-String-4bad-9bdd-2b0d7b3dcb6d-secure-2024
    JWT_EXPIRATION=86400000

    ```
    
3. **빌드 및 실행**
Docker Compose를 사용하여 프로젝트의 모든 서비스를 한 번에 빌드하고 실행합니다.
    
    ```
    ./gradlew build 
    docker-compose -f docker-compose.dev.yml up --build
    ```
    
4. **애플리케이션 접속**
웹 브라우저에서 `http://localhost:8080`으로 접속하여 애플리케이션을 확인할 수 있습니다.
> ngork으로 설정한 url도 가능



## 사용법


1. **로그인**: Google 계정을 통해 로그인합니다.
<img width="1002" height="487" alt="스크린샷 2025-08-26 오후 2 35 24" src="https://github.com/user-attachments/assets/41f6d44e-7605-4e92-b62b-e84e13096f1c" />



2. **콘서트 선택**: 메인 화면에서 원하는 콘서트를 선택합니다.
<img width="1146" height="499" alt="스크린샷 2025-08-26 오후 2 49 40" src="https://github.com/user-attachments/assets/cc79c65b-6787-4961-8cf8-cde9b0ea1fd7" />



3. **좌석 선택**: 좌석 배치도에서 원하는 구역(Zone)과 열(Row)을 선택한 후, 예약할 좌석을 클릭합니다.

<img width="1157" height="790" alt="스크린샷 2025-08-26 오후 2 50 18" src="https://github.com/user-attachments/assets/90cfa025-4077-4bb1-b9c6-b3cca2175b7c" />

<img width="1156" height="956" alt="스크린샷 2025-08-26 오후 2 52 30" src="https://github.com/user-attachments/assets/f516232d-41ad-4149-ac81-e40593212944" />



4. **결제 진행**: 선택한 좌석 정보를 확인하고 '결제하기' 버튼을 눌러 Toss Payments를 통해 결제를 완료합니다.
<img width="686" height="685" alt="스크린샷 2025-08-26 오후 2 52 49" src="https://github.com/user-attachments/assets/c7a38e0d-9b0e-4208-b243-c84c345fd390" />

<img width="635" height="845" alt="스크린샷 2025-08-26 오후 2 53 17" src="https://github.com/user-attachments/assets/aed59e55-a16f-44f9-a023-f82ab02033aa" />


5. **예약 확인**: 결제가 완료되면 '마이페이지'에서 예약 내역을 확인할 수 있습니다.
<img width="1014" height="296" alt="스크린샷 2025-08-26 오후 2 53 54" src="https://github.com/user-attachments/assets/0cefab72-33a0-45cb-86ff-b5ae41ef179f" />





## 👨‍💻 작성자

- **김제림**
    - GitHub: [@jerry-1211](https://github.com/jerry-1211)
    - Email: kcjerim1234567@gmail.com
