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

#### _update - 입력된 도큐먼트를 수정

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

#### 출처
- [김종민님 - Elastic 가이드북](https://esbook.kimjmin.net/)
- [Elasticsearch in Action](https://www.manning.com/books/elasticsearch-in-action)
