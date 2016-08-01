package com.annimon.stream.function;

/**
 * Represents a function which produces an int-valued result from input argument.
 *
 * @param <T> the type of the input of the function
 */
public interface ToIntFunction<T> {

    /**
     * Applies this function to the given argument.
     * 
     * @param t  an argument
     * @return the function result
     */
    int applyAsInt(T t);
}
