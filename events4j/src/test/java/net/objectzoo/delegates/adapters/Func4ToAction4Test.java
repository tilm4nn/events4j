package net.objectzoo.delegates.adapters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import net.objectzoo.delegates.Action4;
import net.objectzoo.delegates.Func4;

public class Func4ToAction4Test
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void invokes_Func4_with_correct_arguments()
	{
		Func4 mockFunc = mock(Func4.class);
		Action4 sut = new Func4ToAction4<Object, Object, Object, Object>(mockFunc);
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		Object argument4 = new Object();
		
		sut.invoke(argument1, argument2, argument3, argument4);
		
		verify(mockFunc).invoke(argument1, argument2, argument3, argument4);
	}
}