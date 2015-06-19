package lk.score.androphsy.indexer.ImageUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OffsetExtractor {
	public Map<String, String> getOffsetValuePairs(String filename) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(filename));

		HashMap<String, String> resultMap = new HashMap<String, String>();

		while (scanner.hasNextLine()) {
			String[] columns = scanner.nextLine().split(" +");
			resultMap.put(columns[1], columns[2]);
		}
		return resultMap;
	}
}
