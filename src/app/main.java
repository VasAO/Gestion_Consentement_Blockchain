package app;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class main {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;
 
	// String[] args
	public static void main(String[] args) {
		// add our blocks to the blockchain ArrayList:
		Data d = new Data(Long.valueOf(args[0]), Boolean.valueOf(args[1]));
		String lastHash = "";
		try {
			lastHash = getLastHash(args[0]);
		} catch (FileNotFoundException e) {
			lastHash = "0";
			try {
				File file = new File("./" + args[0] + ".json");
				if(file.createNewFile()) {
					FileWriter fileW = new FileWriter("./" + args[0] + ".json");
					fileW.write("{ }");
					fileW.close();
				}
			} catch (IOException IOE) {
				IOE.printStackTrace();
			}
		}
		addBlock(new Block(d, lastHash));
		System.out.println("\nBlockchain is Valid: " + isChainValid(lastHash));
		blockchain.get(0).writeJson(args[0]);
	}

	public static Boolean isChainValid(String previousHash) {
		Block currentBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		// loop through blockchain to check hashes:
		currentBlock = blockchain.get(0);
		// compare registered hash and calculated hash:
		if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
			System.out.println("Current Hashes not equal");
			return false;
		}
		// compare previous hash and registered previous hash
		if (!previousHash.equals(currentBlock.previousHash)) {
			System.out.println("Previous Hashes not equal");
			return false;
		}
		// check if hash is solved
		if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
			System.out.println("This block hasn't been mined");
			return false;
		}
		return true;
	}

	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}

	private static String getLastHash(String fileName) throws FileNotFoundException {
		String hash = "0";
		String json = "";
		File file = new File("./" + fileName + ".json");
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			json = json + scan.nextLine();
		}
		scan.close();
		int index = json.lastIndexOf("previousHash") - 5;
		if (index != -6) {
			hash = json.substring(index - 64, index);
		}
		return hash;
	}
}
