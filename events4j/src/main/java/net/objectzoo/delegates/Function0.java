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

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * A {@code Function0} is a reference to a function with return value that can be invoked
 * synchronously similar to a regular Java method call.
 * 
 * @author tilmann
 * 
 * @param <R>
 *        The type of the {@code Function0}'s return value
 */
@FunctionalInterface
public interface Function0<R> extends Supplier<R>
{
	/**
	 * Invoke this {@code Function0}
	 * 
	 * @return the return value of the invocation
	 */
	R get();
	
	/**
	 * Wraps this {@code Function0} in a {@link Callable}
	 * 
	 * @return the {@link Callable} that calls this {@code Function0}
	 */
	default Callable<R> toCallable()
	{
		return toCallable(this);
	}
	
	/**
	 * Converts this {@code Function0} into an {@link Action0} ignoring the function's return value.
	 * 
	 * @return an {@link Action0} calling this function
	 */
	default Action0 toAction()
	{
		return Action0.from(this);
	}
	
	/**
	 * Wraps the given {@link Supplier} in a {@link Callable}
	 * 
	 * @param supplier
	 *        the {@link Supplier} to wrap
	 * @return the {@link Callable} that calls the {@link Supplier}
	 */
	public static <R> Callable<R> toCallable(Supplier<R> supplier)
	{
		return supplier::get;
	}
	
	//	/**
	//	 * Creates a {@link Function} that calls {@link #get()} on the consumed {@link Suppliers}s.
	//	 * 
	//	 * @return the getting {@link Consumer}
	//	 */
	//	public static <R> Function<Supplier<? extends R>, R> gettingConsumer()
	//	{
	//		return function -> function.get();
	//	}
	
	/**
	 * Converts the given {@link Supplier} into a {@code Function0} that invokes the given supplier.
	 * 
	 * @param supplier
	 *        the supplier to be converted
	 * @return the function invoking the given supplier
	 */
	public static <R> Function0<R> from(Supplier<R> supplier)
	{
		return supplier::get;
	}
}