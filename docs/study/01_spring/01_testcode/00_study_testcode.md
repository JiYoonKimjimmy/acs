# Test Code Study

## Test Code 테스트 코드

언제부터였을까.. 
개발 업계에서 **`Test Code` 테스트 코드** 에 대한 중요도가 높아지고 강조가 되는 시점이.. 

어쩌면 당연하게 개발 코드의 품질 보장은 테스트 코드였을 것이다.
하지만, 빠른 산업의 변화로 품질보단 퍼포먼스를 우선시 하는 사회 풍토에서 제대로된 개발자가 아니었다면,
테스트 코드는 그저 사치였고, 번거로운 존재였기 때문에 등한시하게 되었다.

이제부터라도 조금 더 제대로된 개발자가 되기 위해 테스트 코드를 공부해보자.

실습 위주의 스터디를 진행하면서, 중요한 키워드를 정리할 예정이다.

---

### `@SpringBootTest` vs `@WebMvcTest`

#### `@SpringBootTest`
- `Spring` 프로젝트에서 **통합 테스트**를 진행하기 위한 애노테이션이다.
- 테스트 코드에서 프로젝트 내에 등록된 `Bean` 를 주입받기 위해 사용할 수 있다.  
- 테스트 코드 실행 시, Spring Context 를 reload 하는 과정에서 Spring 프로젝트 내 등록되는 Bean 객체를 모두 생성한다.
- 단위 테스트인 경우, Bean 객체를 모두 생성하는 과정에서 시간이 걸리기 때문에 테스트 코드 성능이 저하될 수 있다.

#### `@WebMvcTest`
- `Spring` 프로젝트의 `WebApplication` 관련된 `Bean` 들만 등록하여 테스트를 진행하기 위한 애노테이션이다.
- **통합 테스트**를 위한 `@SpringBootTest` 보단 빠른 테스트 빌드를 강점으로 가지고 있다.
- 반대로, 테스트에서 필요한 `Bean` 객체를 모두 `Mock` 으로 설정해줘야하는 번거러움이 있다.
- 실제 프로젝트의 기능과 달리 `Mocking` 이 되는 기능에 대한 차이가 있을 수 있다.

---

### Mockito

***[Mockito features in Korean](https://github.com/mockito/mockito/wiki/Mockito-features-in-Korean)***

---

### 다른 `Package` 구성된 `SpringBootTest` 실행시 발생하는 에러 해결 방안

> `Unable to find a @SpringBootConfiguration, you need to use @ContextConfiguration or @SpringBootTest(classes=...) with your test`

- `Java` & `Kotlin` 으로 구성된 프로젝트인 경우, 각 언어별로 패키지가 분리되면서, 테스트 코드의 패키지도 분리된다.
- 코드의 패키지는 분리되었지만, `@SpringBootApplication` 이 있는 클래스는 하나의 패키지에만 존재할 것이다. (e.g. 보통은 `Java` 패키지 안에 위치)
- 이런 경우, 다른 패키지에 구성된 `@SpringBootTest` 를 실행하게 되면 `@SpringBootApplication` 클래스를 찾지 못하고 에러가 발생한다.
- 해당 에러를 방지하기 위해서는, 다른 패키지의 `@SpringBootTest` 애노테이션의 `classes` 옵션을 추가하여 해결 가능한다.

```kotlin
@SpringBootTest(classes = [ApiApplication::class])
```

---
