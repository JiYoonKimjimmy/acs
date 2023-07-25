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

#### `Mockito.mock()`

- 원하는 타입의 `Mock` 객체를 생성할 때 사용하는 함수 : **`Mocking`**

```java
class MockitoTest {
    void mockitoTest() {
        TestService service = Mockito.mock(TestService.class);    
    }
}
```

> 이미 구현되어 있는 구현체 클래스를 **`Mocking` 모킹**할 필요는 없다. 외부 라이브러리 또는 API 와 같은 개발자가 제어할 수 없는 모듈에 대해서 모킹을 적용할 필요가 있다.

#### `Mockito.when()`

- `Mock` 객체의 행동을 정의하는 함수 : **`Stubbing`**

```java
class MockitoTest {
    @Test
    void mockitoTest() {
        TestService service = Mockito.mock(TestService.class);
        when(service.hello()).thenReturn("Hello World");
        assertEquals("Hello World", service.hello());
    }
}   
```

#### `Mockito.verify()`

- `Mock` 객체의 행동을 확인하는 함수

```java
class MockitoTest {
    @Test
    void mockitoTest() {
        // given
        TestService service = Mockito.mock(TestService.class);
        
        // when
        when(service.hello()).thenReturn("Hello World");
        
        // then
        assertEquals("Hello World", service.hello());
        verify(service).hello();
    }
}
```

---

### Mockito's `@Mock` `@MockBean` `@Spy` `@SpyBean` `@InjectMocks`
아래 Annotation 들은 `Mockito` 를 활용할 때, 제일 많이 사용하는 주요 Annotation 이라고 한다.
그러기에, 하나씩 한번 정리해보고자 한다.

#### `@Mock`
- `Mockito.mock()` 를 대체하여 Mock 객체를 `Bean` 으로 등록하여 테스트 코드를 작성
- `@ExtendWith(MockitoExtension.class)` Test 클래스 상위 선언 필요

#### `@InjectMocks`
- `@InjectMocks` 가 있는 `Bean` 객체를 생성할 때, `@Mock` 으로 등록된 **Mock 객체를 감지하여 주입하는** 역할

```kotlin
/**
 * `SampleService` 클래스가 `SampleRepository` 의존성 주입을 받아야하는 클래스라면, 
 * `@InjectMocks` 를 활용하여 `@Mock` 으로 등록된 `SampleRepository` Mock 객체 Bean 을 주입받아 Mock 객체 Bean 을 생성
 */
class SampleServiceTest(
    @Mock
    private val sampleRepository: SampleRepository,
    @InjectMocks
    private val sampleService: SampleService
) {
    // ...
}
```

#### `@Spy`
- `Mock` 객체로 사용하고자 하는 객체의 기능을 선택적으로 `Stubbing` 할 수 있는 역할
- `@Spy` 로 등록된 `Mock` 객체의 함수(기능) 중 `Stubbing` 하지 않는 함수는 기존 정의된 로직을 수행 

#### `@MockBean`
- `@SpringBootTest` 통합 테스트를 수행할 때, `@Autowired` 로 생성되는 `Bean` 객체의 의존성을 주입하기 위한 `Mock` 객체를 생성하는 역할

```kotlin
/**
 * `SampleService` 클래스가 `SampleRepository` 의존성 주입을 받아야하는 클래스라면,
 * `@MockBean` 으로 등록된 `SampleRepository` Mock 객체 Bean 을 주입받아 Mock 객체 Bean 을 생성
 */
@SpringBootTest
class SampleServiceTest(
    @MockBean
    private val sampleRepository: SampleRepository,
    @Autowired
    private val sampleService: SampleService
) {
    // ...
}
```

#### `@SpyBean`
- 통합 테스트 환경에서 `@Spy` 역할을 수행하기 위한 역할
- `@Spy` 와 동일하게 `Stubbing` 하지 않는 함수는 그대로 구현 로직 수행
- Interface 인터페이스를 `@SpyBean` 등록하는 경우, **해당 Interface 를 구현한 구현체가 `Spring Context` 에 등록 필수**
  - `@SpyBean` 은 실제 구현체를 감싸는 프록시 객체이기 때문에 실제 구현체가 `Spring Context` 에 등록되어 있어야 한다.

> 관련 참고 글 : [코비의 지극히 사적인 블로그 - Mockito @Mock @MockBean @Spy @SpyBean 차이점](https://cobbybb.tistory.com/16)

---

### Kotlin Mocking F/W `MockK`

`MockK` 는 `Java` 의 `Mockito` 를 대체하는 `Kotlin` 테스트 코드 프레임워크이다.

`Kotlin` 에는 기본적으로 제공하는 다양항 `Collection` 클래스에도 확장함수를 제공하고 있다.
`Java` 로만 이뤄진 프레임워크를 사용한다면, `Kotlin` 의 확장함수를 사용한 테스트 코드 작성에 제약이 생길 수 있다.

그를 보완하고자 탄생한 `MockK` 를 활용해보자.

#### `MockK` Dependency 추가

```groovy
testImplementation("io.mockk:mockk:1.12.4")
```

#### `mockk()`

- `mockk()` 함수는 Mock 객체를 생성하는 함수
- `@MockK` Annotation 제공하여 `Bean` 주입하는 Mock 객체 생성 가능 

```kotlin
class SampleTest(
    @MockK
    val sampleService: SampleService
) {
    @Test
    fun sampleTest() {
        val mock = mockk<SampleService>()
    }
}
```

#### `every()`

- `every()` 함수는 Mock 객체의 기능을 정의하는 `Stubbing` 함수

```kotlin
@Test
fun sampleTest() {
    val mock = mockk<SampleService>()
    every { mock.doSomething(any()) } returns "OK"
}
```

> ##### Relaxed Mock
> - Mock 객체는 `Stub` 정의하지 않으면 에러가 발생하는데, 이를 방지하기 위해 `relaxed = true` 설정
> ```kotlin
> val mock = mockk<SampleService>(relaxed = true)
> ```

#### `verify()`

- `verify()` 함수는 Mock 객체의 함수 호출 여부를 검증

```kotlin
@Test
fun sampleTest() {
    val mock = mockk<SampleService>()
    mock.doSomething(1)
    verify { mock.doSomething(any()) }
}
```

---

### 다른 `Package` 구성된 `SpringBootTest` 실행시 발생하는 에러 해결 방안

> `Unable to find a @SpringBootConfiguration, you need to use @ContextConfiguration or @SpringBootTest(classes = ...) with your test` 에러 발생하는 경우 참고

- `Java` & `Kotlin` 으로 구성된 프로젝트인 경우, 각 언어별로 패키지가 분리되면서, 테스트 코드의 패키지도 분리된다.
- 코드의 패키지는 분리되었지만, `@SpringBootApplication` 이 있는 클래스는 하나의 패키지에만 존재할 것이다. (e.g. 보통은 `Java` 패키지 안에 위치)
- 이런 경우, 다른 패키지에 구성된 `@SpringBootTest` 를 실행하게 되면 `@SpringBootApplication` 클래스를 찾지 못하고 에러가 발생한다.
- 해당 에러를 방지하기 위해서는, 다른 패키지의 `@SpringBootTest` 애노테이션의 `classes` 옵션을 추가하여 해결 가능한다.

```kotlin
@SpringBootTest(classes = [ApiApplication::class])
```

> 참고 블로그 : [그냥 블로그 - Spring boot test 경로가 다른 패키지 테스트 시 오류](https://unhosted.tistory.com/77)

---
