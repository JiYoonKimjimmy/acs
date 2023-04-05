# Elasticsearch Study.07

## 인덱스 설정과 매핑 - Settings & Mappings

`Elasticsearch` 의 `Index` **인덱스**는 도큐먼트 데이터들의 논리적인 집합이다. 
인덱스는 하나의 노드에 저장되기 보다는 [03. Elasticsearch 시스템구조](./03_study_es.md) 에서 다룬 내용처럼
여러 샤드로 분산되어 데이터 저장하고, 데이터 무결성의 보장과 검색의 최적화 기능을 제공한다.
그리고 `Analyzer` **애널라이저**와 같은 과정을 거쳐 분리된 데이터도 인덱스 단위로 저장된다.

`Elasticsearch` 의 핵심인 **인덱스** 단위에서의 설정과 데이터 명세인 **인덱스 매핑**에 대해 학습이 필요할 것이다.

> 설정을 간단하게 파악해둬야 장애 대응이 가능하다.

---

## 인덱스 설정

---

## 인덱스 매핑

---

#### 출처
- [김종민님 - Elastic 가이드북](https://esbook.kimjmin.net/)
    - `Elastic` 회사의 개발자이셨던 **김종민님의 `Elastic 가이드북`** 을 주로 참고하여 문서 작성 계획
- [Elasticsearch in Action](https://www.manning.com/books/elasticsearch-in-action)
