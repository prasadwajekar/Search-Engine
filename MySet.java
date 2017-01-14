
public class MySet<T> {

	private MyLinkedList<T> l;

	public MySet() {
		// TODO Auto-generated constructor stub
		l=new MyLinkedList<T>();
	}

	public boolean IsEmpty()
	{
		if(l.getSize()==0)
			return true;

		return false;
	}

	public int GetSize()
	{
		return l.getSize();
	}

	public void Insert(Object o)
	{
		l.add((T)o);
	}

	public T getElement(T a) throws LinkedListOutofBoundsException
	{
		int i=0;
		while(i<l.getSize())
		{
			if(l.get(i).equals(a))
				return l.get(i).getData();
			i++;
		}

		return null; 
	}

	public T getIthElement(int i) throws LinkedListOutofBoundsException
	{
		return this.l.get(i).getData();
	}

	public void Delete(Object o) throws LinkedListOutofBoundsException, EmptySetException
	{
		int i=0;
		while(i<l.getSize())
		{
			if(l.get(i).getData().equals(o))
			{l.remove(l.get(i).getData());
			return;}
			i++;
		}

		throw new EmptySetException();

	}

	public boolean IsMember(T data) throws LinkedListOutofBoundsException
	{
		Node<T> it=this.getHead();
		while(it.next!=null)
		{
			if(it.getData().equals(data))
			{
				return true;
			}
			it=it.next;
		}

		return false;
	}

	public Node<T> getHead()
	{
		return l.head;
	}
	public MySet<T> Intersection(MySet<T> set) throws LinkedListOutofBoundsException
	{
		Node<T> it=this.getHead();
		MyLinkedList<T> inter=new MyLinkedList<T>();
		while(it.next!=null)
		{
			if(this.IsMember(it.getData()) && set.IsMember(it.getData()))
			{
				inter.add(it.getData());
				it=it.next;
			}
			else
			{it=it.next;}
		}
		
		this.l=new MyLinkedList<T>();
		it=inter.head;
		while(it.next!=null)
		{
			this.l.add(it.getData());
			it=it.next;
		}
		
		return this;

	}

	public MySet<T> Union(MySet<T> set) throws LinkedListOutofBoundsException, EmptySetException
	{
		Node<T> it=set.getHead();
		
		while(it.next!=null)
		{
			if(!this.IsMember(it.getData()))
			{
				this.l.add(it.getData());
				it=it.next;
			}
			else
			{
				it=it.next;
			}
		}
		
		
		
		return this;
	}

}
