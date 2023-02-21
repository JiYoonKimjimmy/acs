# Elasticsearch Study.04

`Elasticsearch` 는 `HTTP` 프로토콜과 `RESTFul API` 를 지원하고 있다. 
그래서 어쩌면 현대의 개발자들에겐 좀 친화적인 데이터 요청과 응답 처리를 구현할 수 있는 것 같다.

기본적으로 요청 `Request Body` 와 응답 `Response Body` 데이터 형식도 `JSON` 으로 처리되기 때문에,
많은 개발자들이 편하게 접근할 수 있는 것 같다.

또한, `curl` 를 통해서 다양한 운영체제에서 바로 `Elasticsearch` 요청 테스트를 할 수도 있지만,
`Kibana` 를 활용하여 충분히 데이터 통신 테스트를 할 수 있다.

### `Elasticsearch` 통신 특징

- `HTTP` 프로토콜
- `RESTFul` 기반 API 통신
- `JSON` 데이터 형식

#### 지원하는 `HTTP` Methods

|  Method  |    CRUD    | 설명        | 
|:--------:|:----------:|-----------|
|  `PUT`   | **C**reate | 데이터 입력할 때 |
|  `GET`   |  **R**ead  | 데이터 조회할 때 |
|  `POST`  | **U**pdate | 데이터 수정할 때 |
| `DELETE` | **D**elete | 데이터 삭제할 때 |

---

## CRUD - 입력, 조회, 수정, 삭제

이제부터 `Elasticsearch` 의 데이터를 입력, 조회, 수정, 삭제하는 방법들에 대해서 살펴보자.

`Elasticsearch` 에서는 단일 도큐먼트별 고유한 `URL` 를 가진다. 도큐먼트에 접근하는 `URL` 은 
`http://<호스트>:<포트>/<인덱스>/_doc/<도큐먼트 id>` 구조이다.

> `6.x 이하 버전` 에서는, `http://<호스트>:<포트>/<인덱스>/<도큐먼트 타입>/<도큐먼트 id>` 와 같은 구조였지만, 
> `<도큐먼트 타입>` 에 대한 의미가 사라지면서 `_doc` 으로 접근하도록 변경되었다고 한다.

---

### 입력 `PUT`

- `PUT` 방식을 활용하여 데이터 입력이 가능하다.
- 도큐먼트에 첫 데이터 입력하는 경우, `Response` 결과에 `"result": "created"` 라는 정보를 확인할 수 있다. 
- 동일한 `URL` 에 다른 내용의 데이터를 다시 입력하면 기존 도큐먼트를 삭제되고, 새로운 도큐먼트로 저장된다. 
  - 이때의 결과는 `"result": "updated"` 정보를 확인할 수 있다.
- 기존 도큐먼트가 `overwrite` 덮어씌워지는 것을 방지하기 위해서는 `_doc` 대신 `_create` 를 사용하면 새로운 도큐먼트 입력만 허용한다.
  - `"reason": "[1]: version conflict, document already exists (current version [2])"` 이라는 에러를 확인할 수 있다.

#### 도큐먼트에 데이터 최초 입력하는 경우

##### Request

```json lines
PUT my_index/_doc/1
{
  "name": "Jim Kim",
  "message": "안녕하세요 Jim"
}
```

##### Response

```json lines
{
  "_index" : "my_index",
  "_type" : "_doc",
  "_id" : "1",
  "_version" : 1,
  "result" : "created",       // 초기 입력된 데이터인 경우 `created` 응답 처리
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 0,
  "_primary_term" : 1
}
```

#### 동일한 도큐먼트에 다른 정보를 입력하는 경우

##### Request

```json lines
PUT my_index/_doc/1
{
  "name": "Jimmyberg Kim",
  "message": "안녕하세요 Jimmyberg"
}
```

##### Response

```json lines
{
  "_index" : "my_index",
  "_type" : "_doc",
  "_id" : "1",
  "_version" : 2,
  "result" : "updated",       // 다시 입력된 데이터인 경우 `updated` 응답 처리
  "_shards" : {
    "total" : 2,
    "successful" : 2,
    "failed" : 0
  },
  "_seq_no" : 1,
  "_primary_term" : 1
}
```

---

### 조회 `GET`

- `GET` 방식을 활용하여 도큐먼트의 `URL`을 입력하여 도큐먼트의 입력된 내용 데이터를 조회한다.
- `Response` 응답의 `_source` 정보에서 확인 가능하다.

##### Request

```json lines
GET my_index/_doc/1
```

##### Response

```json lines
{
  "_index" : "my_index",
  "_type" : "_doc",
  "_id" : "1",
  "_version" : 2,
  "_seq_no" : 1,
  "_primary_term" : 1,
  "found" : true,
  "_source" : {
    "name" : "Jimmyberg Kim",
    "message" : "안녕하세요 Jimmyberg"
  }
}
```

---

### 수정 `POST`

- `POST` 방식으로 데이터를 신규로 입력 또는 수정이 가능하다.
- `<인덱스>/_doc` 뒤에 `도큐먼트 ID` 입력하지 않으면, 자동으로 임의의 `도큐먼트 ID` 가 생성된다. (`PUT` 에서는 동작하지 않는다.)

##### Request

```json lines
POST my_index/_doc
{
  "name":"Jimmyberg Kim",
  "message":"안녕하세요 Jimmyberg"
}
```

##### Response

```json lines
{
  "_index" : "my_index",
  "_type" : "_doc",
  "_id" : "ZuFv12wBspWtEG13dOut",   // 자동으로 생성된 `도큐먼트 ID`
  "_version" : 1,
  "result" : "created",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 0,
  "_primary_term" : 1
}
```

#### `_update` : 입력된 도큐먼트를 수정

##### Request

```json lines
POST my_index/_update/1
{
  "doc": {
    "message":"안녕하세요 Jim"
  }
}
```

##### Response

```json lines
{
  "_index" : "my_index",
  "_type" : "_doc",
  "_id" : "1",
  "_version" : 2,
  "result" : "updated",         // 도큐먼트 수정으로 `updated` 응답 처리 
  "_shards" : {
    "total" : 2,
    "successful" : 2,
    "failed" : 0
  },
  "_seq_no" : 1,
  "_primary_term" : 1
}
```

---

### 삭제 `DELETE`

- `DELETE` 방식으로 도큐먼트 또는 인덱스 단위 삭제가 가능하다.

#### 도큐먼트 삭제

- 삭제 성공한 경우, 응답으로 `"result" : "deleted"` 정보를 확인할 수 있다.
- 이미 삭제된 도큐먼트를 삭제 삭제하는 경우, 응답으로 `"found" : false` 정보를 확인할 수 있다.

##### Request

```json lines
DELETE my_index/_doc/1
```

##### Response

```json lines
{
  "_index" : "my_index",
  "_type" : "_doc",
  "_id" : "1",
  "_version" : 3,
  "result" : "deleted",
  "_shards" : {
    "total" : 2,
    "successful" : 2,
    "failed" : 0
  },
  "_seq_no" : 2,
  "_primary_term" : 1
}
```

#### 인덱스 삭제

- 이미 삭제된 인덱스를 다시 삭제하는 경우, `"type" : "index_not_found_exception"` 에러 응답 처리 된다.

##### Request

```json lines
DELETE my_index
```

##### Response

```json lines
{
  "acknowledged" : true
}
```

---

## Bulk 벌크 처리 API

- 대량의 데이터를 처리할 때 `_bulk API` 통해서 처리 가능하다.
- `index`, `create`, `update`, `delete` 에서 동작 가능하다.

#### `_bulk` 요청 예제


##### Request

```json lines
POST _bulk
{"index":{"_index":"test", "_id":"1"}}
{"field":"value one"}
{"index":{"_index":"test", "_id":"2"}}
{"field":"value two"}
{"delete":{"_index":"test", "_id":"2"}}
{"create":{"_index":"test", "_id":"3"}}
{"field":"value three"}
{"update":{"_index":"test", "_id":"1"}}
{"doc":{"field":"value two"}}
```

##### Response

```json lines
{
  "took" : 440,
  "errors" : false,
  "items" : [
    {
      "index" : {
        "_index" : "test",
        "_type" : "_doc",
        "_id" : "1",
        "_version" : 1,
        "result" : "created",
        "_shards" : {
          "total" : 2,
          "successful" : 1,
          "failed" : 0
        },
        "_seq_no" : 0,
        "_primary_term" : 1,
        "status" : 201
      }
    },
    {
      "index" : {
        "_index" : "test",
        "_type" : "_doc",
        "_id" : "2",
        "_version" : 1,
        "result" : "created",
        "_shards" : {
          "total" : 2,
          "successful" : 1,
          "failed" : 0
        },
        "_seq_no" : 1,
        "_primary_term" : 1,
        "status" : 201
      }
    },
  // ...
  ]
}
```

---

## 검색 API

`Elasticsearch` 는 인덱스 단위의 데이터를 검색하는 기능이 탁월하다.

검색은 `GET <인덱스명>/_search` 형식을 사용하여 쿼리 `Query` 질의 방식으로 검색 조회한다. 
아무런 쿼리가 없다면 도큐먼트 전체 조회하는 `match_all` 를 한다.

검색 API 는 다음과 같은 검색 방식을 지원한다.

- **URI 검색**
- **Data Body 검색**

### URI 검색

URI 검색은 말 그대로 검색 URI 에 검색하고자 하는 검색 쿼리 질의문 포함하여 조회 요청하는 방식이다.

#### 예시 1) `test` 라는 인덱스에서 `value` 라는 문자열 포함한 데이터 조회 방법

- 검색를 하고자하는 문자열을 `?q=<검색 문자열>` 방식을 사용한다.
- 응답 정보에서 **Relevancy** 정확도가 높은 순서대로 `hits.hits` 정보에 목록으로 응답 처리된다.

##### Request

```http request
GET test/_search?q=value
```

##### Response

```json lines
{
  "took" : 3,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 2,
      "relation" : "eq"
    },
    "max_score" : 0.105360515,
    "hits" : [
      {
        "_index" : "test",
        "_type" : "_doc",
        "_id" : "3",
        "_score" : 0.105360515,
        "_source" : {
          "field" : "value three"
        }
      },
      {
        "_index" : "test",
        "_type" : "_doc",
        "_id" : "1",
        "_score" : 0.105360515,
        "_source" : {
          "field" : "value two"
        }
      }
    ]
  }
}
```

#### 예시 2) `test` 라는 인덱스에서 `value` 와 `three` 라는 문자열 모두 포함하는 데이터 조회 방법

- 조건을 추가하기 위해서 `AND, OR, NOT` 를 사용할 수 있다.

##### Request

```http request
GET test/_search?q=value AND three
```

##### Response

```json lines
{
  "took" : 3,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 1,
      "relation" : "eq"
    },
    "max_score" : 0.87546873,
    "hits" : [
      {
        "_index" : "test",
        "_type" : "_doc",
        "_id" : "3",
        "_score" : 0.87546873,
        "_source" : {
          "field" : "value three"
        }
      }
    ]
  }
}
```

#### 예시 3) `test` 라는 인덱스에서 `field` 라는 필드에서 `value` 라는 문자열 포함하는 데이터 조회 방법

- 원하는 필드명에 포함된 문자열을 조회하려면, `<필드명>:<검색 문자열>` 형식으로 질의를 작성한다.

##### Request

```http request
GET test/_serach?q=filed:value
```

##### Response

```json lines
{
  "took" : 1,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 2,
      "relation" : "eq"
    },
    "max_score" : 0.18232156,
    "hits" : [
      {
        "_index" : "test",
        "_type" : "_doc",
        "_id" : "3",
        "_score" : 0.18232156,
        "_source" : {
          "field" : "value three"
        }
      },
      {
        "_index" : "test",
        "_type" : "_doc",
        "_id" : "1",
        "_score" : 0.18232156,
        "_source" : {
          "field" : "value two"
        }
      }
    ]
  }
}
```

---

### Data Body 검색

`Data Body` 데이터 본문 검색 방식은 검색 쿼리 질의문을 `HTTP Body` 에 포함하여 조회 요청하는 방식이다.
`Elasticsearch` 의 `QueryDSL` 을 사용하여 `JSON` 형식으로 작성 가능하다.

#### 예시) `test` 라는 인덱스에서 `field` 라는 필드에서 `value` 라는 문자열 포함하는 데이터 조회 방법

- 가장 많이 사용하는 `match` 쿼리 질의문을 사용하여 필드명에 포함된 문자열을 조회한다.

##### Request

```json lines
GET test/_search
{
  "query": {
    "match": {
      "field": "value"
    }
  }
}
```

##### Response

```json lines
{
  "took" : 2,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 2,
      "relation" : "eq"
    },
    "max_score" : 0.105360515,
    "hits" : [
      {
        "_index" : "test",
        "_type" : "_doc",
        "_id" : "3",
        "_score" : 0.105360515,
        "_source" : {
          "field" : "value three"
        }
      },
      {
        "_index" : "test",
        "_type" : "_doc",
        "_id" : "1",
        "_score" : 0.105360515,
        "_source" : {
          "field" : "value two"
        }
      }
    ]
  }
}
```

---

## Multi-tenancy 멀티 테넌시

`Elasticsearch` 에서는 여러 개의 인덱스를 한번에 질의할 수 있는 `Multi-tenancy` 을 지원한다.

`Multi-tenancy` 를 활용하면, 날짜별로 저장되어있는 여러 개의 로그 파일을 한번의 질의를 통해 검색 가능하다.

#### `,` 쉼표로 구분한 여러 개의 인덱스 한번에 조회하는 방법
```http request
GET logs-2018-01,2018-02,2018-03/_search
```

#### `*` 와일드카드로 여러 개의 인덱스 한번에 조회하는 방법
```json lines
GET logs-2018-*/_search
```

---

#### 출처
- [김종민님 - Elastic 가이드북](https://esbook.kimjmin.net/)
- [Elasticsearch in Action](https://www.manning.com/books/elasticsearch-in-action)
