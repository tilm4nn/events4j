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
 * A {@code Function3} is a reference to a function with return value that can be invoked
 * synchronously similar to a regular Java method call.
 *
 * @param <T1> The type of the {@code Function3}'s first parameter
 * @param <T2> The type of the {@code Function3}'s second parameter
 * @param <T3> The type of the {@code Function3}'s third parameter
 * @param <R>  The type of the {@code Function3}'s return value
 * @author tilmann
 */
@FunctionalInterface
public interface Function3<T1, T2, T3, R>
{
    /**
     * Binds the given parameters to the given {@code Function3} returning a new {@link Function0}
     * that acts as an invocation of the {@code Function3} with the given parameters.
     *
     * @param function   the {@code Function3} to be bound
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @param parameter3 the third parameter to be bound
     * @param <T1>       The type of the {@code Function3}'s first parameter
     * @param <T2>       The type of the {@code Function3}'s second parameter
     * @param <T3>       The type of the {@code Function3}'s third parameter
     * @param <R>        The type of the {@code Function3}'s return value
     * @return the bound {@code Function3} as {@link Function0}
     */
    public static <T1, T2, T3, R> Function0<R> bindParameters(
        Function3<? super T1, ? super T2, ? super T3, R> function,
        T1 parameter1, T2 parameter2,
        T3 parameter3)
    {
        return () -> function.apply(parameter1, parameter2, parameter3);
    }

    /**
     * Creates a {@link java.util.function.Function} that calls {@link #apply(Object, Object, Object)} with the given
     * parameters on the consumed {@code Function3}s.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @param parameter3 the third parameter to be bound
     * @param <T1>       The type of the {@code Function3}'s first parameter
     * @param <T2>       The type of the {@code Function3}'s second parameter
     * @param <T3>       The type of the {@code Function3}'s third parameter
     * @param <R>        The type of the {@code Function3}'s return value
     * @return the applying {@link java.util.function.Function} with bound parameters
     */
    public static <T1, T2, T3, R> java.util.function.Function<Function3<? super T1, ? super T2, ? super T3, ? extends R>, R> boundApplyingFunction(
        T1 parameter1,
        T2 parameter2,
        T3 parameter3)
    {
        return function -> function.apply(parameter1, parameter2, parameter3);
    }

    /**
     * Invoke this {@code Function3} with the given parameter values
     *
     * @param parameter1 the first parameter's value for the invocation
     * @param parameter2 the second parameter's value for the invocation
     * @param parameter3 the third parameter's value for the invocation
     * @return the return value of the invocation
     */
    R apply(T1 parameter1, T2 parameter2, T3 parameter3);

    /**
     * Binds the given parameters to this {@code Function3} returning a new {@link Function0} that
     * acts as an invocation of this {@code Function3} with the given parameters.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @param parameter3 the third parameter to be bound
     * @return the bound {@code Function3} as {@link Function0}
     */
    default Function0<R> bindParameters(T1 parameter1, T2 parameter2, T3 parameter3)
    {
        return bindParameters(this, parameter1, parameter2, parameter3);
    }

    /**
     * Converts this {@code Function3} into an {@link Action3} ignoring the function's return value.
     *
     * @return an {@link Action3} calling this function
     */
    default Action3<T1, T2, T3> toAction()
    {
        return Action3.from(this);
    }
}