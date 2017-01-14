import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class PageEntry {

	BufferedReader br=null;
	String pageData;
	PageIndex pgIndex;
	String pageName;
	AVLTree<String> avl;

	public PageEntry(String pageName) throws LinkedListOutofBoundsException, IOException,FileNotFoundException, PositionNotFoundException {
		// TODO Auto-generated constructor stub
		try {
			//File fileSource=new File("webpages/"+pageName);
			FileInputStream file=new FileInputStream("webpages/"+pageName);
			this.pageName=pageName;
			pageData=" ";
			Scanner s=new Scanner(file);
			String connectors[]={"a","an","the","they","these","this","for","is","are","was","of","or","and","does","will","whose"};
			int i=1;
			pgIndex=new PageIndex();
			avl=new AVLTree<String>();
			while(s.hasNext())
			{
				String toPut=s.next();


				boolean connectorFound=false;
				for(int c=0;c<connectors.length;c++)
				{

					if(toPut.equals(connectors[c]))
					{	
						connectorFound=true;
						break;
					}
				}
				if(connectorFound)
				{
					i++;
					continue;
				}



				toPut=filter(toPut);

				String split[]=toPut.split(" ");
				int j=0;
				while(j<split.length)
				{

					Position p=new Position(this, i);
					pgIndex.addPositionForWord(split[j], p);
					avl.Insert(split[j], i);
					j++;
					i++;
				}



			}

			s.close();
			file.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("FileNotFound,");
			pageData=null;
			return;
		}

	}

	public PageIndex getPageIndex()
	{
		return this.pgIndex;
	}

	public String filter(String str)
	{

		str = str.replaceAll("[\\-\\:\\^\\,\\.\\;\\'\\?\\!\\#\\<\\>\\[\\]\\=\\(\\)\\{\\}]", " ");
		str = str.replace('"', ' ');
		str = str.trim();
		str = str.toLowerCase();

		if(str.equals("stacks") || str.equals("structures") || str.equals("applications")) 
			str = str.substring(0, str.length() - 1);

		return str;
	}

	public double getRelevanceOfPage(String[] str)
	{		
		double relevance = 0;
		Node<WordEntry> it=this.pgIndex.getWordEntries().head;
		WordEntry we=new WordEntry(str[1]);
		while(it.getData()!=null)
		{
			if(it.getData().getWord().equals(str[1]))
			{
				we=it.getData();
			}
			it=it.next;
		}

		Node<Position> iterate=we.getAllPositionsForThisWord().head;
		while(iterate.getData()!=null)
		{
			if(iterate.getData().getPageEntry().equals(this))
			{
				int key=iterate.getData().getWordIndex();
				double score=(double)key;
				score=1.0/(score*score);

				for(int i=2;i<str.length;i++)
				{
					AVLNode<String> node=this.avl.findNodeWithKey(key+i-1,avl.root);
					if(!node.getData().equals(str[i]))
					{
						score=0;
						break;
					}
				}

				if(score!=0)
					relevance+=score;

			}

			iterate=iterate.next;
		}

		return relevance;
	}
	
}
