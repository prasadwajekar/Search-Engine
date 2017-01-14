
public class AVLNode<T> {
	
	private T data;
	AVLNode<T> LeftChild;
	AVLNode<T> RightChild;
	AVLNode<T> Parent;
	private int key;
	int height;
	
	public AVLNode(int key,T data)
	{
		this.data=data;
		this.key=key;
		LeftChild=new AVLNode<T>();
		RightChild=new AVLNode<T>();
		LeftChild.setParent(this);
		RightChild.setParent(this);
		height=0;
	}
	
	public AVLNode()
	{
		height=-1;
		LeftChild=null;
		RightChild=null;
	}
	
	public int Key()
	{
		return key;
	}
	public T getData()
	{
		return this.data;
	}
	
	public void setKey(int key)
	{
		this.key=key;
	}
	public void setParent(AVLNode<T> parent)
	{
		this.Parent=parent;
	}
	
	public boolean hasLeft()
	{
		return LeftChild.height!=-1;
	}
	public boolean hasRight()
	{
		return RightChild.height!=-1;
	}
	

}
