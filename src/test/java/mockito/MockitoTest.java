package mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class MockitoTest {

    @DisplayName("Mockito 기본 동작 테스트 확인한다.")
    @SuppressWarnings("unchecked")
    @Test
    void mockListSimpleTest() {
        List<String> mockedList = mock(List.class);

        when(mockedList.size()).thenReturn(5);

        assertEquals(5, mockedList.size());
    }

    @SuppressWarnings("unchecked")
    @DisplayName("Stubbing 기본 동작 테스트 확인한다.")
    @Test
    void stubbingTest1() {
        // Interface 뿐아니라 구체 클래스도 mock 으로 만들 수 있다.
        LinkedList<String> mockedList = mock(LinkedList.class);

        // Stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenReturn("second");
        // Exception 처리 테스트를 위한 Stub
        // when(mockedList.get(1)).thenThrow(new RuntimeException());

        System.out.println(mockedList.get(0));
        System.out.println(mockedList.get(1));

        // RuntimeException 발생한다.
        // System.out.println(mockedList.get(1));

        // 999 번째 element 얻어오는 부분은 stub 되지 않았으므로 null 출력
        System.out.println(mockedList.get(999));

        // stubbing 된 부분이 호출되는지 확인할 수 있긴 하지만 불필요한 일이다.
        // 만일 코드에서 get(0)의 리턴값을 확인하려고 하면, 다른 어딘가에서 테스트 실패된다.
        // 만일 코드에서 get(0)의 리턴값에 대해 관심이 없다면, stubbing 되지 않았어야 한다.
        verify(mockedList).get(0);
        verify(mockedList).get(1);

        // stubbing 된 호출 순서를 검증한다.
        InOrder inOrder = inOrder(mockedList);
        System.out.println(inOrder.verify(mockedList).get(0));
        System.out.println(inOrder.verify(mockedList).get(1));

    }

    @DisplayName("Stubbing 기본 동작을 확인하고 `verify` 함수 호출하여 확인한다.")
    @Test
    void stubbingTest2() {
        // given
        LinkedList<String> mockedList = mock(LinkedList.class);

        // when
        when(mockedList.get(anyInt())).thenReturn("element");

        // then
        System.out.println(mockedList.get(999));

        verify(mockedList).get(anyInt());
    }

    @DisplayName("Stub List 객체의 실행된 순서를 검증한다.")
    @Test
    void stubbingTest3() {
        // given
        LinkedList<String> mockedList = mock(LinkedList.class);

        // mock 설정
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        // 아래의 두 가지 검증 방법은 동일하다. times(1)은 기본값이라 생략되도 상관없다.
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        // 정확히 지정된 횟수만큼만 호출되는지 검사한다.
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        // never()를 이용하여 검증한다. never()는 times(0)과 같은 의미이다.
        verify(mockedList, never()).add("never happened");

        // atLeast()와 atMost()를 이용해 검증한다.
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atMost(5)).add("three times");
    }

    @Test
    void BDDTest1() {
        ArrayList<String> mockedList = mock(ArrayList.class);

        given(mockedList.get(0)).willReturn("Hello");

        System.out.println(mockedList.get(0));
        // TODO `then()` 함수 사용 방법 확인
//        assertEquals("Hello", then(mockedList.get(0)).should());
    }

}
