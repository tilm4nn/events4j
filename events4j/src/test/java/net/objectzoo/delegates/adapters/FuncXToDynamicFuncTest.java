package net.objectzoo.delegates.adapters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import net.objectzoo.delegates.DynamicFunc;
import net.objectzoo.delegates.Func;
import net.objectzoo.delegates.Func0;
import net.objectzoo.delegates.Func2;
import net.objectzoo.delegates.Func3;
import net.objectzoo.delegates.Func4;

public class FuncXToDynamicFuncTest
{
	@SuppressWarnings("rawtypes")
	@Test
	public void invokes_Func0()
	{
		Func0 mockFunc = mock(Func0.class);
		DynamicFunc sut = new FuncXToDynamicFunc(mockFunc);
		
		sut.dynamicInvoke();
		
		verify(mockFunc).invoke();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void invokes_Func_with_correct_arguments()
	{
		Func mockFunc = mock(Func.class);
		DynamicFunc sut = new FuncXToDynamicFunc(mockFunc);
		Object argument = new Object();
		
		sut.dynamicInvoke(argument);
		
		verify(mockFunc).invoke(argument);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void invokes_Func2_with_correct_arguments()
	{
		Func2 mockFunc = mock(Func2.class);
		DynamicFunc sut = new FuncXToDynamicFunc(mockFunc);
		Object argument1 = new Object();
		Object argument2 = new Object();
		
		sut.dynamicInvoke(argument1, argument2);
		
		verify(mockFunc).invoke(argument1, argument2);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void invokes_Func3_with_correct_arguments()
	{
		Func3 mockFunc = mock(Func3.class);
		DynamicFunc sut = new FuncXToDynamicFunc(mockFunc);
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		
		sut.dynamicInvoke(argument1, argument2, argument3);
		
		verify(mockFunc).invoke(argument1, argument2, argument3);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void invokes_Func4_with_correct_arguments()
	{
		Func4 mockFunc = mock(Func4.class);
		DynamicFunc sut = new FuncXToDynamicFunc(mockFunc);
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		Object argument4 = new Object();
		
		sut.dynamicInvoke(argument1, argument2, argument3, argument4);
		
		verify(mockFunc).invoke(argument1, argument2, argument3, argument4);
	}
}