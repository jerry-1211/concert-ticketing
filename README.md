# 🎟️ 콘서트 티켓팅 프로젝트 (Concert Ticketing)

콘서트 티켓을 예약하는 시스템을 구현한 프로젝트로, 동시에 많은 예약 요청이 몰리는 상황을 시뮬레이션합니다.<br> 
그리고 이 과정에서 발생하는 **문제들을 찾아 해결하여 안정적인 예매 시스템을 구축하는 것을 목표**로 합니다.

## 목차

| 번호 | 섹션 | 설명 |
|:---:|:---|:---|
| **1** | [프로젝트 개요](#프로젝트-개요) | 프로젝트 소개 및 개발 목적 |
| **2** | [주요 기능](#주요-기능) | 핵심 기능 및 특징 |
| **3** | [트러블 슈팅](#트러블-슈팅) | 개발 중 발생한 문제점과 해결 과정 |
| **4** | [시스템 아키텍처](#시스템-아키텍처) | 전체 시스템 구조 및 기술 스택 |
| **5** | [프로젝트 플로우](#프로젝트-플로우) | 사용자 흐름 및 비즈니스 로직 |
| **6** | [프로젝트 구조](#ERD) | 코드 구조 |
| **7** | [프로젝트 구조](#프로젝트-구조) | 코드 구조 |
| **8** | [실행 방법](#실행-방법) | 로컬 환경 실행 가이드 |
| **9** | [실제 서비스](#실제-서비스) | 프로토타입 데모  |

<br>

> 각각의 자세한 내용은 Wiki로 이동하는 링크에서 확인 할 수 있습니다.


<br>

## 프로젝트 개요

이 프로젝트는 **대규모 트래픽이 발생하는 콘서트 예매 시스템에서 발생할 수 있는 기술적 문제들을 해결하는 데 중점**을 둡니다.<br> 
콘서트 티켓처럼 동시에 많은 예약 요청이 몰리는 상황을 시뮬레이션하고,<br> 
이 과정에서 발생하는 문제들을 찾아 해결하여 안정적인 예매 시스템을 구축하는 것을 목표로 합니다.<br> 

또한, **헥사고날 아키텍처**를 데이터 영역에 도입하여 외부 의존성을 낮추고,<br> 
테스트 커버리지 50% 이상을 유지하며 객체지향 원칙에 따라 **테스트하기 좋은 코드**를 작성하는 것을 목표로 삼았습니다. <br> 
Spring Security (OAuth2, JWT)를 통한 인증 시스템과 Docker, ngrok, EC2를 활용한 개발 및 테스트 환경을 구축하여 안정성과 신뢰성을 확보했습니다.

<br>

## 주요 기능

- **좌석 예약**: 사용자는 좌석 현황을 확인하며 원하는 좌석을 선점하고 예약할 수 있습니다.
- **안전한 결제**: Toss Payments API와 연동하여 결제를 처리하고, 웹훅을 통해 결제 상태를 안정적으로 동기화합니다.
- **사용자 인증**: OAuth 2.0 및 JWT를 통해 Google 계정으로 간편하고 안전하게 로그인할 수 있습니다.

<br>

## 트러블 슈팅

| Category | Topic | Detailed Wiki Link |
| :--- | :--- | :--- |
| **Memory** | **대량의 메모리 누수** | **[메인 문서로 이동](https://github.com/jerry-1211/concert-ticketing/wiki/%5BTroubleshooting%5D-1.-%EB%8C%80%EB%9F%89%EC%9D%98-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EB%88%84%EC%88%98)** |
| | └ Native Memory & FD 소켓 누수 추적 | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/%5BTroubleshooting%5D-1%E2%80%901.-Native-Memory-&-FD-%EC%86%8C%EC%BC%93-%EB%88%84%EC%88%98-%EC%B6%94%EC%A0%81) |
| | └ Execution (Interpreter, C1/C2, GraalVM) | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/%5BTroubleshooting%5D-1%E2%80%902.--Execution-(Interpreter,-C1-C2-Complier,-GraalVM)) |
| | └ Java 네이티브 메모리 튜닝 | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/%5BTroubleshooting%5D-1%E2%80%903.-Java-%EB%84%A4%EC%9D%B4%ED%8B%B0%EB%B8%8C-%EB%A9%94%EB%AA%A8%EB%A6%AC-%ED%8A%9C%EB%8B%9D) |
| | └ JVM 튜닝 및 구현체 변경 | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/%5BTroubleshooting%5D-1%E2%80%904.-JVM-%ED%8A%9C%EB%8B%9D-%EB%B0%8F-JVM-%EA%B5%AC%ED%98%84%EC%B2%B4-%EB%B3%80%EA%B2%BD) |
| | └ Code Level에서의 접근 | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/%5BTroubleshooting%5D-1%E2%80%905.-Code-Level-%EC%97%90%EC%84%9C-%EC%8B%9C%EB%8F%84) |
| | └ `jemalloc` 프로파일링 & `strace` 추적 | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/%5BTroubleshooting%5D-1%E2%80%906.-jemalloc-%ED%94%84%EB%A1%9C%ED%8C%8C%EC%9D%BC%EB%A7%81-&-strace-%EC%B6%94%EC%A0%81) |
| | └ `async-profiler` 추적 | [자세히 보기](https://github.com/jerry-1211/concert-ticketing/wiki/%5BTroubleshooting%5D-1%E2%80%907.-async%E2%80%90profiler-%EC%B6%94%EC%A0%81) |
| **Concurrency** | **동시성 문제 (좌석 선점 및 결제)** | **[메인 문서로 이동](https://github.com/jerry-1211/concert-ticketing/wiki/%5BTroubleshooting%5D-2.-%EB%8F%99%EC%8B%9C%EC%84%B1-%EB%AC%B8%EC%A0%9C-(%EC%A2%8C%EC%84%9D-%EC%84%A0%EC%A0%90--&--%EA%B2%B0%EC%A0%9C-%EC%B2%98%EB%A6%AC))** |
| **Performance**| **부하 테스트 병목 현상 (VU 증가 시 RPS 감소)** | **[메인 문서로 이동](https://github.com/jerry-1211/concert-ticketing/wiki/%5BTroubleshooting%5D-3.-VU-%EC%A6%9D%EA%B0%80-%EC%8B%9C-RPS%EA%B0%80-%EA%B0%90%EC%86%8C%ED%95%98%EB%8A%94-%EB%B6%80%ED%95%98-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EB%B3%91%EB%AA%A9-%ED%98%84%EC%83%81)** |
| **Infra** | **CPU 부하 (DB 레플리케이션)** | **[메인 문서로 이동](https://github.com/jerry-1211/concert-ticketing/wiki/%5BTroubleshooting%5D-4.-CPU-%EB%B6%80%ED%95%98-(DB-%EB%A0%88%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98))** |

<br>

## 시스템 아키텍처
<img width="800" height="400" alt="Slide 16_9 - 603" src="https://github.com/user-attachments/assets/8b45b2d4-d79d-43ca-9d3a-e3a748cc1d0c" />


<br>
<br>

| 카테고리 | 기술 | 설명 |
|:---|:---|:---|
| **Backend** | Java 17 + Spring Boot 3.4.5 | 메인 애플리케이션 |
| **Database** | MySQL | RDB |
| **Cache** | Redis | 캐싱 시스템 |
| **Messaging** | RabbitMQ | 비동기 메시지 처리 |
| **Security** | Spring Security + OAuth 2.0 + JWT | 인증 및 인가 시스템 |
| **Infrastructure** | Docker + Docker Compose | 컨테이너 기반 배포 |
| **Monitoring** | k6 + InfluxDB + Grafana | 부하 테스트 및 모니터링 |

<br>

## 프로젝트 플로우
| 구분 | 주요 역할 
| :--- | :--- |
| **좌석 선점 플로우** | [Wiki로 이동](https://github.com/jerry-1211/concert-ticketing/wiki/%5BFlow%5D-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%95%B5%EC%8B%AC-%EB%A1%9C%EC%A7%81-%ED%94%8C%EB%A1%9C%EC%9A%B0#%EC%A2%8C%EC%84%9D-%EC%84%A0%EC%A0%90-%ED%94%8C%EB%A1%9C%EC%9A%B0) |
| **예약 플로우** | [Wiki로 이동](https://github.com/jerry-1211/concert-ticketing/wiki/%5BFlow%5D-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%95%B5%EC%8B%AC-%EB%A1%9C%EC%A7%81-%ED%94%8C%EB%A1%9C%EC%9A%B0#%EC%98%88%EC%95%BD-%ED%94%8C%EB%A1%9C%EC%9A%B0) 
| **결제 플로우** | [Wiki로 이동](https://github.com/jerry-1211/concert-ticketing/wiki/%5BFlow%5D-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%95%B5%EC%8B%AC-%EB%A1%9C%EC%A7%81-%ED%94%8C%EB%A1%9C%EC%9A%B0#%EA%B2%B0%EC%A0%9C-%ED%94%8C%EB%A1%9C%EC%9A%B0) 

<br>

## ERD
<img width="1050" height="800" alt="스크린샷 2025-08-27 오전 9 08 47" src="https://github.com/user-attachments/assets/d5d9ed2d-3a7b-4307-afc9-40513342493a" />

<br>
<br>


## 프로젝트 구조

```
├── TicketingApplication.java
├── concert
│   ├── api
│   ├── application
│   ├── domain
│   └── infrastructure
├── global
│   ├── auth
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

<br>

## 실행 방법

#### 1. **프로젝트 클론**

```yaml
git clone [https://github.com/jerry-1211/concert-ticketing.git](https://github.com/jerry-1211/concert-ticketing.git)
cd concert-ticketing
```
    
#### 2. **환경 변수 설정**
프로젝트 루트 디렉터리에 `.env` 파일을 생성 후 아래에 필요한 변수들 채우기
    
```yaml
# MySQL 데이터베이스 설정
# RabbitMQ 설정
# 포트 설정
# ngrok 설정 (선택 사항)
# Toss Payment 설정 
# Google OAuth2
# JWT 설정

```
    
#### 3. **빌드 및 실행**
Docker Compose를 사용하여 프로젝트의 모든 서비스를 한 번에 빌드하고 실행
    
```yaml
./gradlew build 
docker-compose -f docker-compose.dev.yml up --build
```
    
#### 4. **애플리케이션 접속**
웹 브라우저에서 `http://localhost:8080`으로 접속하여 애플리케이션을 확인 가능

> ngork으로 설정한 url도 가능

<br>

## 실제 서비스 

#### 이 [Wiki 링크](https://github.com/jerry-1211/concert-ticketing/wiki/%5BPrototype%5D-%EC%8B%A4%EC%A0%9C-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%98%88%EC%8B%9C)에서 실제 프로젝트의 프로토타입을 확인 할 수 있습니다. 
