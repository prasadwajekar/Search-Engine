//import java.io.IOException;
//import java.util.Scanner;

public class InvertedPageIndex {

	MyHashTable invertedIndex;

	public InvertedPageIndex() {
		// TODO Auto-generated constructor stub
		invertedIndex=new MyHashTable(800);
	}

	public void addPage(PageEntry p) throws LinkedListOutofBoundsException
	{
		Node<WordEntry> it=p.pgIndex.WordEntries.head;

		while(it.getData()!=null)
		{
			invertedIndex.addPositionsForWord(it.getData());
			it=it.next;
		}
	}

	public MySet<PageEntry> getPagesWhichContainWord(String str)
	{
		WordEntry we=invertedIndex.get(str);
		if(we==null)
		{
			System.out.println("WordNotFound");
			return null;
		}

		MySet<PageEntry> ret=new MySet<PageEntry>();
		MyLinkedList<Position> pos=we.getAllPositionsForThisWord();
		Node<Position> it=pos.head;
		while(it.getData()!=null)
		{
			ret.Insert(it.getData().getPageEntry());
			it=it.next;
		}

		return ret;
	}

}
