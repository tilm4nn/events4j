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
package net.objectzoo.events.impl;

import net.objectzoo.delegates.Action4Async;

/**
 * An {@code Event4AsyncDelegate} implements {@link Action4Async} as well as {@link Event4Delegate}
 * and is meant as a specification for implementations that delegate calls to the
 * {@link Action4Async} interface asynchronously to all subscribers of the {@link Event4Delegate}
 * interface.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the first information parameter the event provides
 * @param <T2>
 *        The type of the second information parameter the event provides
 * @param <T3>
 *        The type of the third information parameter the event provides
 * @param <T4>
 *        The type of the fourth information parameter the event provides
 */
public interface Event4AsyncDelegate<T1, T2, T3, T4> extends Action4Async<T1, T2, T3, T4>,
	Event4Delegate<T1, T2, T3, T4>
{
	
}
