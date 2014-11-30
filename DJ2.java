import java.util.*;
import java.util.Random;

public class DJ2 {
public static void main(String args[]){
Scanner sc=new Scanner(System.in);
System.out.print("Enter the no. of populations: ");
pop_size=sc.nextInt();
System.out.print("Enter the no. of nodes: ");
nodes=sc.nextInt();
Genes();
}
static String possgenes1;
static char possgenes[],pop[][],mut[],fit[],fully_fit[][],new_pop1[][];
static int pop_size,nodes,fully_fit_val[],rows=0,rows1[];
static char snode,dnode;
static int graph[][] = {{ 0, 41, 0, 15,28, 60 ,0},
						{ 28, 10, 51, 0, 32,5 ,20},
						{ 20, 28, 0, 50,10, 0 ,40},
						{ 45, 0, 28, 0,10, 38 , 20},
						{ 10, 0, 32, 0,0,30 ,16},
						{ 5, 29, 20, 20, 21,0 , 5},
						{ 12, 8 , 20,36, 0,22,0}};
	
static void Genes(){				// to generate possible nodes
Scanner sc=new Scanner(System.in);
possgenes=new char[nodes];
System.out.print("Enter the source node: ");
snode=(sc.nextLine()).charAt(0);
System.out.print("Enter the destination node: ");
dnode=(sc.nextLine()).charAt(0);
System.out.print("Nodes:");
for(int i=97,j=0;i<97+nodes;i++,j++){
	possgenes[j]=(char)i;
	}
	for(int k=0;k<nodes;k++)
		{
			System.out.print(possgenes[k]);
		}
		possgenes1=String.copyValueOf(possgenes);
	System.out.println();	
		gen_pop();
}
static void gen_pop(){				// to generate random popualtion
pop=new char[pop_size][nodes];
System.out.println("Population:");
for(int i=0;i<pop_size;i++){
	for(int j=0;j<nodes-2;j++){
	Random random=new Random();
	int r=random.nextInt(nodes);
	String str=String.valueOf(possgenes[r]);
	pop[i][j]=possgenes[r];
	if(pop[i][j]==snode || pop[i][j]==dnode){
	pop[i][j]='0';
	System.out.print(pop[i][j]);
	}
	else{
	System.out.print(pop[i][j]);	
	continue;	
	}
}
System.out.println();
}
mut_Or_fit();
}

static void mut_Or_fit(){								// to check repetitive string nd send  for mutation nd calculate fitness 
mut=new char[nodes-2];
fit=new char[nodes-2];
for(int i=0;i<pop_size;i++){
	for(int j=0;j<nodes-2;j++){
					if((pop[i][j]=='0')&(j!=(nodes-3)))
					continue;
				if(pop[i][j]==pop[i][j+1]){
					System.out.println();
					System.out.print("mutate:");
					for(int p=0,k=0;p<nodes-2;p++,k++){
					mut[k]=pop[i][p];
					System.out.print(mut[k]);
					}
				break;
				}
				else{
				if(j==(nodes-3)){
					System.out.println();
					System.out.print("Fitness");
					for(int p=0,m=0;p<nodes-2;p++,m++){
					fit[m]=pop[i][p];
					System.out.print(fit[m]);
					}
					fitness();
				}
				continue;
				}
	}
}
crossover();
}
static void fitness()				//to reject 0 fitness genes nd send others fr crossover
{
char temp_fit[];
int fit_val=0,check=0,temp=0;
temp_fit=new char[nodes-2];
fully_fit=new char[pop_size][nodes-2];
fully_fit_val=new int[pop_size];
rows1=new int[nodes-2];
int src=possgenes1.indexOf(snode);
int dest=possgenes1.indexOf(dnode);
for(int m=0;m<nodes-2;m++){
	if(fit[m]=='0')
		continue;
	else{
		temp_fit[temp]=fit[m];
		temp++;
	}
}
System.out.println("temp:"+temp);
for(int m1=0;m1<temp;m1++){
	if(m1==0){
	check=graph[src][possgenes1.indexOf(temp_fit[m1])];
	System.out.println("graph["+src+"]["+possgenes1.indexOf(temp_fit[m1])+"]="+check);
	if(check!=0){
		fit_val+=check;
	}
	else
	{
		System.out.print("REJECTED");		
		break;
	}
	}
	if((m1>0)&(m1!=(temp-1))){
	int mid=possgenes1.indexOf(temp_fit[m1]);
	check=graph[possgenes1.indexOf(temp_fit[m1-1])][possgenes1.indexOf(temp_fit[m1])];
	System.out.println("graph["+possgenes1.indexOf(temp_fit[m1-1])+"]["+possgenes1.indexOf(temp_fit[m1])+"]="+check);
	if(check!=0){
		fit_val+=check;
		continue;	
	}
	else
	{	
		System.out.print("REJECTED");
			break;
	}
	}
	if(m1==(temp-1)){
	check=graph[possgenes1.indexOf(temp_fit[m1])][dest];
	System.out.println("graph["+possgenes1.indexOf(temp_fit[m1])+"["+dest+"]="+check);
	if(check!=0){
		fit_val+=check;
		System.out.println("Fitness val:" +fit_val);
		rows1[0]+=(rows++);
		for(int cols=0,t=0;cols<nodes-2;cols++,t++){
		fully_fit[rows1[0]][cols]=fit[t];
	}
	fully_fit_val[rows1[0]]=fit_val;
	System.out.println("Rows"+rows);
	System.out.print(rows1[0]);
	}
	else
	{	System.out.print("REJECTED");
	break;
	}}
	continue;
}
}

static void crossover(){						//if 1 gene den mutate else crossover	
	char parent1[],parent2[],child1[],child2[];
	int temp=Integer.MAX_VALUE,min=0; 
	new_pop1=new char[pop_size][nodes-2];
	parent1=new char[nodes-2];parent2=new char[nodes-2];
	child1=new char[nodes-2];child2=new char[nodes-2];
	System.out.print("rows"+rows);
	System.out.print("rows again"+rows1[0]);
	if((rows)==1){
		System.out.println("mutate:");
		for(int i=0;i<nodes-2;i++)
		new_pop1[0][nodes-2-1]=fully_fit[rows-1][i];	
	}
	else{
		for(int j=0;j<rows;j++){
				if(temp>fully_fit_val[j]){	
					temp=fully_fit_val[j];
					min=j;
				System.out.print("min="+min);
				}
			}
		for(int i=0;i<nodes-2;i++)
		{	new_pop1[0][nodes-2-1]=fully_fit[min][i];
			System.out.print(new_pop1[0][nodes-2-1]);
		}
	Random random=new Random();
	int p1=random.nextInt(rows);
	for(int num=0;num<(nodes-2);num++)
	parent1[num]=fully_fit[rows][num];
	int p2=random.nextInt(rows);
	for(int num=0;num<(nodes-2);num++)
	parent2[num]=fully_fit[rows][num];
	int c_site=random.nextInt(nodes-2);
	for(int first=0;first<(nodes-2);first++){
		if(first!=c_site)
		child1[first]=parent1[first];
	else
		child2[first]=parent1[first];
	}
	for(int last=0;last<(nodes-2);last++){
		if(last!=c_site)
			child2[last]=parent2[last];
			else
			child1[last]=parent2[last];
	}
	for(int i=0;i<nodes-2;i++){
			new_pop1[1][nodes-2]=child1[i];
			new_pop1[2][nodes-2]=child2[i];

	}
}
}
}