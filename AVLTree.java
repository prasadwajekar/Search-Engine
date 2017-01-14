
public class AVLTree<T> {
	
	AVLNode<T> root;
	int height;

	public AVLTree()
	{
		root=new AVLNode<T>();
	}
	
	public AVLTree(AVLNode<T> t)
	{
		root=t;
	}
	
	public void Insert(T data,int key) throws PositionNotFoundException
	{
		//basecase covered
		if(root.height==-1)
		{
			root=new AVLNode<T>(key,data);
			root.setParent(null);
			return;
		}
		
		//search in BST
		AVLNode<T> x=search(key,this.root);
		if(x==null)
		{
			throw new PositionNotFoundException();
		}
		
		heightupdate(x);
		if(x.Key()>=key)
		{
			x.LeftChild=new AVLNode<T>(key,data);
			x.LeftChild.setParent(x);
		}
		else if(x.Key()<key)
		{
			x.RightChild=new AVLNode<T>(key,data);
			x.RightChild.setParent(x);
		}
		
		AVLNode<T> it=x;
		while( it!=null && ( Math.abs(it.LeftChild.height-it.RightChild.height)<=1))
		{
			it=it.Parent;
		}
		
		if(it==null)
		{
			return;
		}
		
		AVLNode<T> abc[]=abcFromXYZ(it);
		
		AVLNode<T> a=abc[0];
		AVLNode<T> b=abc[1];
		AVLNode<T> c=abc[2];
		
		AVLNode<T> T0=abc[3];
		AVLNode<T> T1=abc[4];
		AVLNode<T> T2=abc[5];
		AVLNode<T> T3=abc[6];
		
		AVLNode<T> z=it;
		AVLNode<T> y=(z.LeftChild.Key()>z.RightChild.Key()?z.LeftChild:z.RightChild);
		x=(y.LeftChild.Key()>y.RightChild.Key()?y.LeftChild:y.RightChild);
		
		AVLNode<T> parentOfZ=z.Parent;
		if(parentOfZ==null)
		{
			this.root=b;
			b.setParent(null);
	        b.LeftChild=a;
			a.setParent(b);
			a.LeftChild=T0;
			T0.setParent(a);
			a.RightChild=T1;
			T1.setParent(a);
			a.height=1+(T0.height>T1.height?T0.height:T1.height);
			
			b.RightChild=c;
			c.setParent(b);
			c.LeftChild=T2;
			T2.setParent(c);
			c.RightChild=T3;
			T3.setParent(c);
			c.height=1+(T2.height>T3.height?T2.height:T3.height);
			
			b.height=1+(a.height>c.height?a.height:c.height);
			
			return;
		}
		
		
		boolean flag=(parentOfZ.Key()>z.Key()?false:true);
		if(flag)
		{
			parentOfZ.RightChild=b;
			b.setParent(parentOfZ);
		}
		else
		{
			parentOfZ.LeftChild=b;
			b.setParent(parentOfZ);
		}
		
		b.LeftChild=a;
		a.setParent(b);
		a.LeftChild=T0;
		T0.setParent(a);
		a.RightChild=T1;
		T1.setParent(a);
		a.height=1+(T0.height>T1.height?T0.height:T1.height);
		
		b.RightChild=c;
		c.setParent(b);
		c.LeftChild=T2;
		T2.setParent(c);
		c.RightChild=T3;
		T3.setParent(c);
		c.height=1+(T2.height>T3.height?T2.height:T3.height);
		
		b.height=1+(a.height>c.height?a.height:c.height);
	}
	
	public AVLNode<T>[] abcFromXYZ(AVLNode<T> start)
	{
		AVLNode<T> z=start;
		AVLNode<T> y=(start.LeftChild.Key()>start.RightChild.Key()?start.LeftChild:start.RightChild);
		AVLNode<T> x=(y.LeftChild.Key()>y.RightChild.Key()?y.LeftChild:y.RightChild);
		AVLNode<T>[] ret=new AVLNode[7];
		if(x.Key()<y.Key() && y.Key()<z.Key())
		{
			ret[0]=x;
			ret[1]=y;
			ret[2]=z;
			ret[3]=x.LeftChild;
			ret[4]=x.RightChild;
			ret[5]=y.RightChild;
			ret[6]=z.RightChild;
		}
		else if(x.Key()>y.Key() && y.Key()<z.Key())
		{
			ret[1]=x;
			ret[0]=y;
			ret[2]=z;
			ret[3]=y.LeftChild;
			ret[4]=x.LeftChild;
			ret[5]=x.RightChild;
			ret[6]=z.RightChild;
		}
		else if(z.Key()<y.Key() && y.Key()<x.Key())
		{
			ret[0]=z;
			ret[1]=y;
			ret[2]=x;
			ret[3]=z.LeftChild;
			ret[4]=y.LeftChild;
			ret[5]=x.LeftChild;
			ret[6]=x.RightChild;
		}
		else if(z.Key()<y.Key() && y.Key()>x.Key())
		{
			ret[0]=z;
			ret[2]=y;
			ret[1]=x;
			ret[3]=z.LeftChild;
			ret[4]=x.LeftChild;
			ret[5]=x.RightChild;
			ret[6]=y.RightChild;
		}
			
		return ret;
	}
	
	public void heightupdate(AVLNode<T> x)
	{
		AVLNode<T> curr=x;
		while(curr!=null)
		{
			curr.height=curr.height+1;
			curr=curr.Parent;
		}
	}
	
	public boolean isExternal(AVLNode<T> v)
	{
		if(v.height==-1)
		{
			return true;
		}
		
		return false;
	}
	
	public AVLNode<T> search(int key,AVLNode<T> curr)
	{
		
		if(isExternal(curr))
		{
			return curr;
		}
		
		
		if(key<curr.Key())
		{
			if(curr.LeftChild.height==-1)
			{
				return curr;
			}
			return search(key,curr.LeftChild);
		}
		else if(key>curr.Key())
		{
			if(curr.RightChild.height==-1)
			{
				return curr;
			}
			return search(key, curr.RightChild);
		}
		else
		{
			return curr;
		}
	}
	
	public AVLNode<String> findNodeWithKey(int key,AVLNode<String> curr)
	{
		if(curr.height==-1)
		{
			return null;
		}
		if(key<curr.Key())
		{
			return findNodeWithKey(key, curr.LeftChild);
		}
		else if(key>curr.Key())
		{
			return findNodeWithKey(key, curr.RightChild);
		}
		else
		{
			return curr;
		}
	}
	
	public void printTree(AVLTree<T> t)
	{
		AVLNode<T> curr=t.root;
		if(t.root.height==-1)
		{
			return;
		}
		
		
		
		System.out.print(curr.Key()+":");
		if(curr.hasLeft())
		{
			System.out.print(curr.LeftChild.Key()+",");
		}
		
		if(curr.hasRight())
		{
			System.out.println(curr.RightChild.Key());
		}
		else
		{
			System.out.println();
		}
		
		if(curr.hasLeft())
		printTree(new AVLTree<T>(curr.LeftChild));
		
		if(curr.hasRight())
		printTree(new AVLTree<T>(curr.RightChild));
	}
	
//	public static void main(String args[]) throws PositionNotFoundException
//	{
//		AVLTree<String> t=new AVLTree<String>();
//		t.Insert("",1);
//		t.Insert("",2);
//		t.Insert("",5);
//		t.Insert("",4);
//		t.Insert("",3);
//		t.Insert("",0);
//		t.printTree(t);
//	}
}
