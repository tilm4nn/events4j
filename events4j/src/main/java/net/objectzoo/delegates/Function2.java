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

import java.util.function.BiFunction;

/**
 * A {@code Function2} is a reference to a function with return value that can be invoked
 * synchronously similar to a regular Java method call. To enable interoperability with Java 8
 * libraries {@code Function2} is replaceable with its super interface {@link BiFunction} in many
 * situations.
 *
 * @param <T1> The type of the {@code Function2}'s first parameter
 * @param <T2> The type of the {@code Function2}'s second parameter
 * @param <R>  The type of the {@code Function2}'s return value
 * @author tilmann
 */
@FunctionalInterface
public interface Function2<T1, T2, R> extends BiFunction<T1, T2, R>
{
    /**
     * Binds the given parameters to the given {@link BiFunction} returning a new {@link Function0}
     * that acts as an invocation of the {@link BiFunction} with the given parameters.
     *
     * @param function   the {@code BiFunction} to be bound
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @param <T1>       The type of the {@code Function2}'s first parameter
     * @param <T2>       The type of the {@code Function2}'s second parameter
     * @param <R>        The type of the {@code Function2}'s return value
     * @return the bound {@link BiFunction} as {@link Function0}
     */
    public static <T1, T2, R> Function0<R> bindParameters(BiFunction<? super T1, ? super T2, R> function,
                                                          T1 parameter1, T2 parameter2)
    {
        return () -> function.apply(parameter1, parameter2);
    }

    /**
     * Converts the given {@link BiFunction} into a {@code Function2} that invokes the given
     * function.
     *
     * @param function the function to be converted
     * @param <T1>     The type of the {@code Function2}'s first parameter
     * @param <T2>     The type of the {@code Function2}'s second parameter
     * @param <R>      The type of the {@code Function2}'s return value
     * @return the function invoking the given function
     */
    public static <T1, T2, R> Function2<T1, T2, R> from(BiFunction<T1, T2, R> function)
    {
        return function::apply;
    }

    /**
     * Creates a {@link java.util.function.Function} that calls {@link #apply(Object, Object)} with the given
     * parameters on the consumed {@link BiFunction}s.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @param <T1>       The type of the {@code Function2}'s first parameter
     * @param <T2>       The type of the {@code Function2}'s second parameter
     * @param <R>        The type of the {@code Function2}'s return value
     * @return the applying {@link java.util.function.Function} with bound parameters
     */
    public static <T1, T2, R> java.util.function.Function<BiFunction<? super T1, ? super T2, ? extends R>, R> boundApplyingFunction(
        T1 parameter1,
        T2 parameter2)
    {
        return function -> function.apply(parameter1, parameter2);
    }

    /**
     * Invoke this {@code Function2} with the given parameter values
     *
     * @param parameter1 the first parameter's value for the invocation
     * @param parameter2 the second parameter's value for the invocation
     * @return the return value of the invocation
     */
    R apply(T1 parameter1, T2 parameter2);

    /**
     * Binds the given parameters to this {@code Function2} returning a new {@link Function0} that
     * acts as an invocation of this {@code Function2} with the given parameters.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @return the bound {@code Function2} as {@link Function0}
     */
    default Function0<R> bindParameters(T1 parameter1, T2 parameter2)
    {
        return bindParameters(this, parameter1, parameter2);
    }

    /**
     * Converts this {@code Function2} into an {@link Action2} ignoring the function's return value.
     *
     * @return an {@link Action2} calling this function
     */
    default Action2<T1, T2> toAction()
    {
        return Action2.from(this);
    }
}