# Elasticsearch Study.01

## Elasticsearch 소개

`백전백승 지피지기` 말처럼 먼저 간단한 소개라도 알아야 왜 `Elasticsearch` 가 탄생했는지, 
어떤 방향으로 적용해야 하는지 정확히 이해할 수 있을 것이다.

`Elasticsearch` 는 간단하게는 검색 엔진이다. `Compass` 라는 오픈소스 검색엔진에서 `Apache Luence` 에 
적용하던 중 루씬의 한계를 극복하고자 새로운 검색엔진을 개발하게 된 것이 `Elasticsearch` 가 되었다고 한다. 

보통 `Logstash`, `Kibana` 와 함께 사용되면서 한동안 `ELK Stack` 으로 많이 알려졌고, 
2013년에는 `Logstash`, `Kibana` 자체가 `Elasticsearch` 프로젝트에 흡수되면서 
`ELK Stack` 에서 `Elastic Stack` 이라고 정식 명명되었다.

> 2013년 `Logstash`, `Kibana` 흡수되면서 회사명도 `Elasticseacrh` 에서 `Elastic` 으로 변경되었다고 한다.

`Elasticsearch (이하 ES)` 는 기본적으로 모든 데이터를 색인하여 저장하고 검색, 집계 등을 수행하는 역할을 한다.
설치 과정이 비교적 간단한 편이라고 하며, 그에 반해 뛰어난 검색 기능과 대규모 분산 시스템 구축이 가능하다.

기본적인 `DBMS` 에 비해 전문 검색(`Full-Text Search`) 기능과 점수 기반의 정확도 알고리즘, 실시간 분석 등의 구현이 가능하며,
다양한 플로그인 지원도 가능하다.

> 실무에서도 `DBMS` 의 `Full-Scan` 조회는 `DB` 서버 자체의 큰 부하를 주게 되기 때문에, 각별히 주의해야 하는 점이다.

---

### Elasticsearch 특징

#### Open Source 오픈 소스

- `ES` 의 핵심 기능들은 `Apache 2.0` 라이센스로 배포되었으며, `Elastic Stack` 의 모든 제품의 코드는 `GitHub` 에 
올라가있다. 
- 그리고 중요한 점은 루씬 기반이기 때문에 `ES` 또한 `Java` 로 구현되었다.

#### Real-Time Analysis 실시간 분석

- `ES` 는 클러스터가 실행되고 있는 동안에는 계속 데이터를 입력(색인)된다.
- 그와 동시에 거의 실시간에 가까운 속도로 색인된 데이터의 검색 집계가 가능하다.

> Hadoop `하둡`은 기본적으로 Batch 기반의 분석 시스템이다.

#### Full-Text Search Engine 전문 검색 엔진

- `ES` 는 **Inverted file Index `역파일 색인`** 라는 구조로 데이터를 저장한다.
- 색인된 모든 데이터를 역파일 색인 파일 구조로 저장하여 가공된 텍스트를 검색하는데, 이런 특성을 **전문 검색**이라고 한다.
- `ES` 특성 상 기본적으로 `JSON` 형식의 데이터 입력을 허용한다.
  - `JSON` 형식이 아닌 데이터 구조인 경우, `Logstash` 에서 변환을 지원하고 있다.

#### RESTFul API

- `ES` 는 `RESTFul API` 를 지원하여 모든 조회, 입력, 삭제를 `HTTP` 통신을 통해 처리된다.

#### Multi-Tenancy 멀티테넌시

- `ES` 의 데이터는 **인덱스** 라는 논리적인 집합 단위로 구성되며, 다른 저장소에 분산 저장된다.
- 서로 다른 인덱스들을 별도의 연결없이 하나의 질의로 검색하며, 검색된 결과를 하나의 출력으로 도출할 수 있다.

---

## Logstash 소개

`Logstash` 는 `Elasticsearch` 와는 별개의 프로젝트였으며, 다양한 데이터의 수집과 저장을 위해 개발된 프로젝이다.

데이터의 색인, 검색 기능만 가진 `Elasticsearch` 와 데이터의 수집을 위한 도구인 `Logstash` 는 서로의 필요성이 너무나도 충족하기 때문에
통합하게 되었다.

--- 

### Logstash 특징

#### JRuby 기반

- `Logstash` 는 루비 코드로 구현되었기에 `JVM` 에서 동작한다.

#### 데이터 처리 과정

- `Logstash` 의 데이터 처리 과정은 아래와 같다.

1. 입력 (Input) : 다양한 데이터 저장소로부터 입력을 받는다.
2. 필터 (Filter) : 필터 기능을 통해 데이터 확장, 변경, 삭제, 필터링 등 처리를 통해 데이터 가공한다.
3. 출력 (Output) : 다양한 저장소로 다시 출력한다.

- `Logstash` 를 거친 데이터 파일은 `Elasticsearch` 가 아니더라도 다른 저장소로 출력이 가능하기 때문에, 
- `ES` 에서 데이터를 색인하는 동시에 로컬 파일이나 `AWS S3` 와 같은 저장소에 동시 송출이 가능하다.

---

## Kibana 소개

`Kibana` 는 `Elasticsearch` 의 시각화 도구로서, 검색 및 집계 등 기능을 이용하여 웹 도구로 시각화하였다.
크게 `Discover`, `Visualize`, `Dashboard` 3가지 메뉴를 구성하고 있으며, 다양한 플러그인과 APP 연동을 지원한다.

---

### Kibana 특징

#### Discover

- `Discover` 메뉴는 `Elasticsearch` 에서 색인된 소스 데이터를 검색할 수 있는 메뉴이다.
- 검색 창에 질의문을 작성하여 요청하면, 데이터를 간단하게 검색, 필터링 할 수 있다.
- 검색된 데이터의 원본 문서나 선택한 필드만 필터링된 테이블 형태의 조회가 가능하다.
- 시계열 기반의 로그 데이터인 경우 시간 히스토그램 그래프를 통해서 시간대별 로그 수도 표기 가능하다.

#### Visualize

- `Visualize` 는 집계된 데이터의 통계를 다양한 차트로 볼 수 있는 메뉴이다.
- 다양한 시각화된 차트들도 다양한 대시보드를 구성할 수 있다.

#### Dashboard

- `Dashboard` 는 말그대로 `Visualize` 메뉴 통해서 구성된 도구를 조합하여 대시보드 화면을 생성, 저장, 조회 할 수 있는 메뉴이다.

---

#### 출처
- [Elastic 가이드북](https://esbook.kimjmin.net/)
- [Elasticsearch in Action](https://www.manning.com/books/elasticsearch-in-action)
