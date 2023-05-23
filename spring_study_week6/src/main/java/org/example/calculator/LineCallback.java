package org.example.calculator;

/**
 * title        :
 * author       : sim
 * date         : 2023-05-23
 * description  :
 */
public interface LineCallback<T> {
    T doSomethingWithLing(String line, T value);

}
