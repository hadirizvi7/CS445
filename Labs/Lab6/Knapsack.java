import java.io.*;

public class Knapsack {

	public static void main(String[] args) throws Exception {
		if (args.length < 1 || args.length > 1) {
			System.exit(0);
		}

		String filename = args[0];
		BufferedReader infile = new BufferedReader(new FileReader(filename));
		String set = infile.readLine();
		int target = Integer.parseInt(infile.readLine());

		int[] initialSet = new int[16];

		int count = 0;

		for (String value : set.split(" ")) {
			initialSet[count] = Integer.parseInt(value);
			count += 1;
		}
		infile.close();
		System.out.println(set);
		System.out.println(target);

		for (int bitmap = -1 >>> 16; bitmap >= 0; bitmap--) {
			int sum = 0;
			String bitSet = "";

			for (int i = 15; i >= 0; i--) {
				if ((bitmap >>> i)%2 == 1) {
					sum += initialSet[i];
					bitSet += initialSet[i] + " ";
				}
			}
			if (target == sum) {
				System.out.println(bitSet);
			}
		}
	}
}