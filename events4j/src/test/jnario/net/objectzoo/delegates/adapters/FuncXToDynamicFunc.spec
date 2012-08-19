package net.objectzoo.delegates.adapters

import net.objectzoo.delegates.Func
import net.objectzoo.delegates.Func0
import net.objectzoo.delegates.Func2
import net.objectzoo.delegates.Func3
import net.objectzoo.delegates.Func4
import static org.mockito.Mockito.*
import static extension org.jnario.lib.Should.*

describe FuncXToDynamicFunc
{
	fact "invokes Func0"
	{
		val mockFunc = mock(typeof(Func0)) as Func0<?>
		val sut = new FuncXToDynamicFunc(mockFunc)
	
		sut.dynamicInvoke()
	
		verify(mockFunc).invoke()
	}
	
	fact "invokes Func with correct arguments"
	{
		val mockFunc = mock(typeof(Func)) as Func<Object,?>
		val sut = new FuncXToDynamicFunc(mockFunc)
		val argument = new Object
	
		sut.dynamicInvoke(argument)
	
		verify(mockFunc).invoke(argument)
	}
	
	fact "invokes Func2 with correct arguments"
	{
		val mockFunc = mock(typeof(Func2)) as Func2<Object, Object,?>
		val sut = new FuncXToDynamicFunc(mockFunc)
		val argument1 = new Object
		val argument2 = new Object
	
		sut.dynamicInvoke(argument1, argument2)
	
		verify(mockFunc).invoke(argument1, argument2)
	}
	
	fact "invokes Func3 with correct arguments"
	{
		val mockFunc = mock(typeof(Func3)) as Func3<Object, Object, Object,?>
		val sut = new FuncXToDynamicFunc(mockFunc)
		val argument1 = new Object
		val argument2 = new Object
		val argument3 = new Object
	
		sut.dynamicInvoke(argument1, argument2, argument3)
	
		verify(mockFunc).invoke(argument1, argument2, argument3)
	}
	
	fact "invokes Func4 with correct arguments"
	{
		val mockFunc = mock(typeof(Func4)) as Func4<Object, Object, Object, Object,?>
		val sut = new FuncXToDynamicFunc(mockFunc)
		val argument1 = new Object
		val argument2 = new Object
		val argument3 = new Object
		val argument4 = new Object
	
		sut.dynamicInvoke(argument1, argument2, argument3, argument4)
	
		verify(mockFunc).invoke(argument1, argument2, argument3, argument4)
	}
}