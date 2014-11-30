package motifidentification;

import java.util.*;

// Cluster class holding list of motifs in its cluster domain
class Cluster {
	public ArrayList<Integer> position = new ArrayList<Integer>();
	public ArrayList<String> motifs = new ArrayList<String>();
}

public class MakingCluster {
	public static void main(String[] args) {

		MakingCluster mc = new MakingCluster();
		// Two functions are part of this above class hence , it is necessary to
		// make object of this class to call them

		Cluster[] cl;

		int motiflength = 8;
		cl = mc.initCluster(motiflength); // initialization

		int position[] = {}; // position P
		String[] consensus = {}; // hard code the consensus strings or
									// initialise using ur program of motif
									// finding
		Arrays.sort(consensus); // sorting must be done by implementing
								// comparator interface according to fitness
		String bestconsensus = consensus[0];

		// calling the clustering method
		mc.categoriseCluster(cl, position, consensus, bestconsensus);

		// code for printing the motifs in the clusters
		for (int i = 0; i < cl.length; i++) {
			System.out.println(cl[i].motifs);
		}

	}

	// method to initialise and create clusters according to motiflengths
	public Cluster[] initCluster(int motiflength) {
		Cluster[] cl = new Cluster[motiflength];
		for (int i = 0; i < motiflength; i++) {
			cl[i] = new Cluster();
		}
		return cl;
	}

	// categorising the consensus strings according to hamming distance
	public void categoriseCluster(Cluster[] cl, int[] pos, String[] M, String M1) {
		for (int i = 0; i < M.length; i++) {

			// Calculate Hamming distance
			int hdistance = 0;
			for (int j = 0; j < M1.length(); j++) {
				// condition of hamming distance
				if (M[i].charAt(j) != M1.charAt(j)) {
					hdistance++;
				}
			}

			// adding the consensus to cluster arraylist of index hdistance
			cl[hdistance].motifs.add(M[i]);
			cl[hdistance].position.add(pos[i]);

		}
	}
}