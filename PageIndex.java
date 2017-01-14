
public class PageIndex {
	
	MyLinkedList<WordEntry> WordEntries;
	double score;
	public PageIndex() {
		// TODO Auto-generated constructor stub
		this.WordEntries=new MyLinkedList<WordEntry>();
		score=0.00;
	}
	
	
	public void addPositionForWord(String str,Position p) throws LinkedListOutofBoundsException
	{
		int i=0;
		
		while(i<WordEntries.getSize())
		{
			if(WordEntries.get(i).getData().getWord().equals(str))
			{
				WordEntries.get(i).getData().addPosition(p);
				return;
			}
			
			i++;
		}
		
		WordEntry toAdd=new WordEntry(str);
		toAdd.addPosition(p);
		this.WordEntries.add(toAdd);
		
	}

	public MyLinkedList<WordEntry> getWordEntries()
	{
		return this.WordEntries;
	}
	
	public double getScore(String word)
	{	
		if(score!=0.00){return score;}
		
		Node<WordEntry> it=this.WordEntries.head;
		while(it.getData()!=null && !it.getData().getWord().equals(word))
		{
			it=it.next;
		}
		
		if(it.getData()==null)
		{
			return 0;
		}
		else
		{
			WordEntry we=it.getData();
			
			MyLinkedList<Position> temp=we.getAllPositionsForThisWord();
			Node<Position> iter=temp.head;
			double sum=0.0000000;
			
			while(iter.getData()!=null)
			{
				if(iter.getData().getPageEntry().getPageIndex().equals(this))
				{	
					double t=( (iter.getData().getWordIndex() ) * (iter.getData().getWordIndex()) );
					sum += 1.0/t;
				}
				iter=iter.next;
				
			}
			score=sum;
			return sum;
		}
		
		
			
	}

}
