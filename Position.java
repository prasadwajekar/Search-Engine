
public class Position {
	
	//Tuple of PageEntry p and WordIndex in page p
	private PageEntry p;
	private int WordIndex;
	public Position(PageEntry p,int wordIndex) {
		// TODO Auto-generated constructor stub
		this.p=p;
		this.WordIndex=wordIndex;
	}
	
	public PageEntry getPageEntry()
	{
		return p;
	}
	
	public int getWordIndex()
	{
		return WordIndex;
	}
	
	public void setWordIndex(int a)
	{
		this.WordIndex=a;
	}
	
	
}
