# 📚 중고 거래 플랫폼

Spring Boot 기반으로 리팩토링한 **강남대학교 중고 교재 거래 플랫폼**입니다.  
기존 Servlet/JSP 기반 시스템을 전면 개선하여 보안, 유지보수성, 사용자 경험을 강화했습니다.

개발 기간 : 4월 1일 - 5월 8일  
   
개발 인원 : 단독

---

## 🏗️ 프로젝트 개요

- JWT 기반 안전한 인증 시스템 (토큰 유효성 검사 및 자동 갱신)
- WebSocket(STOMP)을 활용한 실시간 1:1 채팅 기능
- 이메일 인증을 통한 비밀번호 찾기 및 계정 복구
- RESTful API 기반 프론트/백엔드 분리 아키텍처
- Spring Data JPA 적용으로 데이터 계층 최적화

---

## 🛠 개발 환경

| 구분        | 기술 스택                                  |
|-------------|---------------------------------------------|
| **Frontend**| Thymeleaf, JavaScript, jQuery, HTML, CSS   |
| **Backend** | Java, Spring Boot, JPA, Spring Security    |
| **DB**      | MySQL                                      |
| **툴**      | Eclipse, HeidiSQL, GitHub                  |

---

## 🔧 기술 스택 상세

### ✅ 백엔드
- **Spring Boot**: REST API 서버 구축
- **Spring Security**: JWT 인증/인가 처리 (`JwtAuthFilter`)
- **WebSocket (STOMP)**: 실시간 채팅 기능
- **JPA (Spring Data)**: CRUD 자동화
- **BCrypt**: 비밀번호 암호화 (`SecurityConfig` Bean 등록)

### ✅ 프론트엔드
- **Thymeleaf**: 서버 사이드 렌더링
- **JavaScript**: WebSocket 클라이언트 채팅 구현

---

## ✨ 주요 기능

### 👤 사용자 기능
- 회원가입 / 로그인 / 로그아웃
- 이메일 인증을 통한 비밀번호 재설정
- 마이페이지에서 사용자 정보 수정 가능

### 📦 상품 기능
- 상품(교재, 학용품) 등록 / 수정 / 삭제
- 키워드 검색 및 페이징 처리
- 찜 기능으로 관심 상품 관리

### 💬 커뮤니케이션
- **1:1 실시간 채팅** (WebSocket 기반)
- 채팅 내역 저장 및 조회 가능
- 댓글 기능 및 후기 작성 기능 제공

---

## 🖼️ 화면 미리보기

### 🔐 로그인 & 메인 홈
![image](https://github.com/user-attachments/assets/82ca8f3b-994e-48f4-add6-914ee42de34b)
![image](https://github.com/user-attachments/assets/3dcaeb7a-1343-43e1-9490-26ddcbddf75a)



### 🔐 비밀번호 찾기 (이메일 인증)
![image](https://github.com/user-attachments/assets/a5dfb41c-6c42-4609-9c71-b89dbb529f4b)
![image](https://github.com/user-attachments/assets/56654859-0c10-4cd8-90c6-fdad4e8c7140)




### 📄 게시물 작성 & 상세보기
![image](https://github.com/user-attachments/assets/f4ea363f-fab8-437d-a5ec-a6cd9fd3172c)
![image](https://github.com/user-attachments/assets/faa1a2ea-a8fd-49fa-a3ea-4cc78098453b)


### 👤 마이페이지 & 채팅
![image](https://github.com/user-attachments/assets/0373bbe9-650d-45f2-9483-fc8e548565cc)
![image](https://github.com/user-attachments/assets/22d1507a-339a-43f9-adac-a1f2206a5de2)


---

## 🗂 ERD
<img width="1343" height="554" alt="image" src="https://github.com/user-attachments/assets/135b530b-6f97-4fe8-9afe-d4a98dcf8dc3" />

본 프로젝트는 사용자 중심의 중고 거래 플랫폼으로, 아래와 같은 데이터 모델로 구성되어 있습니다.

## 🔗 주요 엔티티 및 관계

테이블	설명	주요 관계
| 테이블          | 설명        | 주요 관계                                                        |
| ------------ | --------- | ------------------------------------------------------------ |
| **User**     | 사용자 정보 관리 | BBS(1:N), Reply(1:N), Jjim(1:N), Chatroom(1:N), Message(1:N) |
| **BBS**      | 거래 게시글 정보 | User(1:N), Reply(1:N), Jjim(1:N), Chatroom(1:N)              |
| **Reply**    | 게시글 댓글    | User(1:N), BBS(1:N)                                          |
| **Jjim**     | 찜 기능      | User(1:N), BBS(1:N)                                          |
| **Chatroom** | 1:1 채팅방   | User(1:N)×2, BBS(1:N), Message(1:N)                          |
| **Message**  | 채팅 메시지    | User(1:N), Chatroom(1:N)                                     |


---



## 📌 GitHub

> 기존 프로젝트 Repository: [kangnamMarket](https://github.com/HONG0805/kangnamMarket)

