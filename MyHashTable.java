public class MyHashTable {

	int actualSize;
	private int maxSizeOfUnique;
	MyLinkedList<WordEntry> list[];


	public MyHashTable(int size){

		list=new MyLinkedList[size];
		actualSize=0;
		this.maxSizeOfUnique=size;

		for(int i=0;i<this.maxSizeOfUnique;i++)
		{
			list[i]=new MyLinkedList<WordEntry>();
		}
	}

	public long hashedValue(String key)
	{
		long hashCode=0;

		String str=(String)key;
		int i=0;
		long factor=1;
		while(i<str.length())
		{
			hashCode+=((int)(str.charAt(i)-'a'))*factor;
			factor*=33;
			i++;
		}

		if(( (31*hashCode+39)%maxSizeOfUnique )<0)
		{
			return -(31*hashCode+39+maxSizeOfUnique)%maxSizeOfUnique;
		}

		return (31*hashCode+39)%maxSizeOfUnique;
	}

	public boolean isEmpty()
	{
		return actualSize==0;
	}

	public int findValue(String key)
	{
		int hash=(int)hashedValue(key);

		//returns the index in the table for the hashed value of the key

		if(list[hash].getSize()==0)
		{
			return -1;
		}

		return hash;
	}

	public WordEntry get(String key)
	{

		if(findValue(key)==-1)
		{
			//the key was not found
			return null;
		}

		int index=findValue(key);
		Node<WordEntry> it=list[index].head;
		//gets the element 

		while(it.getData()!=null && !it.getData().getWord().equals(key)  )
		{
			it=it.next;
		}

		if(it.getData()==null)
		{
			return null;
		}

		else
		{
			return it.getData();
		}

	}

	public void put(WordEntry value)
	{
		int indexToBeAddedAt=(int) hashedValue(value.getWord());
		list[indexToBeAddedAt].add(value);
		actualSize++;
		return;

	}

	public void addPositionsForWord(WordEntry value) throws LinkedListOutofBoundsException
	{
		String phrase[]=value.getWord().split(" ");
		int i=0;
		for (String element : phrase) {
			if(element.equals(" ") || element.equals("")) continue;
			WordEntry entry=get(element);
			if(entry==null)
			{	
				MyLinkedList<Position> l=value.getAllPositionsForThisWord();
				WordEntry toPut=new WordEntry(element);

				Node<Position> it=l.head;
				while(it.getData()!=null)
				{
					toPut.addPosition(new Position(it.getData().getPageEntry(),it.getData().getWordIndex() + i ));
					it=it.next;
				}
				put(toPut);
				i++;

			}
			else
			{
				Node<Position> it=value.getAllPositionsForThisWord().head;
				while(it.getData()!=null)
				{
					entry.addPosition(new Position(it.getData().getPageEntry(), it.getData().getWordIndex() +i));
					it=it.next;
				}
				i++;

			}
		}

		return ;
	}



}
