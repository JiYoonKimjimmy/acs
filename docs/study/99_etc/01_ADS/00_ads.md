# 주소 DB 연계 서비스

## 주소 DB 연계

- **국가주소정보시스템** `KAIS` 에서 관리하는 국가 전체의 주소 정보를 수집하기 위해 [주소 연계 서비스](https://business.juso.go.kr/addrlink/main.do) 오픈 API 를 활용
- 최초 1회 전체 데이터 수집 후, 1일 1회 `Batch` 처리를 통해서 변동 자료분에 대한 수집 처리
- **주소 연계 서비스**에서 제공하는 `Java` 라이브러리 주입 필요하며 `API` 호출 모듈 분리

> 관련 가이드 문서 : [국가주소연계서비스_변동자료_연계서비스_가이드_JAVA.pdf](./%EA%B5%AD%EA%B0%80%EC%A3%BC%EC%86%8C%EC%97%B0%EA%B3%84%EC%84%9C%EB%B9%84%EC%8A%A4_%EB%B3%80%EB%8F%99%EC%9E%90%EB%A3%8C_%EC%97%B0%EA%B3%84%EC%84%9C%EB%B9%84%EC%8A%A4_%EA%B0%80%EC%9D%B4%EB%93%9C_JAVA.pdf)

---

### 주소 연계 API 연동

- 주소 연계 API 연동은 `KAIS` 에서 제공하는 `JAR` 파일을 활용한다.
- 가이드 문서를 참고하여 `JAR` 를 다운로드하고 프로젝트에 `import` 한다.

> [도로명주소 연계 가이드 페이지](https://business.juso.go.kr/addrlink/adresDbCntc/rnAdresCntc.do)

---

### 주소 수집 정보

- `100001` 도로명주소 한글 테이블(`master`)
