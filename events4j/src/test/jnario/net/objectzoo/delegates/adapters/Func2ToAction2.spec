package net.objectzoo.delegates.adapters

import net.objectzoo.delegates.Func2
import static org.mockito.Mockito.*
import static extension org.jnario.lib.Should.*

describe Func2ToAction2
{
	fact "invokes Func2 with correct arguments"
	{
		val mockFunc = mock(typeof(Func2)) as Func2<Object, Object,?>
		val sut = new Func2ToAction2<Object, Object>(mockFunc)
		val argument1 = new Object
		val argument2 = new Object
	
		sut.invoke(argument1, argument2)
	
		verify(mockFunc).invoke(argument1, argument2)
	}
}