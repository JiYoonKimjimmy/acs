package mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @DisplayName("Stubbing 기본 동작 테스트 확인한다.")
    @SuppressWarnings("unchecked")
    @Test
    void stubSampleTest() {
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

        // stubbing 된 부분이 호출되는지 확인할 수 있긴 하지만 불필요한 일입니다.
        // 만일 코드에서 get(0)의 리턴값을 확인하려고 하면, 다른 어딘가에서 테스트가 깨집니다.
        // 만일 코드에서 get(0)의 리턴값에 대해 관심이 없다면, stubbing 되지 않았어야 합니다.
        verify(mockedList).get(0);
        verify(mockedList).get(1);
    }

}
