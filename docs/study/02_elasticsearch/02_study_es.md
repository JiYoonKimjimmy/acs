# Elasticsearch Study.02

`Elasticsearch` 의 기본 설치 및 설정 방법을 살펴보자.

> 1. 데이터 색인
> 2. Elasticsearch 설치 방법 (링크)
> 3. Elasticsearch 환경 설정

---

## 데이터 색인

검색 기술을 보다보면 **색인 (`Indexing`)** 이란 단어가 많이 나오게 된다. **색인** 과 관련된 용어 정리를 하고 넘어가자.

|       용어       | 명칭  | 설명                                                                                                |
|:--------------:|:---:|---------------------------------------------------------------------------------------------------|
|    Indexing    | 색인  | 데이터가 검색될 수 있는 구조로 변경하기 위해 데이터 원본 문서를 검색어 토큰으로 변환하여 저장하는 과정이다.                                     |
| Index, Indices | 인덱스 | 색인 과정을 거친 결과물, 또는 결과물이 저장되는 저장소를 의미한다. `Elasticsearch` 에서는 `Document` 들의 논리적인 집합으로 표현되는 단위이기도 하다. |
|     Search     | 검색  | `Index` 에 들어있는 검색어 토큰을 포함하고 있는 `Document` 를 찾아가는 과정이다.                                            |
|     Query      | 질의  | 사용자가 원하는 문서를 찾거나 집계 결과를 출력하기 위해 검색 시 입력하는 검색어 또는 검색 조건을 의미한다.                                     |

![Elasticsearch 데이터 색인 과정](./image/es_study_02_01.png)

---

## Elasticsearch 설치 방법

설치 방법에 대해서는 정확한 가이드북을 참고하는 것이 좋을 것 같아 정리 대신 링크 첨부로 대신한다.

> #### [Elastic 가이드북 - 2. Elasticsearch 시작하기 > 2.2 설치 및 실행](https://esbook.kimjmin.net/02-install/2.2)

---

## Elasticsearch 환경 설정

대용량 데이터 처리를 위해서 `Elasticsearch` 도 분산 클러스터을 구성할 수 있다.

클러스터 설정을 위한 간략한 환경 설정 방식을 살펴보고자 한다.

### Elasticsearch 설정 항목
- `jvm.options` : Java Heap 메모리 및 환경 변수 설정
- `elasticsearch.yml` : `Elasticsearch` 옵션 설정
- `log4j2.properties` : LOG 관련 옵션 설정

---

#### 출처
- [Elastic 가이드북](https://esbook.kimjmin.net/)
- [Elasticsearch in Action](https://www.manning.com/books/elasticsearch-in-action)
