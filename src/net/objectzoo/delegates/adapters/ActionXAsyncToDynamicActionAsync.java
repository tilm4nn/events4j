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
package net.objectzoo.delegates.adapters;

import net.objectzoo.delegates.Action0Async;
import net.objectzoo.delegates.Action2Async;
import net.objectzoo.delegates.Action3Async;
import net.objectzoo.delegates.Action4Async;
import net.objectzoo.delegates.ActionAsync;
import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.DynamicActionAsync;
import net.objectzoo.delegates.helpers.DynamicAsyncInvoker;

/**
 * This adapter converts a given {@code ActionXAsync} to a {@link DynamicActionAsync}.
 * 
 * The calls to the
 * {@link DynamicActionAsync#beginDynamicInvoke(ActionAsyncCallback, Object, Object...)} method are
 * forwarded to the original action's {@code beginInvoke} method. If a call to
 * {@code beginDynamicInvoke} has the wrong argument count regarding the original action an
 * {@link IllegalArgumentException} is thrown.
 * 
 * @author tilmann
 */
public class ActionXAsyncToDynamicActionAsync implements DynamicActionAsync
{
	private final DynamicAsyncInvoker dynamicInvoker;
	
	/**
	 * Convert the given {@link ActionAsync} to the interface {@link DynamicActionAsync}
	 * 
	 * @param action
	 *        the action to be converted
	 */
	public ActionXAsyncToDynamicActionAsync(ActionAsync<?> action)
	{
		this(ActionAsync.class, action);
	}
	
	/**
	 * Convert the given {@link Action0Async} to the interface {@link DynamicActionAsync}
	 * 
	 * @param action
	 *        the action to be converted
	 */
	public ActionXAsyncToDynamicActionAsync(Action0Async action)
	{
		this(Action0Async.class, action);
	}
	
	/**
	 * Convert the given {@link Action2Async} to the interface {@link DynamicActionAsync}
	 * 
	 * @param action
	 *        the action to be converted
	 */
	public ActionXAsyncToDynamicActionAsync(Action2Async<?, ?> action)
	{
		this(Action2Async.class, action);
	}
	
	/**
	 * Convert the given {@link Action3Async} to the interface {@link DynamicActionAsync}
	 * 
	 * @param action
	 *        the action to be converted
	 */
	public ActionXAsyncToDynamicActionAsync(Action3Async<?, ?, ?> action)
	{
		this(Action3Async.class, action);
	}
	
	/**
	 * Convert the given {@link Action4Async} to the interface {@link DynamicActionAsync}
	 * 
	 * @param action
	 *        the action to be converted
	 */
	public ActionXAsyncToDynamicActionAsync(Action4Async<?, ?, ?, ?> action)
	{
		this(Action4Async.class, action);
	}
	
	/**
	 * Convert the given {@code ActionXAsync} to the interface {@link DynamicActionAsync}
	 * 
	 * @param <ActionType>
	 *        the type of action to be converted
	 * @param actionType
	 *        the type of action to be converted
	 * @param action
	 *        the action to be converted
	 */
	private <ActionType> ActionXAsyncToDynamicActionAsync(Class<ActionType> actionType,
														  ActionType action)
	{
		dynamicInvoker = new DynamicAsyncInvoker(actionType, action);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *         if the parameter count given to this method does not match the parameter count of the
	 *         underlying original action.
	 */
	@Override
	public ActionAsyncResult beginDynamicInvoke(ActionAsyncCallback callback, Object asyncState,
												Object... params)
	{
		return dynamicInvoker.beginDynamicInvoke(new ActionCallbackToFuncCallback(callback),
			asyncState, params);
	}
}
