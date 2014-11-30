
//import java.io.*;
import java.util.*;
public class DJtry2
{
	public DJtry2()
    {
    	//Console con=System.console();
    	Scanner con = new Scanner(System.in);
    	System.out.print("Enter no. of populations:");
    	no_pop=Integer.parseInt(con.nextLine());
    	System.out.print("Enter no. of nodes:");
    	no_nodes=Integer.parseInt(con.nextLine());
    	System.out.print("Enter the character value of the first node:");
    	fnode=(con.nextLine()).charAt(0);
    	System.out.print("Enter the character value of the last node:");
    	lnode=(con.nextLine()).charAt(0);
    	possGeneGen();
		gen=evolve();
		fitness=getFitness();
		display();
		//crossover();
		String mut=mutation();
		System.out.println(mut);
    }

	static char possGenes[],pop[][];
	static String gen[],allPossGenes;
	static char fnode,lnode;
	static int no_nodes,no_pop,fitness[][],rank[][];
	final int parentUsePercent = 30;

	static int graph[][] = {

			{ Integer.MAX_VALUE, 41, Integer.MAX_VALUE, 15,
					28, 60 ,Integer.MAX_VALUE},
			{ 28, Integer.MAX_VALUE, 51, Integer.MAX_VALUE, 32,
					5 ,20},
			{ Integer.MAX_VALUE, 28, Integer.MAX_VALUE, 50,
					10, Integer.MAX_VALUE ,40},
			{ 45, Integer.MAX_VALUE, 28, Integer.MAX_VALUE,
					10, 38 , Integer.MAX_VALUE},
			{ 10, Integer.MAX_VALUE, 32, Integer.MAX_VALUE,
					Integer.MAX_VALUE,30 ,16},
			{ Integer.MAX_VALUE, 29, Integer.MAX_VALUE, Integer.MAX_VALUE, 21,
					Integer.MAX_VALUE , 5},
			{ 12, 8 , Integer.MAX_VALUE,36, Integer.MAX_VALUE,
					22,Integer.MAX_VALUE}};

	public static String[] evolve()
	{
		gen=new String[no_pop];
		char tempPossGenes[]=possGenes;
		pop=new char[no_pop][no_nodes-2];
		boolean flag=true;
		char add='-';
		for(int i=0;i<no_pop;i++)
		{
			gen[i]="";
			for(int j=0;j<no_nodes-2;j++)
			{
				boolean b = (int)(Math.random()*10) >= 5;
				if(b)
				{
					int r=random(no_nodes-3);
					add=possGenes[r];
					System.out.println("to be added:"+add);
					pop[i][j]=add;
				}
				else
					pop[i][j]='0';
				gen[i]+=pop[i][j];

			}
			System.out.println("gen"+i+":"+gen[i]);
			gen[i]=getChromWithoutDuplicates(gen[i]);
			System.out.println("gen"+i+":"+gen[i]);
		}
		return gen;
	}

	static int random(int limit)
	{
		int ans=9999;
		while(ans>=limit)
			ans=(int)(Math.random()*10);
		return ans;
	}


	   static protected String getChromWithoutDuplicates(String sChromosome)
    {
        int iPos;
        int iRandomGeneLeftOut;
        String sGene, sGenesLeftOut, sRestOfChrom;

        //first get a string (which acts as a list) of all genes left OUT of this chrom
        sGenesLeftOut = "";
        for (int i = 0; i < possGenes.length; i++)
        {
            sGene = "" + possGenes[i];
            iPos = sChromosome.indexOf(sGene);
            if (iPos < 0) //this gene not found in chromosome
                sGenesLeftOut += sGene;
        }

        if (sGenesLeftOut.length() == 0) //no duplicate genes, so exit
            return (sChromosome);

        StringBuffer sbChromosome = new StringBuffer(sChromosome);
        StringBuffer sbGenesLeftOut = new StringBuffer(sGenesLeftOut);

		System.out.println(sbChromosome);
		System.out.println(sbGenesLeftOut);
		int count=0;
        while(count!=2)
        	{
		        for (int i = 0; i < no_nodes-2; i++)
		        {
		            sGene = "" + sbChromosome.charAt(i);
		            sRestOfChrom = sbChromosome.substring(i + 1);

		            iPos = sRestOfChrom.indexOf(sGene);
		            if (iPos > -1) //gene also found in a later part of the chromosome, it is duplicated!
		            {
		                //assign this duplicate gene a random value from the list of genes left out
		                iRandomGeneLeftOut = random(sbGenesLeftOut.length());
		                sbChromosome.setCharAt(iPos + i + 1, sbGenesLeftOut.charAt(iRandomGeneLeftOut));

		                //now take this "gene left out" out of the list (string) of available genes
		                sbGenesLeftOut.deleteCharAt(iRandomGeneLeftOut);
		            }
		        }
		        count++;
        	}
        return (sbChromosome.toString());
    }

	static void display()
	{
		for(int j=0;j<no_pop;j++)
				System.out.println(gen[j]+"\t"+fitness[j][1]);
	}

	static void possGeneGen()
	{
		char aPGenes[]=new char[no_nodes];
		possGenes=new char[no_nodes-2];
		for(int i=97,j=0;i<97+no_nodes;i++,j++)
		{
			aPGenes[i-97]=(char)i;
			if((int)fnode==i || (int)lnode==i)
			{
				j--;
				continue;
			}
			else
			{
				possGenes[j]=(char)i;
			}

		}
		allPossGenes=String.copyValueOf(aPGenes);
		for(int k=0;k<no_nodes-2;k++)
		{
			System.out.println(possGenes[k]+"\t");
		}
			System.out.println("APG:"+allPossGenes);
	}

	public int[][] getFitness()
	{
		int sum[][]=new int[no_pop][2];
		for(int i=0;i<no_pop;i++)
		{
			sum[i][0]=i;
			int add=0;
			boolean flag=false;
			int start=allPossGenes.indexOf(fnode);
			for(int j=0;j<no_nodes-2;j++)
			{

				if(gen[i].charAt(j)=='0')
					continue;
				else
				{
					add=graph[start][allPossGenes.indexOf(gen[i].charAt(j))];
					if(add!=Integer.MAX_VALUE)
						sum[i][1]+=add;
					else
					{
						sum[i][1]=Integer.MAX_VALUE;
						flag=true;
						break;
					}
					start=allPossGenes.indexOf(gen[i].charAt(j));
				}
			}
			add=graph[start][allPossGenes.indexOf(lnode)];
			if(flag==false)
			{
				if(add!=Integer.MAX_VALUE)
					sum[i][1]+=add;
			}
			System.out.println("gen"+i+" fit:"+sum[i][1]);
		}
		return sum;
	}


	/*public void crossover()
	{
		int temp[]=new int[2];
		int rank[][]=fitness;
		for(int i=0;i<no_pop;i++)
			for(int j=i;j<no_pop;j++)
			{
				if(rank[i][1]>rank[j][1])
				{
					temp=rank[i];
					rank[i]=rank[j];
					rank[j]=temp;
				}
			}
		String parent1=gen[rank[0][0]];
		String parent2=gen[rank[1][0]];
		String child1=parent1;
		String child2=parent2;
		char Gene1='/';
		char Gene2='/';
		for(int j=0;j<no_nodes-2;j++)
			{
				if(j%2==0)
				{
					Gene1 =child1.charAt(j);
					Gene2 =child2.charAt(j);
					if(!(child2.indexOf(Gene1)>-1) && !(child1.indexOf(Gene2)>-1)|| Gene1!='0' || Gene2!='0')
					{
						child1=child1.replace(child1.charAt(j),Gene2);
		                child2=child2.replace(child2.charAt(j),Gene1);
					}
		        }

			}
			System.out.println(parent1+":"+child1+"\t"+parent2+":"+child2);
	}*/
	
	String mutation()
	{
		int temp[]=new int[2];
		int rank[][]=fitness;
		for(int i=0;i<no_pop;i++)
			for(int j=i;j<no_pop;j++)
			{
				if(rank[i][1]>rank[j][1])
				{
					temp=rank[i];
					rank[i]=rank[j];
					rank[j]=temp;
				}
			}

			String p3=gen[rank[3][0]];
			//String p4=gen[rank[4][0]];
			//String p5=gen[rank[5[0]];
			int noto=-1;
			for(int i=0;i<no_nodes;i++)
				if(p3.charAt(i)!='0')
				{
					noto=i;
					break;
				}
			if(noto==-1)
			{
				return p3;
			}
			int mid=0;
			System.out.println(allPossGenes.indexOf(fnode)+"\t"+
								allPossGenes.indexOf(p3.charAt(noto))+"\t"+allPossGenes.indexOf(p3.charAt(p3.length()-1))+"\t"+	allPossGenes.indexOf(fnode));
			if(graph[allPossGenes.indexOf(fnode)][allPossGenes.indexOf(p3.charAt(noto))]!=Integer.MAX_VALUE && 
				graph[allPossGenes.indexOf(p3.charAt(p3.length()-1))][allPossGenes.indexOf(fnode)]!=Integer.MAX_VALUE	)
				{
					
					
						for(int i=0;i<no_nodes-2;i++)
						{
							int j=i;
							if(p3.charAt(i)=='0')
							{
								while(p3.charAt(j++)!='0' && j<no_nodes-2)
								{
								}
									int s=allPossGenes.indexOf(p3.charAt(i-1));
									int e=allPossGenes.indexOf(p3.charAt(j));
									for(mid=0;mid<no_nodes-2;mid++)
									{
										if(graph[s][mid]!=Integer.MAX_VALUE && graph[mid][e]!=Integer.MAX_VALUE)
											break;
									}
								
							}
							p3.replace(p3.charAt(i),allPossGenes.charAt(mid));
							
								
						}
					
				}
				return p3;
	}

	public static void main(String args[])
	{
		DJtry2 dj=new DJtry2();
	}
}