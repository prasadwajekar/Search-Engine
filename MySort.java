import java.util.ArrayList;
import java.util.Collections;

public class MySort {
	
	public ArrayList<SearchResult> sortThisList(MySet<SearchResult> listOfSortableEntries) throws LinkedListOutofBoundsException
	{
		ArrayList<SearchResult> ret=new ArrayList<SearchResult>();
		for(int i=0;i<listOfSortableEntries.GetSize();i++)
		{
			if(ret.contains(listOfSortableEntries.getIthElement(i)))
			{
				continue;
			}
			ret.add(listOfSortableEntries.getIthElement(i));
		}
		
		Collections.sort(ret);
		return ret;
	}

}
