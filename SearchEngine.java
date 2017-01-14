import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import sun.org.mozilla.javascript.ast.ArrayLiteral;

public class SearchEngine{

	InvertedPageIndex inv;
	public SearchEngine() {
		inv=new InvertedPageIndex();
	}

	public void addPage(String filename) throws LinkedListOutofBoundsException, IOException, PositionNotFoundException
	{
		PageEntry p=new PageEntry(filename);

		if(p.pageData==null)
		{
			throw new IOException();
		}
		inv.addPage(p);
	}

	public PageEntry[] findPagesWhichContainWord(String word) throws LinkedListOutofBoundsException,WordNotFoundException
	{

		if(word==null)
		{
			throw new WordNotFoundException();
		}
		MySet<PageEntry> ret=inv.getPagesWhichContainWord(word);
		if(ret==null)
		{
			return null;
		}
		PageEntry list[]=new PageEntry[ret.GetSize()];
		int i=0;
		int listC=0;
		while(i<ret.GetSize())
		{
			PageEntry temp=ret.getIthElement(i);

			int count;
			for(count=0;count<listC;count++)
				if(list[count].pageName.equals(temp.pageName))
					break;
			if(count!=listC)
			{
				i++;
				continue;
			}
			list[listC]=temp;
			i++;
			listC++;
		}

		for(int j=0;j<listC;j++)
		{
			for(int k=j+1;k<listC;k++)
			{
				if(list[j].getPageIndex().getScore(word)<list[k].getPageIndex().getScore(word))
				{
					PageEntry temp2=list[k];
					list[k]=list[j];
					list[j]=temp2;
				}
			}
		}




		return list;
	}

	public void positionsWhereWordIsInWebpage(String word,String pageName) throws LinkedListOutofBoundsException, IOException,WordNotFoundException, PositionNotFoundException
	{
		if((new PageEntry(pageName)).pageData==null)
		{
			throw new IOException();
		}

		String word2=filter(word);
		if(word2==null)
		{
			throw new WordNotFoundException();
		}
		WordEntry we=inv.invertedIndex.get(word2);
		if(we==null)
		{
			System.out.println("Word "+word+"/"+word2+" not found in "+pageName);
			return;
		}

		Node<Position> it=we.getAllPositionsForThisWord().head;
		System.out.print("Position(s) of "+word+"/"+word2+" in "+pageName+": ");
		int count=0;
		while(it.getData()!=null)
		{
			if(it.getData().getPageEntry().pageName.equals(pageName))
			{
				System.out.print(it.getData().getWordIndex()+",");
				count++;
			}
			it=it.next;
		}
		if(count==0)
		{
			System.out.println("FileNotAdded");
		}
		else
		System.out.println();
	}

	public String filter(String str)
	{
		String connectors[]={"a","an","the","they","these","this","for","is","are","was","of","or","and","does","will","whose"};
		boolean connectorFound=false;
		for(int c=0;c<connectors.length;c++)
		{
			if(str.equals(connectors[c]))
			{	
				connectorFound=true;
				break;
			}
		}
		if(connectorFound)
		{
			return null;
		}	
		str = str.replaceAll("[\\-\\:\\^\\,\\.\\;\\'\\?\\!\\#\\<\\>\\[\\]\\=\\(\\)\\{\\}]", "");
		str = str.replace('"', ' ');
		str = str.trim();
		str = str.toLowerCase();
		if(str.endsWith("s")) 
			str = str.substring(0, str.length() - 1);

		return str;
	}

	public void andOrQueryHandler(String arr[],int flag) throws LinkedListOutofBoundsException, EmptySetException,WordNotFoundException
	{
		MySet<PageEntry> array[]=new MySet[arr.length-1];
		
		for(int i=1;i<arr.length;i++)
		{
			PageEntry a[]=findPagesWhichContainWord(arr[i]);
			if(a==null)
			{
				throw new WordNotFoundException();
			}
			MySet<PageEntry> temp=new MySet<PageEntry>();
			for(int j=0;j<a.length && a[j]!=null;j++)
			{
				temp.Insert(a[j]);
			}
			array[i-1]=temp;
		}

		MySet<PageEntry> set=new MySet<PageEntry>();
		for(int c=0;c<array[0].GetSize();c++)
		{
			set.Insert(array[0].getIthElement(c));
		}
		for(int i=1;i<array.length;i++)
		{
			if(flag==1)
				set.Intersection(array[i]);
			else if(flag==0)
				set.Union(array[i]);
		}

		MySet<SearchResult> ret=new MySet<SearchResult>();
		for(int i=0;i<set.GetSize();i++)
		{
			double score=0;
			PageIndex pg=set.getIthElement(i).getPageIndex();
			for(int j=1;j<arr.length;j++)
			{
				score+=pg.getScore(arr[j]);
			}
			SearchResult temp=new SearchResult(set.getIthElement(i), score);
			ret.Insert(temp);
		}

		MySort s=new MySort();
		ArrayList<SearchResult> r=s.sortThisList(ret);

		for(int i=0;i<r.size();i++)
		{
			System.out.print(r.get(i).getPageEntry().pageName+",");
		}
		System.out.println();
	}

	public void pagesWhichContainPhrase(String arr[]) throws LinkedListOutofBoundsException, WordNotFoundException
	{
		MySet<PageEntry> array[]=new MySet[arr.length-1];
		
		for(int i=1;i<arr.length;i++)
		{
			PageEntry a[]=findPagesWhichContainWord(arr[i]);
			MySet<PageEntry> temp=new MySet<PageEntry>();
			if(a==null)
			{
				throw new WordNotFoundException();
			}
			for(int j=0;j<a.length && a[j]!=null;j++)
			{
				temp.Insert(a[j]);
			}
			array[i-1]=temp;
		}

		MySet<PageEntry> set=new MySet<PageEntry>();
		for(int c=0;c<array[0].GetSize();c++)
		{
			set.Insert(array[0].getIthElement(c));
		}
		for(int i=1;i<array.length;i++)
		{
			set.Intersection(array[i]);
		}
		if(set.GetSize()==0)
		{
			System.out.println("No match found");
			return;
		}
		
		MySet<SearchResult> ret=new MySet<SearchResult>();
		Node<PageEntry> it=set.getHead();
		while(it.getData()!=null)
		{
			double score=it.getData().getRelevanceOfPage(arr);
			if(score!=0)
			{
				SearchResult s=new SearchResult(it.getData(), score);
				ret.Insert(s);
			}
			it=it.next;
		}
		
		if(ret.GetSize()==0)
		{
			System.out.println("No match found");
			return;
		}
		
		MySort srt=new MySort();
		ArrayList<SearchResult> list=srt.sortThisList(ret);

		for(int i=0;i<list.size();i++)
		{
			System.out.print(list.get(i).getPageEntry().pageName+",");
		}
		System.out.println();
	}

	public void performAction(String actionMessage){
		String actions[]=actionMessage.split(" ");
		try{
			if(actions[0].equals("addPage"))
			{
				addPage(actions[1]);
			}
			else if(actions[0].equals("queryFindPagesWhichContainWord"))
			{
				PageEntry list[]=findPagesWhichContainWord(filter(actions[1]));
				if(list==null)
				{
					return;
				}
				System.out.println("Pages which contain '"+actions[1]+"' :");
				for(int i=0;i<list.length && list[i]!=null ;i++)
				{
					System.out.print(list[i].pageName+",");
				}
				System.out.println();
			}
			else if(actions[0].equals("queryFindPositionsOfWordInAPage"))
			{
				positionsWhereWordIsInWebpage(actions[1], actions[2]);
			}
			else if(actions[0].equals("queryFindPagesWhichContainAllWords"))
			{
				System.out.print("Pages which contain all of these '");
				for(int i=1;i<actions.length;i++)
				{
					System.out.print(actions[i]+" ");
				}
				System.out.print("' :");
				andOrQueryHandler(actions,1);
			}
			else if(actions[0].equals("queryFindPagesWhichContainAnyOfTheseWords"))
			{
				System.out.print("Pages which contain any of these '");
				for(int i=1;i<actions.length;i++)
				{
					System.out.print(actions[i]+" ");
				}
				System.out.print("' :");
				andOrQueryHandler(actions, 0);
			}
			else if(actions[0].equals("queryFindPagesWhichContainPhrase"))
			{
				System.out.print("Pages which contain phrase '");
				for(int i=1;i<actions.length;i++)
				{
					System.out.print(actions[i]+" ");
				}
				System.out.print("' :");
				pagesWhichContainPhrase(actions);
			}
			else
			{
				System.out.println("Invalid Input");
			}
		}
		catch(LinkedListOutofBoundsException e)
		{
			e.printMessage();
		}
		catch(IOException e)
		{
			System.out.println("IOException");
		}
		catch(WordNotFoundException e)
		{
			e.printMessage();
		}
		catch(EmptySetException e)
		{
			e.printMessage();
		}
		catch(PositionNotFoundException e)
		{
			e.printMessage();
		}
	}
}
