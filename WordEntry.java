
public class WordEntry {
	
	private String ofThisWord;
	private MyLinkedList<Position> positions;
	
	public WordEntry(String word) {
		// TODO Auto-generated constructor stub
		this.ofThisWord=word;
		positions= new MyLinkedList<Position>();
	}
	
	public WordEntry(String word,MyLinkedList<Position> l)
	{
		this.ofThisWord=word;
		this.positions=l;
	}
	
	public void addPosition(Position position)
	{
		positions.add(position);
	}
	
	public void addPositions(MyLinkedList<Position> positions) throws LinkedListOutofBoundsException
	{
		//adding an entire list of new positions to be added
		Node<Position> tailOfToBeAdded=positions.get(positions.getSize()-1);
		tailOfToBeAdded.setNext(this.positions.head);
		this.positions.head=positions.head;
	}
	
	public MyLinkedList<Position> getAllPositionsForThisWord()
	{
		return this.positions;
	}
	
	public String getWord()
	{
		return this.ofThisWord;
	}
	
	
	
	

}
