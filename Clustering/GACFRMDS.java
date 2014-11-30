//package motifidentification;

import java.util.*;
import java.io.*;

public class GACFRMDS {

	public static void main(String[] args) {
		ArrayList<String> chromosomes;
		ArrayList<String> geneName;
		ArrayList<String> geneCode;
		String temp;
		int count = 0;

		try {
			FileReader fr = new FileReader("MIG1.txt");
			Scanner in = new Scanner(fr);
			Scanner sc;
			chromosomes = new ArrayList<String>();
			geneName = new ArrayList<String>();
			geneCode = new ArrayList<String>();
			temp = in.next();
			while (in.hasNext()) {
				String chromosome = "";
				geneName.add(temp);
				geneCode.add(in.next());

				while (true) {
					if (!in.hasNext()) {
						break;
					}
					temp = in.next();
					if (temp.charAt(0) == '>') {
						break;
					}
					chromosome += temp;
				}

				if (chromosome != "") {
					chromosomes.add(chromosome);
				}

				count++;

			}

			for (int i = 0; i < count; i++) {
				System.out.println("Gene Name is : " + geneName.get(i));
				System.out.println("Gene Code is : " + geneCode.get(i));
				System.out.println(chromosomes.get(i));
				System.out.println();
			}

			sc = new Scanner(System.in);
			System.out.println("Enter the Motif Length : ");
			int ml = sc.nextInt();
			// int ml = 6;
			System.out.println("Enter No. of Motif's in each Chromosome : ");
			int nm = sc.nextInt();
			// int nm = 4;

			Random r = new Random();
			int randomindex[] = new int[nm];

			System.out.println("Random Position are : ");
			for (int i = 0; i < chromosomes.size(); i++) {
				int range = chromosomes.get(i).length();
				for (int j = 0; j < nm; j++) {
					randomindex[j] = r.nextInt(range - ml);
				}
				System.out.println(geneName.get(i));
				for (int j = 0; j < nm; j++) {
					System.out.println(randomindex[j]
							+ " "
							+ chromosomes.get(i).substring(randomindex[j],
									randomindex[j] + ml));
				}
				System.out.println();
			}

			System.out.println();

			fr.close();
			System.out.println("Number of DataSet Inputted: " + count);
			System.exit(1);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
