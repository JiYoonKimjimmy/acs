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
| `DELETE` | **D**elete | 데이터 삭제할 때 |

---

## CRUD - 입력, 조회, 수정, 삭제

이제부터 `Elasticsearch` 의 데이터를 입력, 조회, 수정, 삭제하는 방법들에 대해서 살펴보자.

`Elasticsearch` 에서는 단일 도큐먼트별 고유한 `URL` 를 가진다. 도큐먼트에 접근하는 `URL` 은 
`http://<호스트>:<포트>/<인덱스>/_doc/<도큐먼트 id>` 구조이다.

> `6.x 이하 버전` 에서는, `http://<호스트>:<포트>/<인덱스>/<도큐먼트 타입>/<도큐먼트 id>` 와 같은 구조였지만, 
> `<도큐먼트 타입>` 에 대한 의미가 사라지면서 `_doc` 으로 접근하도록 변경되었다고 한다.

---

#### 출처
- [김종민님 - Elastic 가이드북](https://esbook.kimjmin.net/)
- [Elasticsearch in Action](https://www.manning.com/books/elasticsearch-in-action)
