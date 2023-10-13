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

#### `100001` 도로명주소 한글

- 도로명 주소 정보의 **Master** 역할의 정보
- 도로명 주소, 지번 주소 정보 포함한 정보 수집
- 해당 주소 기반으로 주소 검색 ES 인덱스 구성

#### `200001` 도로명주소 출입구정보

- 도로명 주소 정보의 출입구 정보
- `100001` 정보의 5가지 `PK` 기준 출입구 좌표 정보 수집

---

### 도로명주소 ES 인덱스 설계

```json lines
PUT jim_juso_index
{
  "mappings": {
    "properties": {
      "fullAddress": {
        "type": "text",
        "analyzer": "jim_juso_analyzer"
      },
      "roadAddress": {
        "type": "text",
        "analyzer": "jim_juso_analyzer"
      },
      "jibunAddress": {
        "type": "text",
        "analyzer": "jim_juso_analyzer"
      }
    }
  },
  "settings": {
    "index": {
      "analysis": {
        "analyzer": {
          "jim_juso_analyzer": {
            "type": "custom",
            "char_filter": [
              "road_address_replace_filter",
              "jibun_address_replace_filter",
              "etc_addr_replace_filter",
              "dash_mapping_filter"
            ],
            "tokenizer": "whitespace_tokenizer",
            "filter": [
              "sido_synonym_filter",
              "gugun_synonym_filter",
              "shingle_filter"
            ]
          }
        },
        "char_filter": {
          "road_address_replace_filter": {
            "type": "pattern_replace",
            "pattern": "(\\S+[로|길]\\b)\\s+(\\d+)\\s+(\\S*[로|길]\\b)",
            "replacement": "$1$2$3"
          },
          "jibun_address_replace_filter": {
            "type": "pattern_replace",
            "pattern": "(?<=구\\b\\s)(\\S+)\\s+(\\d*)\\s+([동|가|리]\\b)",
            "replacement": "$1$2$3"
          },
          "etc_addr_replace_filter": {
            "type": "pattern_replace",
            "pattern": "\\s(?<=[0-9](,?)\\s).*$",
            "replacement": ""
          },
          "mapping_filter": {
            "type": "mapping",
            "mappings": [
              "- => 0000000000",
              ", => "
            ]
          }
        },
        "tokenizer": {
          "whitespace_tokenizer": {
            "type": "whitespace"
          }
        },
        "filter": {
          "sido_synonym_filter": {
            "type": "synonym",
            "synonyms": [
              "서울시, 서울 => 서울특별시",
              "부산시, 부산 => 부산광역시",
              "대구시, 대구 => 대구광역시",
              "인천시, 인천 => 인천광역시",
              "광주시, 광주 => 광주광역시",
              "대전시, 대전 => 대전광역시",
              "울산시, 울산 => 울산광역시",
              "세종시, 세종 => 세종특별자치시",
              "강원도, 강원 => 강원특별자치도",
              "충북 => 충청북도",
              "충남 => 충청남도",
              "전북 => 전라북도",
              "전남 => 전라남도",
              "경북 => 경상북도",
              "경남 => 경상남도"
            ]
          },
          "gugun_synonym_filter": {
            "type": "synonym",
            "synonyms": [
              "인천광역시 남구 => 인천광역시 미추홀구"
            ]
          },
          "shingle_filter": {
            "type": "shingle",
            "min_shingle_size": 5,
            "max_shingle_size": 7
          }
        }
      }
    }
  }
}
```

#### Character Filter 구성

##### `road_address_replace_filter`

```json
{
    "type": "pattern_replace",
    "pattern": "(\\S+[로|길]\\b)\\s+(\\d+)\\s+(\\S*[로|길]\\b)",
    "replacement": "$1$2$3"
}
```

- 도로명 주소의 정규화를 위한 캐릭터 필터
- `은행로 3 번길` 인 도로명 문자열 공백 제거 > `은행로3번길` 변환

##### `jibun_address_replace_filter`

```json
{
    "type": "pattern_replace",
    "pattern": "(?<=구\\b\\s)(\\S+)\\s+(\\d*)\\s+([동|가|리]\\b)",
    "replacement": "$1$2$3"
}
```

- 지번 주소 정규화를 위한 캐릭터 필터
- `영등포구 은행 1 동` 인 지번 문자열 공백 제거 > `영등포구 은행1동` 변환

##### `etc_addr_replace_filter`

```json
{
    "type": "pattern_replace",
    "pattern": "\\s(?<=[0-9](,?)\\s).*$",
    "replacement": ""
}
```

- 실제 주소 외 건물명과 같은 부가적인 문자열 삭제를 위한 캐릭터 필터
- `서울특별시 영등포구 은행로 25 안원빌딩` 인 문자열의 건물명인 `안원빌딩` 제거 > `서울특별시 영등포구 은행로 25` 변환

##### `mapping_filter`

```json
{
    "type": "mapping",
    "mappings": [
        "- => 0000000000",
        ", => "
    ]
}
```

- 주소 정보의 번지/호 정보는 `-` 로 구분되는 경우, `-` 정보를 `0000000000` 으로 변환
  - 하나의 숫자로 변환하여 검색 성능 효율 증가
  - `12-3` 인 문자열 > `1200000000003` 변환
- `,` 문자열만 남은 경우 제거 처리

##### `whitespace_tokenizer`

```json
{
    "type": "whitespace"
}
```

- 주어진 문서의 공백 기준으로 `Token` 분리

##### `sido_synonym_filter`

```json 
{
    "type": "synonym",
    "synonyms": [
        "서울시, 서울 => 서울특별시",
        "부산시, 부산 => 부산광역시",
        "대구시, 대구 => 대구광역시",
        "인천시, 인천 => 인천광역시",
        "광주시, 광주 => 광주광역시",
        "대전시, 대전 => 대전광역시",
        "울산시, 울산 => 울산광역시",
        "세종시, 세종 => 세종특별자치시",
        "강원도, 강원 => 강원특별자치도",
        "충북 => 충청북도",
        "충남 => 충청남도",
        "전북 => 전라북도",
        "전남 => 전라남도",
        "경북 => 경상북도",
        "경남 => 경상남도"
    ]
}
```

- 주소 검색의 편의성 제공을 위한 시도 정보의 동의어 처리

##### `gugun_synonym_filter`

```json
{
    "type": "synonym",
    "synonyms": [
        "인천광역시 남구 => 인천광역시 미추홀구",
        "경상북도 군위군 => 대구광역시 군위군"
    ]
}
```

- 과거 주소 정보에서 변경된 이력의 주소를 제공을 위한 구군 동의어 처리

##### `shingle_filter`

```json
{
    "type": "shingle",
    "min_shingle_size": 5,
    "max_shingle_size": 7
}
```

- 분리된 `Term` 의 조합을 통해 색인 처리 최적화
- `서울특별시`, `영등포구`, `은행로`, `25` 각각 분리된 `Term` 을 순서대로 5 ~ 7 조합

---
