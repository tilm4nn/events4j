/*
 * The MIT License
 * 
 * Copyright (C) 2011 Tilmann Kuhn
 * 
 * http://www.object-zoo.net
 * 
 * mailto:events4j@object-zoo.net
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.objectzoo.delegates;

/**
 * A {@code Function} is a reference to a function with return value that can be invoked
 * synchronously similar to a regular Java method call. To enable interoperability with Java 8
 * libraries {@code Function} is replaceable with its super interface
 * {@link java.util.function.Function} in many situations.
 *
 * @param <T> The type of the {@code Function}'s parameter
 * @param <R> The type of the {@code Function}'s return value
 * @author tilmann
 */
@FunctionalInterface
public interface Function<T, R> extends java.util.function.Function<T, R>
{
    /**
     * Binds the given parameter to the given {@link java.util.function.Function} returning a new
     * {@link Function0} that acts as an invocation of the {@link java.util.function.Function} with
     * the given parameter.
     *
     * @param function the {@code Function} to be bound
     * @param parameter the parameter to be bound
     * @param <T>       The type of the {@code Function}'s parameter
     * @param <R>       The type of the {@code Function}'s return value
     * @return the bound {@link java.util.function.Function} as {@link Function0}
     */
    public static <T, R> Function0<R> bindParameter(java.util.function.Function<? super T, R> function,
                                                    T parameter)
    {
        return () -> function.apply(parameter);
    }

    /**
     * Converts the given {@link java.util.function.Function} into a {@code Function} that invokes
     * the given function.
     *
     * @param function the function to be converted
     * @param <T>      The type of the {@code Function}'s parameter
     * @param <R>      The type of the {@code Function}'s return value
     * @return the function invoking the given function
     */
    public static <T, R> Function<T, R> from(java.util.function.Function<T, R> function)
    {
        return function::apply;
    }

    /**
     * Creates a {@link java.util.function.Function} that calls {@link #apply(Object)} with the given parameter on the
     * consumed {@link java.util.function.Function}s.
     *
     * @param parameter the parameter to be bound
     * @param <T>       The type of the {@code Function}'s parameter
     * @param <R>       The type of the {@code Function}'s return value
     * @return the applying {@link java.util.function.Function} with bound parameter
     */
    public static <T, R> java.util.function.Function<java.util.function.Function<? super T, ? extends R>, R> boundApplyingFunction(
        T parameter)
    {
        return function -> function.apply(parameter);
    }

    /**
     * Invoke this {@code Function} with the given parameter value
     *
     * @param parameter the parameter value for the invocation
     * @return the return value of the invocation
     */
    R apply(T parameter);

    /**
     * Binds the given parameter to this {@code Function} returning a new {@link Function0} that
     * acts as an invocation of this {@code Function} with the given parameter.
     *
     * @param parameter the parameter to be bound
     * @return the bound {@code Function} as {@link Function0}
     */
    default Function0<R> bindParameter(T parameter)
    {
        return bindParameter(this, parameter);
    }

    /**
     * Converts this {@code Function} into an {@link Action} ignoring the function's return value.
     *
     * @return an {@link Action} calling this function
     */
    default Action<T> toAction()
    {
        return Action.from(this);
    }
}