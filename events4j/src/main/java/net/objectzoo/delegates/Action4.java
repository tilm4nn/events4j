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
 * An {@code Action4} is a reference to a procedure without return value that can be invoked
 * synchronously similar to a regular Java method call.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the {@code Action4}'s first parameter
 * @param <T2>
 *        The type of the {@code Action4}'s second parameter
 * @param <T3>
 *        The type of the {@code Action4}'s third parameter
 * @param <T4>
 *        The type of the {@code Action4}'s fourth parameter
 */
public interface Action4<T1, T2, T3, T4>
{
	/**
	 * Invoke this {@code Action4} with the given parameter values
	 * 
	 * @param parameter1
	 *        the first parameter's value for the invocation
	 * @param parameter2
	 *        the second parameter's value for the invocation
	 * @param parameter3
	 *        the third parameter's value for the invocation
	 * @param parameter4
	 *        the fourth parameter's value for the invocation
	 */
	public void invoke(T1 parameter1, T2 parameter2, T3 parameter3, T4 parameter4);
}