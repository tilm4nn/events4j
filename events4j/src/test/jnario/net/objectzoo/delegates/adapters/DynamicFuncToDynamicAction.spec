package net.objectzoo.delegates.adapters

import net.objectzoo.delegates.DynamicFunc
import static org.mockito.Mockito.*
import static extension org.jnario.lib.Should.*

describe DynamicFuncToDynamicAction
{
	fact "invokes DynamicFunc with 0 argument"
	{
		val mockFunc = mock(typeof(DynamicFunc))
		val sut = new DynamicFuncToDynamicAction(mockFunc)
	
		sut.dynamicInvoke()
	
		verify(mockFunc).dynamicInvoke()
	}
	
	fact "invokes DynamicFunc with 1 argument"
	{
		val mockFunc = mock(typeof(DynamicFunc))
		val sut = new DynamicFuncToDynamicAction(mockFunc)
		val argument = new Object
	
		sut.dynamicInvoke(argument)
	
		verify(mockFunc).dynamicInvoke(argument)
	}
	
	fact "invokes DynamicFunc with 4 argument"
	{
		val mockFunc = mock(typeof(DynamicFunc))
		val sut = new DynamicFuncToDynamicAction(mockFunc)
		val argument1 = new Object
		val argument2 = new Object
		val argument3 = new Object
		val argument4 = new Object
	
		sut.dynamicInvoke(argument1, argument2, argument3, argument4)
	
		verify(mockFunc).dynamicInvoke(argument1, argument2, argument3, argument4)
	}
}