package net.objectzoo.delegates.adapters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.delegates.Action2;
import net.objectzoo.delegates.Action3;
import net.objectzoo.delegates.Action4;
import net.objectzoo.delegates.DynamicAction;

public class ActionXToDynamicActionTest
{
	@Test
	public void invokes_Action0()
	{
		Action0 mockAction = mock(Action0.class);
		DynamicAction sut = new ActionXToDynamicAction(mockAction);
		
		sut.dynamicInvoke();
		
		verify(mockAction).invoke();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void invokes_Action_with_correct_arguments()
	{
		Action mockAction = mock(Action.class);
		DynamicAction sut = new ActionXToDynamicAction(mockAction);
		Object argument = new Object();
		
		sut.dynamicInvoke(argument);
		
		verify(mockAction).invoke(argument);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void invokes_Action2_with_correct_arguments()
	{
		Action2 mockAction = mock(Action2.class);
		DynamicAction sut = new ActionXToDynamicAction(mockAction);
		Object argument1 = new Object();
		Object argument2 = new Object();
		
		sut.dynamicInvoke(argument1, argument2);
		
		verify(mockAction).invoke(argument1, argument2);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void invokes_Action3_with_correct_arguments()
	{
		Action3 mockAction = mock(Action3.class);
		DynamicAction sut = new ActionXToDynamicAction(mockAction);
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		
		sut.dynamicInvoke(argument1, argument2, argument3);
		
		verify(mockAction).invoke(argument1, argument2, argument3);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void invokes_Action4_with_correct_arguments()
	{
		Action4 mockAction = mock(Action4.class);
		DynamicAction sut = new ActionXToDynamicAction(mockAction);
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		Object argument4 = new Object();
		
		sut.dynamicInvoke(argument1, argument2, argument3, argument4);
		
		verify(mockAction).invoke(argument1, argument2, argument3, argument4);
	}
}