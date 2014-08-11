package net.objectzoo.delegates.adapters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import net.objectzoo.delegates.Action0;
import net.objectzoo.delegates.Func0;

public class Func0ToAction0Test
{
	@SuppressWarnings("rawtypes")
	@Test
	public void invokes_Func0()
	{
		Func0 mockFunc = mock(Func0.class);
		Action0 sut = new Func0ToAction0(mockFunc);
		
		sut.invoke();
		
		verify(mockFunc).invoke();
	}
}