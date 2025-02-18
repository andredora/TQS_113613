package tqs;

import java.util.LinkedList;
import java.util.NoSuchElementException;


public class TqsStack {
    LinkedList<Integer> stack;
    private int capacity;

    public TqsStack() {
        this.stack = new LinkedList<>();
        this.capacity = Integer.MAX_VALUE;
    }

    public TqsStack(int capacity) {
        this.stack = new LinkedList<>();
        this.capacity = capacity;
    }

    public Integer pop() {
        if (stack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return stack.pop();
    }

    public Integer size() {
        return stack.size();
    }

    public Integer peek() {
        if (stack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return stack.peek();
    }

    public void push(Integer value) {
        if (stack.size() >= capacity) {
            throw new IllegalStateException("Stack is full");
        }
        stack.push(value);
    }
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public Integer popTopN(Integer n){
        Integer top = null;
        for(int i = 0; i < n; i++){
            top = stack.removeFirst();
        }
        return top;
    }
}