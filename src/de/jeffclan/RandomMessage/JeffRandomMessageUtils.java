package de.jeffclan.RandomMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
	
	public static byte[] getBytes(InputStream is) throws IOException {

	    int len;
	    int size = 1024;
	    byte[] buf;

	    if (is instanceof ByteArrayInputStream) {
	      size = is.available();
	      buf = new byte[size];
	      len = is.read(buf, 0, size);
	    } else {
	      ByteArrayOutputStream bos = new ByteArrayOutputStream();
	      buf = new byte[size];
	      while ((len = is.read(buf, 0, size)) != -1)
	        bos.write(buf, 0, len);
	      buf = bos.toByteArray();
	    }
	    return buf;
	  }

}
