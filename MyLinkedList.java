
public class MyLinkedList<T> {
	
	Node<T> head;
	private int size;
	
	public MyLinkedList() {
		// TODO Auto-generated constructor stub
		head=new Node<T>();
		size=0;
	}
	public MyLinkedList(Node<T> head)
	{	
		this.head=head;
		size=1;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void add(T data)
	{
		Node<T> node=new Node<T>(data);
		node.setNext(head);
		head=node;
		size++;
	}
	
	public void remove(T node)
	{
		
		if(node.equals(this.head.getData()))
		{
			head=head.next;
			size--;
			return;
		}
		
		
		Node<T> curr=this.head;
		//TODO handle the last deletion
		while(!curr.next.getData().equals(node))
		{
			curr=curr.next;
		}
		
		
		curr.next=curr.next.next;
		size--;
	}
	
	public boolean isEmpty()
	{
		if(size==0)
		{
			return true;
		}
		return false;
	}
	
	public boolean contains(Node<T> node)
	{
		int i=0;
		Node<T> curr=head;
		while(i<size && !curr.equals(node))
		{
			curr=curr.next;
			i++;
		}
		
		if(i==size)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public Node<T> get(int i) throws LinkedListOutofBoundsException 
	{
		Node<T> curr=this.head;
		if(i>=size)
		{
			throw new LinkedListOutofBoundsException();
		}
		
		int count=size-i-1;
		while(count>0)
		{
			curr=curr.next;
			count--;
		}
		
		return curr;
		
	}


}
