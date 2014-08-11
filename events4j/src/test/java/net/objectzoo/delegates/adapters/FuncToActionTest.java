package net.objectzoo.delegates.adapters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Func;

public class FuncToActionTest
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void invokes_Func_with_correct_arguments()
	{
		Func mockFunc = mock(Func.class);
		Action sut = new FuncToAction<Object>(mockFunc);
		Object argument = new Object();
		
		sut.invoke(argument);
		
		verify(mockFunc).invoke(argument);
	}
}