import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import net.objectzoo.delegates.Func;

public class Linq
{
	private static abstract class NoRmoveIterator<R> implements Iterator<R>
	{
		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
	
	public static <T, R> Iterable<R> select(final Iterable<T> in,
											final Func<? super T, ? extends R> selector)
	{
		return new Iterable<R>()
		{
			@Override
			public Iterator<R> iterator()
			{
				return new SelectIterator<T, R>(in.iterator(), selector);
			}
		};
	}
	
	private static class SelectIterator<T, R> extends NoRmoveIterator<R> implements Iterator<R>
	{
		final Iterator<T> in;
		final Func<? super T, ? extends R> selector;
		
		SelectIterator(Iterator<T> in, Func<? super T, ? extends R> selector)
		{
			this.in = in;
			this.selector = selector;
		}
		
		@Override
		public boolean hasNext()
		{
			return in.hasNext();
		}
		
		@Override
		public R next()
		{
			return selector.invoke(in.next());
		}
	}
	
	public static <T> Iterable<T> where(final Iterable<T> in,
										final Func<? super T, Boolean> selector)
	{
		return new Iterable<T>()
		{
			@Override
			public Iterator<T> iterator()
			{
				return new WhereIterator<T>(in.iterator(), selector);
			}
		};
	}
	
	private static class WhereIterator<T> extends NoRmoveIterator<T> implements Iterator<T>
	{
		final Iterator<T> in;
		final Func<? super T, Boolean> selector;
		
		private T next = null;
		
		WhereIterator(Iterator<T> in, Func<? super T, Boolean> selector)
		{
			this.in = in;
			this.selector = selector;
		}
		
		@Override
		public boolean hasNext()
		{
			findNext();
			return next != null;
		}
		
		@Override
		public T next()
		{
			findNext();
			if (next != null)
			{
				try
				{
					return next;
				}
				finally
				{
					next = null;
				}
			}
			else
			{
				throw new NoSuchElementException();
			}
		}
		
		private void findNext()
		{
			while (next == null && in.hasNext())
			{
				T nextIn = in.next();
				if (selector.invoke(nextIn))
				{
					next = nextIn;
				}
			}
		}
	}
	
	public static <T> Iterable<T> concat(final Iterable<T>... iterables)
	{
		return new Iterable<T>()
		{
			@Override
			public Iterator<T> iterator()
			{
				return new ConcatIterator<T>(select(fromArray(iterables),
					new Func<Iterable<T>, Iterator<T>>()
					{
						@Override
						public Iterator<T> invoke(Iterable<T> iterable)
						{
							return iterable.iterator();
						}
					}));
			}
		};
	}
	
	private static class ConcatIterator<T> extends NoRmoveIterator<T> implements Iterator<T>
	{
		final Iterator<Iterator<T>> iterators;
		Iterator<T> current;
		
		ConcatIterator(Iterable<Iterator<T>> iterators)
		{
			this.iterators = iterators.iterator();
		}
		
		@Override
		public boolean hasNext()
		{
			if (current != null && !current.hasNext()) current = null;
			if (current == null && iterators.hasNext()) current = iterators.next();
			if (current == null) return false;
			if (current.hasNext()) return true;
			return hasNext();
		}
		
		@Override
		public T next()
		{
			if (!hasNext()) throw new NoSuchElementException();
			return current.next();
		}
	}
	
	private static <T> Iterable<T> fromArray(final T[] array)
	{
		return new Iterable<T>()
		{
			@Override
			public Iterator<T> iterator()
			{
				return new NoRmoveIterator<T>()
				{
					private int index = 0;
					
					@Override
					public boolean hasNext()
					{
						return index < array.length;
					}
					
					@Override
					public T next()
					{
						if (!hasNext()) throw new NoSuchElementException();
						return array[index++];
					}
				};
			}
		};
	}
	
	public static <T> List<T> toList(Iterable<T> in)
	{
		List<T> list = new LinkedList<T>();
		for (T item : in)
		{
			list.add(item);
		}
		return list;
	}
	
	public static String concat(Iterable<String> in)
	{
		StringBuilder sb = new StringBuilder();
		for (String s : in)
		{
			sb.append(s);
		}
		return sb.toString();
	}
	
	public static void main(String... args)
	{
		int n = 100;
		List<Integer> numbers = new ArrayList<Integer>(n);
		for (int i = 1; i <= n; i++)
			numbers.add(i);
		
		Iterable<Integer> evenNumbers = where(numbers, new Func<Integer, Boolean>()
		{
			@Override
			public Boolean invoke(Integer number)
			{
				return number % 2 == 0;
			}
		});
		
		Iterable<String> formattedNumbers = select(evenNumbers, new Func<Integer, String>()
		{
			@Override
			public String invoke(Integer number)
			{
				return number.toString() + " o ";
			}
		});
		
		@SuppressWarnings("unchecked")
		Iterable<String> zweimalFormattedNumbers = concat(formattedNumbers, formattedNumbers);
		
		String output = concat(zweimalFormattedNumbers);
		
		System.out.println(output);
	}
}
