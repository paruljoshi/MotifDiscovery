//package motifidentification;

import java.util.*;
import java.io.*;

public class FitnessCalculationMine {
	public static void main(String[] args) {
		ArrayList<String> motifs = new ArrayList<String>();
			
			int count = 0;
			
			int motiflength = motifs.get(0).length();
			int cA[] = new int[motiflength];
			int cT[] = new int[motiflength];
			int cG[] = new int[motiflength];
			int cC[] = new int[motiflength];

			for (int i = 0; i < motiflength; i++) {
				for (int j = 0; j < count; j++) {
					if (motifs.get(j).charAt(i) == 'A') {
						cA[i]++;
					} else if (motifs.get(j).charAt(i) == 'T') {
						cT[i]++;
					} else if (motifs.get(j).charAt(i) == 'G') {
						cG[i]++;
					} else if (motifs.get(j).charAt(i) == 'C') {
						cC[i]++;
					}
				}
			}
			
			//Printing the Count Matrix for finding the Consensus
			System.out.print("    ");
			for(int i=0;i<motiflength;i++) {
				System.out.print((i+1) + " ");
			}
			System.out.print("\nA : ");
			for(int i=0;i<motiflength;i++) {
				System.out.print(cA[i] + " ");
			}
			System.out.print("\nT : ");
			for(int i=0;i<motiflength;i++) {
				System.out.print(cT[i] + " ");
			}
			System.out.print("\nG : ");
			for(int i=0;i<motiflength;i++) {
				System.out.print(cG[i] + " ");
			}
			System.out.print("\nC : ");
			for(int i=0;i<motiflength;i++) {
				System.out.print(cC[i] + " ");
			}

			System.out.println("\n\n");
			
			// Printing the Probability Matrix for finding consensus
			System.out.print("    ");
			for(int i=0;i<motiflength;i++) {
				System.out.print("  " + (i+1) + " ");
			}
			System.out.print("\nA : ");
			for(int i=0;i<motiflength;i++) {
				System.out.print((cA[i]/(double)count) + " ");
			}
			System.out.print("\nT : ");
			for(int i=0;i<motiflength;i++) {
				System.out.print((cT[i]/(double)count) + " ");
			}
			System.out.print("\nG : ");
			for(int i=0;i<motiflength;i++) {
				System.out.print((cG[i]/(double)count) + " ");
			}
			System.out.print("\nC : ");
			for(int i=0;i<motiflength;i++) {
				System.out.print((cC[i]/(double)count) + " ");
			}
		}
	}
}
