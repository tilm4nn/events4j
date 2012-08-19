package net.objectzoo.delegates.adapters

import net.objectzoo.delegates.Func0
import static org.mockito.Mockito.*
import static extension org.jnario.lib.Should.*

describe Func0ToAction0
{
	fact "invokes Func0"
	{
		val mockFunc = mock(typeof(Func0)) as Func0<?>
		
		val sut = new Func0ToAction0(mockFunc)
	
		sut.invoke()
	
		verify(mockFunc).invoke()
	}
}