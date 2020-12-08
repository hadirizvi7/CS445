import java.io.*;
import java.util.*;

public class Jumbles {
	static HashMap<String, String> hashmap = new HashMap<String, String>();
	static ArrayList<String> dictionary = new ArrayList<String>();
	static ArrayList<String> jumbleWords = new ArrayList<String>();

	public static void main( String args[] ) throws Exception {
		BufferedReader dictReader = new BufferedReader(new FileReader(args[0]));
		while (dictReader.ready()) {
			dictionary.add(dictReader.readLine());
		}
		dictReader.close();

		BufferedReader jumbleReader = new BufferedReader(new FileReader(args[1]));
		while (jumbleReader.ready()) {
			jumbleWords.add(jumbleReader.readLine());
		}
		jumbleReader.close();

		// loop through dictionary and insert into hashmap (key: sorted word, value: list of unsorted words)
		for (String word:dictionary) {
			if (hashmap.containsKey(toCanonical(word))) {
				String temp = hashmap.get(toCanonical(word));
				temp += " " + word;
				hashmap.put(toCanonical(word), temp);
			}

			else {
				hashmap.put(toCanonical(word), word);
			}
		}
		Collections.sort(jumbleWords);

		for (String word:jumbleWords) {
			System.out.print(word + " ");

			if (hashmap.containsKey(toCanonical(word))) {
				String temp = hashmap.get(toCanonical(word));
				String[] arr = temp.split(" ");
				Arrays.sort(arr);
				for (String jumble:arr) {
					System.out.print(jumble + " ");
				}
			}
			else {
				System.out.print("");
			}
			System.out.println("");
		}

	}

	static String toCanonical(String word) {
		char[] letters = word.toCharArray();
		Arrays.sort(letters);
		return new String(letters);
	}
}