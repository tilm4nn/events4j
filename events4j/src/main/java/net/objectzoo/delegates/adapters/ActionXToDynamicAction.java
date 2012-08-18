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

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.delegates.Action2;
import net.objectzoo.delegates.Action3;
import net.objectzoo.delegates.Action4;
import net.objectzoo.delegates.DynamicAction;
import net.objectzoo.delegates.helpers.DynamicInvoker;

/**
 * This adapter converts a given {@code ActionX} to a {@link DynamicAction}.
 * 
 * The calls to the {@link DynamicAction#dynamicInvoke(Object...)} method are forwarded to the
 * original action's {@code invoke} method. If a call to {@code dynamicInvoke} has the wrong
 * argument count regarding the original action an {@link IllegalArgumentException} is thrown.
 * 
 * @author tilmann
 */
public class ActionXToDynamicAction implements DynamicAction
{
	private final DynamicInvoker dynamicInvoker;
	
	/**
	 * Convert the given {@link Action} to the interface {@link DynamicAction}
	 * 
	 * @param action
	 *        the action to be converted
	 */
	public ActionXToDynamicAction(Action<?> action)
	{
		this(Action.class, action);
	}
	
	/**
	 * Convert the given {@link Action0} to the interface {@link DynamicAction}
	 * 
	 * @param action
	 *        the action to be converted
	 */
	public ActionXToDynamicAction(Action0 action)
	{
		this(Action0.class, action);
	}
	
	/**
	 * Convert the given {@link Action2} to the interface {@link DynamicAction}
	 * 
	 * @param action
	 *        the action to be converted
	 */
	public ActionXToDynamicAction(Action2<?, ?> action)
	{
		this(Action2.class, action);
	}
	
	/**
	 * Convert the given {@link Action3} to the interface {@link DynamicAction}
	 * 
	 * @param action
	 *        the action to be converted
	 */
	public ActionXToDynamicAction(Action3<?, ?, ?> action)
	{
		this(Action3.class, action);
	}
	
	/**
	 * Convert the given {@link Action4} to the interface {@link DynamicAction}
	 * 
	 * @param action
	 *        the action to be converted
	 */
	public ActionXToDynamicAction(Action4<?, ?, ?, ?> action)
	{
		this(Action4.class, action);
	}
	
	/**
	 * Convert the given {@code ActionX} to the interface {@link DynamicAction}
	 * 
	 * @param <ActionType>
	 *        the type of action to be converted
	 * @param actionType
	 *        the type of action to be converted
	 * @param action
	 *        the action to be converted
	 */
	private <ActionType> ActionXToDynamicAction(Class<ActionType> actionType, ActionType action)
	{
		dynamicInvoker = new DynamicInvoker(actionType, action);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *         if the parameter count given to this method does not match the parameter count of the
	 *         underlying original action.
	 */
	@Override
	public void dynamicInvoke(Object... params)
	{
		dynamicInvoker.dynamicInvoke(params);
	}
	
}
