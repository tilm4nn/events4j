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
 * A {@code Func} is a reference to a function with return value that can be invoked synchronously
 * similar to a regular Java method call.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the {@code Func}'s first parameter
 * @param <T2>
 *        The type of the {@code Func}'s second parameter
 * @param <R>
 *        The type of the {@code Func}'s return value
 */
public interface Func2<T1, T2, R>
{
	/**
	 * Invoke this {@code Func} with the given parameter values
	 * 
	 * @param parameter1
	 *        the first parameter's value for the invocation
	 * @param parameter2
	 *        the second parameter's value for the invocation
	 * @return the return value of the invocation
	 */
	public R invoke(T1 parameter1, T2 parameter2);
}