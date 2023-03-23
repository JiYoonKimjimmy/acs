# Elasticsearch Study.06.02

`Elasticsearch` 의 역인덱스 과정은 `Analyzer` 애널라이저를 통해서 처리된다.

애널라이저는 다음과 같은 구성으로 이뤄지고, 각 역할에 대해 자세히 살펴보자.

- `Character Filter` **캐릭터 필터**
- `Tokenizer` **토크나이저**
- `Token Filter` **토큰 필터**

---

## Character Filter 캐릭터 필터

`Character Filter` **캐릭터 필터**는 텍스트 분석 과정에서 제일 처음으로 수행되는 과정으로,
색인된 텍스트가 토크나이저를 통해 `Term` **텀**으로 분리되기 전에 전체 문장에 대해 일련의 작업이 적용되는 전처리 도구이다.

`7.0 버전`부터는 캐릭터 필터로 `HTML Strip`, `Mapping`, `Pattern Replace` 총 3개가 있다.

3개를 모두 차례대로 적용할 수도 있고, 사용자 지정을 통해 다르게 지정 가능하다.

---

### HTML Strip

`HTML Strip` 필터는 색인된 파일에 `HTML` 텍스트인 경우, `HTML` 태그를 제거하여 일반 텍스트로 만든다.
`<>` 와 같은 태그를 포함하여 특수문자를 표현하는 `HTML` 문법도 해석하여 대치한다.

##### Request

```json lines
POST _analyze
{
  "tokenizer": "keyword",
  "char_filter": [
    "html_strip"
  ],
  "text": "<p>I&apos;m so <b>happy</b>!</p>"
}
```

##### Response

```json lines
{
  "tokens" : [
    {
      "token" : """

I'm so happy!

""",
      "start_offset" : 0,
      "end_offset" : 32,
      "type" : "word",
      "position" : 0
    }
  ]
}
```

---

### Mapping

`Mapping` 필터는 지정한 단어를 다른 단어로 치환 가능하다. 
특수문자 등을 포함한 검색 기능을 구현하는 경우 필수적으로 설정해야하는 필터라고 한다.

#### Mapping 필터 등록 방법

- `cpp_char_filter` 등록을 통해 필터 타입 지정
- `mappings` 필드에서 치환하고자 하는 단어 지정

##### e.g. `C++` 단어는 `C_plus__plus_` 로 변형되어 저장된다.

```json lines
PUT coding
{
  "settings": {
    "analysis": {
      "analyzer": {
        "coding_analyzer": {
          "char_filter": [
            "cpp_char_filter"
          ],
          "tokenizer": "whitespace",
          "filter": [ "lowercase", "stop", "snowball" ]
        }
      },
      "char_filter": {
        "cpp_char_filter": {
          "type": "mapping",
          "mappings": [ "+ => _plus_", "- => _minus_" ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "language": {
        "type": "text",
        "analyzer": "coding_analyzer"
      }
    }
  }
}
```

---

### Pattern Replace

`Pattern Replace` 필터는 정규식(`Regular Expression`)을 이용하여 단어를 치환해주는 필터이다.

#### Pattern Replace 필터 등록 방법

```json lines
PUT camel
{
  "settings": {
    "analysis": {
      "analyzer": {
        "camel_analyzer": {
          "char_filter": [
            "camel_filter"
          ],
          "tokenizer": "standard",
          "filter": [
            "lowercase"
          ]
        }
      },
      "char_filter": {
        "camel_filter": {
          "type": "pattern_replace",
          "pattern": "(?<=\\p{Lower})(?=\\p{Upper})",
          "replacement": " "
        }
      }
    }
  }
}
```

---

## Tokenizer 토크나이저

---

## Token Filter 토큰 필터

---

#### 출처
- [김종민님 - Elastic 가이드북](https://esbook.kimjmin.net/)
    - `Elastic` 회사의 개발자이셨던 **김종민님의 `Elastic 가이드북`** 을 주로 참고하여 문서 작성 계획
- [Elasticsearch in Action](https://www.manning.com/books/elasticsearch-in-action)
