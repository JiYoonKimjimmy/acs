# ACS <small>Address Collection Service</small>
## 주소 수집 처리 서비스

### Purpose
- `juso.go.kr` 의 주소 정보 수집
- 수집된 주소 정보 **Elasticsearch** 저장
- 정보 `인덱싱(Indexing)` 처리하여 검색 성능 최적화

> ### 학습 목표
> - [Spring F/W 심화 학습](./docs/study/01_spring/00_study_spring.md)
> - [Elasticsearch 학습](./docs/study/02_elasticsearch/00_study_es.md)
> - 비동기 프로그래밍 학습
> - Kotlin 클린 코드 & 심화 학습
> - `TDD` 연습

---

### Project Specification
- Java 11
- Kotlin
- Spring Boot 2.7.7
- Spring Webclient
- Elasticsearch
- Gradle (with Kotlin DSL)

---

## Project Service
### 주소 연계 수집 서비스
- [주소 연계 서비스](https://business.juso.go.kr/addrlink/main.do) 오픈 API 를 활용하여 **도로명 주소** 정보 조회
- 조회된 주소 정보 `Elasticsearch` 저장 처리

#### 기능 구현
- `Spring WebClient` API 활용하여 오픈 API 연동
- 연동 DB 데이터 정보 `Elasticsearch` 저장 및 `역인덱싱` 처리

---

### 주소 검색 서비스
- `Elasticsearch` 저장된 주소 정보 조회 API 제공
- 검색 성능 최적화

---

## Project Test

### `HTTP Request` Test
- `Intellij` `HTTP` 요청 테스트 파일 활용
- `/docs/http/*.http` 위치의 파일 정의된 API 테스트 가능

---

### Link
- [주소 연계 서비스 개발자 센터](https://business.juso.go.kr/addrlink/main.do)
