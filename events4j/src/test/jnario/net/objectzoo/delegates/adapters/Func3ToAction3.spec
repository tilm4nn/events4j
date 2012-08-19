package net.objectzoo.delegates.adapters

import net.objectzoo.delegates.Func3
import static org.mockito.Mockito.*
import static extension org.jnario.lib.Should.*

describe Func3ToAction3
{
	fact "invokes Func3 with correct arguments"
	{
		val mockFunc = mock(typeof(Func3)) as Func3<Object, Object, Object, ?>
		val sut = new Func3ToAction3<Object, Object, Object>(mockFunc)
		val argument1 = new Object
		val argument2 = new Object
		val argument3 = new Object
	
		sut.invoke(argument1, argument2, argument3)
	
		verify(mockFunc).invoke(argument1, argument2, argument3)
	}
}