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
 * A {@code Function4} is a reference to a function with return value that can be invoked
 * synchronously similar to a regular Java method call.
 *
 * @param <T1> The type of the {@code Function4}'s first parameter
 * @param <T2> The type of the {@code Function4}'s second parameter
 * @param <T3> The type of the {@code Function4}'s third parameter
 * @param <T4> The type of the {@code Function4}'s fourth parameter
 * @param <R>  The type of the {@code Function4}'s return value
 * @author tilmann
 */
@FunctionalInterface
public interface Function4<T1, T2, T3, T4, R>
{

    /**
     * Binds the given parameters to the given {@code Function4} returning a new {@link Function0}
     * that acts as an invocation of the {@code Function4} with the given parameters.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @param parameter3 the third parameter to be bound
     * @param parameter4 the fourth parameter to be bound
     * @return the bound {@code Function4} as {@link Function0}
     */
    public static <T1, T2, T3, T4, R> Function0<R> bindParameters(
        Function4<? super T1, ? super T2, ? super T3, ? super T4, R> function,
        T1 parameter1, T2 parameter2,
        T3 parameter3, T4 parameter4)
    {
        return () -> function.apply(parameter1, parameter2, parameter3, parameter4);
    }

    /**
     * Invoke this {@code Function4} with the given parameter values
     *
     * @param parameter1 the first parameter's value for the invocation
     * @param parameter2 the second parameter's value for the invocation
     * @param parameter3 the third parameter's value for the invocation
     * @param parameter4 the fourth parameter's value for the invocation
     * @return the return value of the invocation
     */
    R apply(T1 parameter1, T2 parameter2, T3 parameter3, T4 parameter4);

    /**
     * Binds the given parameters to this {@code Function4} returning a new {@link Function0} that
     * acts as an invocation of this {@code Function4} with the given parameters.
     *
     * @param parameter1 the first parameter to be bound
     * @param parameter2 the second parameter to be bound
     * @param parameter3 the third parameter to be bound
     * @param parameter4 the fourth parameter to be bound
     * @return the bound {@code Function4} as {@link Function0}
     */
    default Function0<R> bindParameters(T1 parameter1, T2 parameter2, T3 parameter3, T4 parameter4)
    {
        return bindParameters(this, parameter1, parameter2, parameter3, parameter4);
    }

    /**
     * Converts this {@code Function4} into an {@link Action4} ignoring the function's return value.
     *
     * @return an {@link Action4} calling this function
     */
    default Action4<T1, T2, T3, T4> toAction()
    {
        return Action4.from(this);
    }

    //	/**
    //	 * Creates a {@link Function} that calls {@link #apply(Object, Object, Object, Object)} with the
    //	 * given parameters on the consumed {@code Function4}s.
    //	 *
    //	 * @param parameter1
    //	 *        the first parameter to be bound
    //	 * @param parameter2
    //	 *        the second parameter to be bound
    //	 * @param parameter3
    //	 *        the third parameter to be bound
    //	 * @param parameter4
    //	 *        the fourth parameter to be bound
    //	 * @return the applying {@link Function} with bound parameters
    //	 */
    //	public static <T1, T2, T3, T4, R> Function<Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R>, R> boundApplyingFunction(T1 parameter1,
    //																																				T2 parameter2,
    //																																				T3 parameter3,
    //																																				T4 parameter4)
    //	{
    //		return function -> function.apply(parameter1, parameter2, parameter3, parameter4);
    //	}
}