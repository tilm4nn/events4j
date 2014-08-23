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

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * An {@code Action2} is a reference to a procedure without return value that can be invoked
 * synchronously similar to a regular Java method call. To enable interoperability with Java 8
 * libraries {@code Action} is replaceable with its super interface {@link BiConsumer} in many
 * situations.
 *
 * @param <T1> The type of the {@code Action2}'s first parameter
 * @param <T2> The type of the {@code Action2}'s second parameter
 * @author tilmann
 */
@FunctionalInterface
public interface Action2<T1, T2> extends BiConsumer<T1, T2>
{
    /**
     * Binds the given parameters to the given {@link BiConsumer} returning a new {@link Action0}
     * that acts as an invocation of the {@link BiConsumer} with the given parameters.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @return the bound {@link BiConsumer} as {@link Action0}
     */
    public static <T1, T2> Action0 bindParameters(BiConsumer<? super T1, ? super T2> action,
                                                  T1 parameter1, T2 parameter2)
    {
        return () -> action.accept(parameter1, parameter2);
    }

    /**
     * Creates a {@link Consumer} that calls {@link #accept(Object, Object)} with the given
     * parameters on the consumed {@link BiConsumer}s.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @return the accepting {@link Consumer} with bound parameters
     */
    public static <T1, T2> Consumer<BiConsumer<? super T1, ? super T2>> boundAcceptingConsumer(T1 parameter1,
                                                                                               T2 parameter2)
    {
        return action -> action.accept(parameter1, parameter2);
    }

    /**
     * Converts the given {@link BiConsumer} into an {@code Action2} that invokes the consumer.
     *
     * @param consumer the consumer to be converted
     * @return the action invoking the consumer
     */
    public static <T1, T2> Action2<T1, T2> from(BiConsumer<T1, T2> consumer)
    {
        return consumer::accept;
    }

    /**
     * Converts the given {@link BiFunction} into an {@code Action2} that invokes the function and
     * then ignores its return value.
     *
     * @param function the function to be converted
     * @return the action invoking the function
     */
    public static <T1, T2> Action2<T1, T2> from(BiFunction<T1, T2, ?> function)
    {
        return function::apply;
    }

    /**
     * Invoke this {@code Action2} with the given parameter values
     *
     * @param parameter1 the first parameter's value for the invocation
     * @param parameter2 the second parameter's value for the invocation
     */
    void accept(T1 parameter1, T2 parameter2);

    /**
     * Binds the given parameters to this {@code Action2} returning a new {@link Action0} that acts
     * as an invocation of this {@code Action2} with the given parameters.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @return the bound {@code Action2} as {@link Action0}
     */
    default Action0 bindParameters(T1 parameter1, T2 parameter2)
    {
        return bindParameters(this, parameter1, parameter2);
    }
}
