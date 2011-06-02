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
 * An {@code ActionAsyncCallback} is a callback that is called when the asynchronous invocation of
 * an {@link ActionAsync} is completed.
 * 
 * The {@code interface ActionAsyncCallback} can be implemented by API users and given to the
 * {@link ActionAsync#beginInvoke(ActionAsyncCallback, Object, Object)} method.
 * 
 * @author tilmann
 */
public interface ActionAsyncCallback
{
	/**
	 * The invocation of the {@link ActionAsync} has completed.
	 * 
	 * @param result
	 *        the {@link ActionAsyncResult} associated with the asynchronous invocation
	 */
	public void invocationFinished(ActionAsyncResult result);
	
}
