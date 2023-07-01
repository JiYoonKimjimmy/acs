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

### Keyword

- `@SpringBootTest` vs `@WebMvcTest`

---

#### `@SpringBootTest` vs `@WebMvcTest`

##### `@SpringBootTest`
- `Spring` 프로젝트에서 **통합 테스트**를 진행하기 위한 애노테이션이다.
- 테스트 코드에서 프로젝트 내에 등록된 `Bean` 를 주입받기 위해 사용할 수 있다.  
- 테스트 코드 실행 시, Spring Context 를 reload 하는 과정에서 Spring 프로젝트 내 등록되는 Bean 객체를 모두 생성한다.
- 단위 테스트인 경우, Bean 객체를 모두 생성하는 과정에서 시간이 걸리기 때문에 테스트 코드 성능이 저하될 수 있다.

##### `@WebMvcTest`
- TODO 작성 예쩡