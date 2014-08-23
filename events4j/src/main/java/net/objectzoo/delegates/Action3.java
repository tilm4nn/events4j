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
 * An {@code Action3} is a reference to a procedure without return value that can be invoked
 * synchronously similar to a regular Java method call.
 *
 * @param <T1> The type of the {@code Action3}'s first parameter
 * @param <T2> The type of the {@code Action3}'s second parameter
 * @param <T3> The type of the {@code Action3}'s third parameter
 * @author tilmann
 */
@FunctionalInterface
public interface Action3<T1, T2, T3>
{
    /**
     * Binds the given parameters to the given {@code Action3} returning a new {@link Action0} that
     * acts as an invocation of the {@code Action3} with the given parameters.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @param parameter3 the third parameter to be bound
     * @return the bound {@code Action3} as {@link Action0}
     */
    public static <T1, T2, T3> Action0 bindParameters(Action3<? super T1, ? super T2, ? super T3> action,
                                                      T1 parameter1, T2 parameter2, T3 parameter3)
    {
        return () -> action.accept(parameter1, parameter2, parameter3);
    }

    /**
     * Creates a {@link Consumer} that calls {@link #accept(Object, Object, Object)} with the given
     * parameters on the consumed {@code Action3}s.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @param parameter3 the third parameter to be bound
     * @return the accepting {@link Consumer} with bound parameters
     */
    public static <T1, T2, T3> Consumer<Action3<? super T1, ? super T2, ? super T3>> boundAcceptingConsumer(
        T1 parameter1,
        T2 parameter2,
        T3 parameter3)
    {
        return action -> action.accept(parameter1, parameter2, parameter3);
    }

    /**
     * Converts the given {@link Function3} into an {@code Action3} that invokes the function and
     * then ignores its return value.
     *
     * @param function the function to be converted
     * @return the action invoking the function
     */
    public static <T1, T2, T3> Action3<T1, T2, T3> from(Function3<T1, T2, T3, ?> function)
    {
        return function::apply;
    }

    /**
     * Invoke this {@code Action3} with the given parameter values
     *
     * @param parameter1 the first parameter's value for the invocation
     * @param parameter2 the second parameter's value for the invocation
     * @param parameter3 the third parameter's value for the invocation
     */
    void accept(T1 parameter1, T2 parameter2, T3 parameter3);

    /**
     * Binds the given parameters to this {@code Action3} returning a new {@link Action0} that acts
     * as an invocation of this {@code Action3} with the given parameters.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @param parameter3 the third parameter to be bound
     * @return the bound {@code Action3} as {@link Action0}
     */
    default Action0 bindParameters(T1 parameter1, T2 parameter2, T3 parameter3)
    {
        return bindParameters(this, parameter1, parameter2, parameter3);
    }
}
