package net.objectzoo.delegates.adapters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import net.objectzoo.delegates.DynamicAction;
import net.objectzoo.delegates.DynamicFunc;

public class DynamicFuncToDynamicActionTest
{
	@Test
	public void invokes_DynamicFunc_with_0_argument()
	{
		DynamicFunc mockFunc = mock(DynamicFunc.class);
		DynamicAction sut = new DynamicFuncToDynamicAction(mockFunc);
		
		sut.dynamicInvoke();
		
		verify(mockFunc).dynamicInvoke();
	}
	
	@Test
	public void invokes_DynamicFunc_with_1_argument()
	{
		DynamicFunc mockFunc = mock(DynamicFunc.class);
		DynamicAction sut = new DynamicFuncToDynamicAction(mockFunc);
		Object argument = new Object();
		
		sut.dynamicInvoke(argument);
		
		verify(mockFunc).dynamicInvoke(argument);
	}
	
	@Test
	public void invokes_DynamicFunc_with_4_argument()
	{
		DynamicFunc mockFunc = mock(DynamicFunc.class);
		DynamicAction sut = new DynamicFuncToDynamicAction(mockFunc);
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		Object argument4 = new Object();
		
		sut.dynamicInvoke(argument1, argument2, argument3, argument4);
		
		verify(mockFunc).dynamicInvoke(argument1, argument2, argument3, argument4);
	}
}