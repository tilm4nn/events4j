package net.objectzoo.delegates.adapters

import net.objectzoo.delegates.Func
import static org.mockito.Mockito.*
import static extension org.jnario.lib.Should.*

describe FuncToAction
{
	fact "invokes Func with correct arguments"
	{
		val mockFunc = mock(typeof(Func)) as Func<Object,?>
		val sut = new FuncToAction<Object>(mockFunc)
		val argument = new Object
	
		sut.invoke(argument)
	
		verify(mockFunc).invoke(argument)
	}
}