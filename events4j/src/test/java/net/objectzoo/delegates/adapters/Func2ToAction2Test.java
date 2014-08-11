package net.objectzoo.delegates.adapters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import net.objectzoo.delegates.Action2;
import net.objectzoo.delegates.Func2;

public class Func2ToAction2Test
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void invokes_Func2_with_correct_arguments()
	{
		Func2 mockFunc = mock(Func2.class);
		Action2 sut = new Func2ToAction2<Object, Object>(mockFunc);
		Object argument1 = new Object();
		Object argument2 = new Object();
		
		sut.invoke(argument1, argument2);
		
		verify(mockFunc).invoke(argument1, argument2);
	}
}