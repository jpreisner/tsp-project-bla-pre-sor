package pne.project.tsp.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TSPReader {

	public static int[][] getPositionsFromTsp(String filePath) {
		Scanner scanner;
		try {
			scanner = new Scanner(new File(filePath));		 
			while (scanner.hasNextLine()) {
			    String line = scanner.nextLine();
			 
			    System.out.println(line);
				//faites ici votre traitement
			}			 
			scanner.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
