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

import java.util.function.Consumer;

/**
 * An {@code Action4} is a reference to a procedure without return value that can be invoked
 * synchronously similar to a regular Java method call.
 *
 * @param <T1> The type of the {@code Action4}'s first parameter
 * @param <T2> The type of the {@code Action4}'s second parameter
 * @param <T3> The type of the {@code Action4}'s third parameter
 * @param <T4> The type of the {@code Action4}'s fourth parameter
 * @author tilmann
 */
@FunctionalInterface
public interface Action4<T1, T2, T3, T4>
{
    /**
     * Binds the given parameters to the given {@code Action4} returning a new {@link Action0} that
     * acts as an invocation of the {@code Action4} with the given parameters.
     *
     * @param action     the {@code Action4} to be bound
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @param parameter3 the third parameter to be bound
     * @param parameter4 the fourth parameter to be bound
     * @param <T1>       The type of the {@code Action4}'s first parameter
     * @param <T2>       The type of the {@code Action4}'s second parameter
     * @param <T3>       The type of the {@code Action4}'s third parameter
     * @param <T4>       The type of the {@code Action4}'s fourth parameter
     * @return the bound {@code Action4} as {@link Action0}
     */
    public static <T1, T2, T3, T4> Action0 bindParameters(
        Action4<? super T1, ? super T2, ? super T3, ? super T4> action,
        T1 parameter1, T2 parameter2,
        T3 parameter3, T4 parameter4)
    {
        return () -> action.accept(parameter1, parameter2, parameter3, parameter4);
    }

    /**
     * Creates a {@link Consumer} that calls {@link #accept(Object, Object, Object, Object)} with
     * the given parameters on the consumed {@code Action4}s.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @param parameter3 the third parameter to be bound
     * @param parameter4 the fourth parameter to be bound
     * @param <T1>       The type of the {@code Action4}'s first parameter
     * @param <T2>       The type of the {@code Action4}'s second parameter
     * @param <T3>       The type of the {@code Action4}'s third parameter
     * @param <T4>       The type of the {@code Action4}'s fourth parameter
     * @return the accepting {@link Consumer} with bound parameters
     */
    public static <T1, T2, T3, T4> Consumer<Action4<? super T1, ? super T2, ? super T3, ? super T4>> boundAcceptingConsumer(
        T1 parameter1,
        T2 parameter2,
        T3 parameter3,
        T4 parameter4)
    {
        return action -> action.accept(parameter1, parameter2, parameter3, parameter4);
    }

    /**
     * Converts the given {@link Function4} into an {@code Action4} that invokes the function and
     * then ignores its return value.
     *
     * @param function the function to be converted
     * @param <T1>     The type of the {@code Function4}'s first parameter
     * @param <T2>     The type of the {@code Function4}'s second parameter
     * @param <T3>     The type of the {@code Function4}'s third parameter
     * @param <T4>     The type of the {@code Function4}'s fourth parameter
     * @return the action invoking the function
     */
    public static <T1, T2, T3, T4> Action4<T1, T2, T3, T4> from(Function4<T1, T2, T3, T4, ?> function)
    {
        return function::apply;
    }

    /**
     * Invoke this {@code Action4} with the given parameter values
     *
     * @param parameter1 the first parameter's value for the invocation
     * @param parameter2 the second parameter's value for the invocation
     * @param parameter3 the third parameter's value for the invocation
     * @param parameter4 the fourth parameter's value for the invocation
     */
    void accept(T1 parameter1, T2 parameter2, T3 parameter3, T4 parameter4);

    /**
     * Binds the given parameters to this {@code Action4} returning a new {@link Action0} that acts
     * as an invocation of this {@code Action4} with the given parameters.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @param parameter3 the third parameter to be bound
     * @param parameter4 the fourth parameter to be bound
     * @return the bound {@code Action4} as {@link Action0}
     */
    default Action0 bindParameters(T1 parameter1, T2 parameter2, T3 parameter3, T4 parameter4)
    {
        return bindParameters(this, parameter1, parameter2, parameter3, parameter4);
    }
}
