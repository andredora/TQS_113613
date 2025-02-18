package tqs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class TqsStackTest {

    TqsStack tqsStack;

    @BeforeEach
    public void setup() {
        tqsStack = new TqsStack();
    }

    @DisplayName("A stack is empty on construction.")
    @Test
    public void isEmpty() {
        Assertions.assertTrue(tqsStack.isEmpty());
    }

    @DisplayName("A stack has size 0 on construction.")
    @Test
    public void size() {
        Assertions.assertEquals(0, (int) tqsStack.size());
    }

    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n.")
    @Test
    public void push() {
        int n = 3;
        for (int i = 0; i < n; i++) {
            tqsStack.push(i);
        }
        Assertions.assertEquals(n, (int) tqsStack.size());
    }

    @DisplayName("If one pushes x then pops, the value popped is x.")
    @Test
    public void pop() {
        int n = 3;
        tqsStack.push(n);
        Assertions.assertEquals(n, (int) tqsStack.pop());
    }

    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same.")
    @Test
    public void peek() {
        int n = 3;
        tqsStack.push(n);
        int size = tqsStack.size();
        Assertions.assertEquals(n, (int) tqsStack.peek());
        Assertions.assertEquals(size, (int) tqsStack.size());
    }

    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0.")
    @Test
    public void nPops_size() {
        int n = 3;
        for (int i = 0; i < n; i++) {
            tqsStack.push(i);
        }
        for (int i = 0; i < n; i++) {
            tqsStack.pop();
        }
        Assertions.assertTrue(tqsStack.isEmpty());
    }

    @DisplayName("Popping from an empty stack does throw a NoSuchElementException.")
    @Test
    public void popThrowsException() {
        assertThrows(NoSuchElementException.class, () -> tqsStack.pop());
    }

    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException.")
    @Test
    public void peekThrowsException() {
        assertThrows(NoSuchElementException.class, () -> tqsStack.peek());
    }

    @DisplayName("For bounded stacks only, pushing onto a full stack throws an IllegalStateException.")
    @Test

    public void pushThrowsExceptionFull() {
        int capacity = 2;
        TqsStack boundedStack = new TqsStack(capacity);

        for (int i = 0; i < capacity; i++) {
            boundedStack.push(i);
        }

        assertThrows(IllegalStateException.class, () -> boundedStack.push(100));
    }

    @DisplayName("popTopN removes n elements and returns the last one removed")
    @Test
    public void popTopN_RemovesCorrectly() {
        tqsStack.push(1);
        tqsStack.push(2);
        tqsStack.push(3);

        Integer result = tqsStack.popTopN(2);

        Assertions.assertEquals(2, result);
        Assertions.assertEquals(1, tqsStack.size());
    }

    @DisplayName("popTopN throws NoSuchElementException when n is greater than stack size")
    @Test
    public void popTopN_ThrowsExceptionWhenTooLarge() {
        tqsStack.push(1);

        assertThrows(NoSuchElementException.class, () -> tqsStack.popTopN(2));
    }

    @DisplayName("popTopN(1) behaves like pop()")
    @Test
    public void popTopN_BehavesLikePop() {
        tqsStack.push(42);

        Integer popResult = tqsStack.pop();
        tqsStack.push(42);
        Integer popTopNResult = tqsStack.popTopN(1);

        Assertions.assertEquals(popResult, popTopNResult);
    }

}
