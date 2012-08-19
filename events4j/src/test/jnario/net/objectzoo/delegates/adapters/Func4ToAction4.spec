package net.objectzoo.delegates.adapters

import net.objectzoo.delegates.Func4
import static org.mockito.Mockito.*
import static extension org.jnario.lib.Should.*

describe Func4ToAction4
{
	fact "invokes Func4 with correct arguments"
	{
		val mockFunc = mock(typeof(Func4)) as Func4<Object, Object, Object, Object, ?>
		val sut = new Func4ToAction4<Object, Object, Object, Object>(mockFunc)
		val argument1 = new Object
		val argument2 = new Object
		val argument3 = new Object
		val argument4 = new Object
	
		sut.invoke(argument1, argument2, argument3, argument4)
	
		verify(mockFunc).invoke(argument1, argument2, argument3, argument4)
	}
}