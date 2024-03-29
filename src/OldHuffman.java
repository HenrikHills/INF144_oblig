import java.util.*;
import java.io.*;


abstract class HuffmanTree implements Comparable<HuffmanTree> {
	public final int frequency; 

	public HuffmanTree(int freq) { 
		frequency = freq; 
	}

	public int compareTo(HuffmanTree tree) {
		return frequency - tree.frequency;
	}
}

class HuffmanLeaf extends HuffmanTree {
	public final char value;

	public HuffmanLeaf(int freq, char val) {
		super(freq);
		value = val;
	}
}

class HuffmanNode extends HuffmanTree {
	public final HuffmanTree left, right;

	public HuffmanNode(HuffmanTree l, HuffmanTree r) {
		super(l.frequency + r.frequency);
		left = l;
		right = r;
	}
}

public class OldHuffman {

	static PrintWriter out;
	public static HuffmanTree buildTree(int[] charFreqs) {
		PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();

		for (int i = 0; i < charFreqs.length; i++)
			if (charFreqs[i] > 0)
				trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));

		assert trees.size() > 0;
		// loop until there is only one tree left
		while (trees.size() > 1) {
			// two trees with least frequency
			HuffmanTree a = trees.poll();
			HuffmanTree b = trees.poll();

			// put into new node and re-insert into queue
			trees.offer(new HuffmanNode(a, b));
		}
		return trees.poll();
	}

	public static void printCodes(HuffmanTree tree, StringBuffer prefix) {
		assert tree != null;
		if (tree instanceof HuffmanLeaf) {
			HuffmanLeaf leaf = (HuffmanLeaf)tree;

			// print out character, frequency, and code for this leaf (which is just the prefix)
			System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);
			out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);

		} else if (tree instanceof HuffmanNode) {
			HuffmanNode node = (HuffmanNode)tree;

			// traverse left
			prefix.append('0');
			printCodes(node.left, prefix);
			prefix.deleteCharAt(prefix.length()-1);

			// traverse right
			prefix.append('1');
			printCodes(node.right, prefix);
			prefix.deleteCharAt(prefix.length()-1);
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		String test = ReadFile();
		int[] charFreqs = new int[256];

		for (char c : test.toCharArray()) {
			charFreqs[c]++;
		}
		HuffmanTree tree = buildTree(charFreqs);

		out = new PrintWriter("Huffman.txt");
		printCodes(tree, new StringBuffer());
		out.close();
	}


	private static String ReadFile() { 
		InputStreamReader inputSR = new InputStreamReader(System.in); 
		BufferedReader bufferedR = new BufferedReader(inputSR); 
		String text = "";
		try {
			String line = null;
			File file = new File("src/Folktale.txt");

			bufferedR = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

			while((line = bufferedR.readLine()) != null){
				text += line.toLowerCase().replaceAll(" ", "");
			}
		} catch(IOException ioe) { 
			System.out.println("Error reading from file");
		} 
		return text; 
	} 
}