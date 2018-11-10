package de.jeffclan.RandomMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JeffRandomMessageUtils {
	
	// copypasted from ChestSorts getArrayFromCategoryFile
	static String[] getStringArrayFile(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		List<String> lines = new ArrayList<String>();
		while (sc.hasNextLine()) {
			//if(!sc.nextLine().startsWith("#")) {
		  lines.add(sc.nextLine());
			//}
		}

		String[] arr = lines.toArray(new String[0]);
		sc.close();
		return arr;
	}

}
