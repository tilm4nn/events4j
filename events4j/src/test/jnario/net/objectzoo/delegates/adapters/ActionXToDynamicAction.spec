package net.objectzoo.delegates.adapters

import net.objectzoo.delegates.Action
import net.objectzoo.delegates.Action0
import net.objectzoo.delegates.Action2
import net.objectzoo.delegates.Action3
import net.objectzoo.delegates.Action4
import static org.mockito.Mockito.*
import static extension org.jnario.lib.Should.*

describe ActionXToDynamicAction
{
	fact "invokes Action0"
	{
		val mockAction = mock(typeof(Action0)) as Action0
		val sut = new ActionXToDynamicAction(mockAction)
	
		sut.dynamicInvoke()
	
		verify(mockAction).invoke()
	}
	
	fact "invokes Action with correct arguments"
	{
		val mockAction = mock(typeof(Action)) as Action<Object>
		val sut = new ActionXToDynamicAction(mockAction)
		val argument = new Object
	
		sut.dynamicInvoke(argument)
	
		verify(mockAction).invoke(argument)
	}
	
	fact "invokes Action2 with correct arguments"
	{
		val mockAction = mock(typeof(Action2)) as Action2<Object, Object>
		val sut = new ActionXToDynamicAction(mockAction)
		val argument1 = new Object
		val argument2 = new Object
	
		sut.dynamicInvoke(argument1, argument2)
	
		verify(mockAction).invoke(argument1, argument2)
	}
	
	fact "invokes Action3 with correct arguments"
	{
		val mockAction = mock(typeof(Action3)) as Action3<Object, Object, Object>
		val sut = new ActionXToDynamicAction(mockAction)
		val argument1 = new Object
		val argument2 = new Object
		val argument3 = new Object
	
		sut.dynamicInvoke(argument1, argument2, argument3)
	
		verify(mockAction).invoke(argument1, argument2, argument3)
	}
	
	fact "invokes Action4 with correct arguments"
	{
		val mockAction = mock(typeof(Action4)) as Action4<Object, Object, Object, Object>
		val sut = new ActionXToDynamicAction(mockAction)
		val argument1 = new Object
		val argument2 = new Object
		val argument3 = new Object
		val argument4 = new Object
	
		sut.dynamicInvoke(argument1, argument2, argument3, argument4)
	
		verify(mockAction).invoke(argument1, argument2, argument3, argument4)
	}
}