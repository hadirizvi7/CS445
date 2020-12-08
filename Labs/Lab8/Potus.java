import java.io.*;
import java.util.*;

public class Potus
{
	public static void main(String args[]) throws Exception
	{
		TreeMap<String, ArrayList<String>> state2Pres = new TreeMap<String, ArrayList<String>>();
		TreeSet<String> allStates = new TreeSet<String>();
		TreeSet<String> allPres = new TreeSet<String>();
		TreeSet<String> statePres = new TreeSet<String>();
		TreeSet<String> listOfStates = new TreeSet<String>();
		BufferedReader state2PresFile = new BufferedReader(new FileReader("state2Presidents.txt"));
		BufferedReader allPresFile = new BufferedReader(new FileReader("allPresidents.txt"));
		BufferedReader listOfStatesFile = new BufferedReader(new FileReader("allStates.txt"));

		while (allPresFile.ready())
		{
			allPres.add(allPresFile.readLine());
		}

		while (listOfStatesFile.ready())
		{
			listOfStates.add(listOfStatesFile.readLine());
		}

		while (state2PresFile.ready())
		{
			String[] tokens = state2PresFile.readLine().split("\\s+");
			ArrayList<String> currPres = new ArrayList<String>(Arrays.asList(tokens));
			allStates.add(currPres.get(0));
			currPres.remove(0);
			state2Pres.put(tokens[0], currPres);
		}
		for (String state: allStates)
		{
			System.out.print(state + " ");
			ArrayList<String> members = state2Pres.get(state);
			Collections.sort(members);
			for (String member: members)
			{
				System.out.print(member + " ");
			}
			System.out.println("");
		}
		System.out.println();
		for (String pres:allPres)
		{	
			for (String member:state2Pres.keySet())
			{
				if (state2Pres.get(member).contains(pres))
				{
					statePres.add(pres);
					System.out.print(pres+" ");
					System.out.print(member + " ");
					System.out.println();
				}
			}
		}
		System.out.println();
		for (String pres:allPres)
		{
			if (!statePres.contains(pres)){
				System.out.println(pres);
			}
		}
		System.out.println();
		for (String state:listOfStates)
		{
			if (!state2Pres.containsKey(state))
			{
				System.out.println(state);
			}
		}
	}
}