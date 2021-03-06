package scripts.Coordinate_Manipulation.GFF_Manipulation;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class GFFtoBED {
	public static void convertBEDtoGFF(File out_path, File input) throws IOException {
		//GFF:	chr22  TeleGene enhancer  10000000  10001000  500 +  .  touch1
		//BED:	chr12	605113	605120	region_0	0	+

	    String bedName = (input.getName()).substring(0,input.getName().length() - 4) + ".bed";
	    Scanner scan = new Scanner(input);
	    PrintStream OUT = null;
	    if(out_path == null) OUT = new PrintStream(bedName);
	    else OUT = new PrintStream(out_path + File.separator + bedName);
	    
		while (scan.hasNextLine()) {
			String[] temp = scan.nextLine().split("\t");
			if(temp.length == 9) {
				if(!temp[0].contains("track") && !temp[0].contains("#")) {
					String name = temp[8];
					String score = temp[5]; //Get or make direction
					String dir = temp[6];

					//Make sure coordinate start is >= 0
					if(Integer.parseInt(temp[3]) >= 1) {
						int newstart = Integer.parseInt(temp[3]) - 1;
						OUT.println(temp[0] + "\t" + newstart + "\t" + temp[4] + "\t" + name + "\t" + score + "\t" + dir);						
					} else {
						System.out.println("Invalid Coordinate in File!!!\n" + Arrays.toString(temp));
					}
				} else {
					System.out.println("Invalid Coordinate in File!!!\n" + Arrays.toString(temp));
				}
			}
	    }
		scan.close();
		OUT.close();
	}
}
