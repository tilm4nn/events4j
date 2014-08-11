package net.objectzoo.delegates.adapters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import net.objectzoo.delegates.Action3;
import net.objectzoo.delegates.Func3;

public class Func3ToAction3Test
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void invokes_Func3_with_correct_arguments()
	{
		Func3 mockFunc = mock(Func3.class);
		Action3 sut = new Func3ToAction3<Object, Object, Object>(mockFunc);
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		
		sut.invoke(argument1, argument2, argument3);
		
		verify(mockFunc).invoke(argument1, argument2, argument3);
	}
}