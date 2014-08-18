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
 * An {@code Action} is a reference to a procedure without return value that can be invoked
 * synchronously similar to a regular Java method call. To enable interoperability with Java 8
 * libraries {@code Action} is replaceable with its super interface {@link Consumer} in many
 * situations.
 * 
 * @author tilmann
 * 
 * @param <T>
 *        The type of the {@code Action}'s parameter
 */
@FunctionalInterface
public interface Action<T> extends Consumer<T>
{
	/**
	 * Invoke this {@code Action} with the given parameter value
	 * 
	 * @param parameter
	 *        the parameter value for the invocation
	 */
	void accept(T parameter);
	
	/**
	 * Binds the given parameter to this {@code Action} returning a new {@link Action0} that acts as
	 * an invocation of this {@code Action} with the given parameter.
	 * 
	 * @param parameter
	 *        the parameter to be bound
	 * @return the bound {@code Action} as {@link Action0}
	 */
	default Action0 bindParameter(T parameter)
	{
		return bindParameter(this, parameter);
	}
	
	/**
	 * Binds the given parameter to the given {@link Consumer} returning a new {@link Action0} that
	 * acts as an invocation of the {@link Consumer} with the given parameter.
	 * 
	 * @param parameter
	 *        the parameter to be bound
	 * @return the bound {@link Consumer} as {@link Action0}
	 */
	public static <T> Action0 bindParameter(Consumer<? super T> action, T parameter)
	{
		return () -> action.accept(parameter);
	}
	
	/**
	 * Creates a {@link Consumer} that calls {@link #accept(Object)} with the given parameter on the
	 * consumed {@link Consumer}s.
	 * 
	 * @param parameter
	 *        the parameter to be bound
	 * @return the accepting {@link Consumer} with bound parameter
	 */
	public static <T> Consumer<Consumer<? super T>> boundAcceptingConsumer(T parameter)
	{
		return action -> action.accept(parameter);
	}
	
	/**
	 * Converts the given {@link Consumer} into an {@code Action} that invokes the consumer.
	 * 
	 * @param consumer
	 *        the consumer to be converted
	 * @return the action invoking the consumer
	 */
	public static <T> Action<T> from(Consumer<T> consumer)
	{
		return t -> consumer.accept(t);
	}
	
	/**
	 * Converts the given {@link java.util.function.Function} into an {@code Action} that invokes
	 * the function and then ignores its return value.
	 * 
	 * @param function
	 *        the function to be converted
	 * @return the action invoking the function
	 */
	public static <T> Action<T> from(java.util.function.Function<T, ?> function)
	{
		return t -> function.apply(t);
	}
}
