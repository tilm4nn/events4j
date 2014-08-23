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
import java.util.function.Supplier;

/**
 * An {@code Action0} is a reference to a procedure without return value that can be invoked
 * synchronously similar to a regular Java method call.
 *
 * @author tilmann
 */
@FunctionalInterface
public interface Action0
{
    /**
     * Wraps the given {@code Action0} in a {@link Runnable}
     *
     * @param action0 the {@code Action0} to wrap
     * @return the {@link Runnable} that executes the {@code Action0}
     */
    public static Runnable toRunnable(Action0 action0)
    {
        return action0::start;
    }

    /**
     * Creates a {@link Consumer} that calls {@link #start()} on the consumed {@code Action0}s.
     *
     * @return the starting {@link Consumer}
     */
    public static Consumer<Action0> startingConsumer()
    {
        return Action0::start;
    }

    /**
     * Converts the given {@link Supplier} into an {@code Action0} that invokes the supplier and
     * then ignores its return value.
     *
     * @param supplier the supplier to be converted
     * @return the action invoking the supplier
     */
    public static Action0 from(Supplier<?> supplier)
    {
        return supplier::get;
    }

    /**
     * Invoke this {@code Action0}
     */
    void start();

    /**
     * Wraps this {@code Action0} in a {@link Runnable}
     *
     * @return the {@link Runnable} that executes this {@code Action0}
     */
    default Runnable toRunnable()
    {
        return toRunnable(this);
    }
}
