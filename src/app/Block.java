package app;

import java.util.Date;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Block {
	
	public String hash;
	public String previousHash; 
	private Data data = new Data(); //our data will be a simple message.
	private long timeStamp; //as number of milliseconds since 1/1/1970.
	private int nonce;
	
	//Block Constructor.  
	public Block(Data data,String previousHash ) {
		this.data.equals(data);
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}
	
	//Calculate new hash based on blocks contents
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				data 
				);
		return calculatedhash;
	}
	
	//Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty) {
		String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Hash of the mined block : " + hash);
	}
	
	public String toString() {
		return "{\nhash : " + this.hash + ",\npreviousHash : " + this.previousHash + "\ndata : " + this.data + "\ntimeStamp : " + Long.toString(this.timeStamp) + "\nnonce : " + Integer.toString(this.nonce) + "\n}";
	}

	public void writeJson(String fileName) {
		String json = "";
		try{
			File file = new File("./" + fileName + ".json");
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) {
				json = json + scan.nextLine();
			}
			scan.close();

			Gson gson = new Gson();
			JsonObject input = gson.fromJson(json, JsonObject.class);

			JsonObject newBlock = new JsonObject();
			newBlock.addProperty("previousHash", this.previousHash);
			newBlock.add("data", this.data.toJson());
			newBlock.addProperty("timestamp", this.timeStamp);
			newBlock.addProperty("nonce", this.nonce);
	
			input.add(this.hash, newBlock);
	
			try{
				FileWriter fileW = new FileWriter("./" + fileName + ".json");
				fileW.write(input.toString());
				fileW.close();
			} catch (IOException e) {e.printStackTrace();}
		} catch (IOException e) {e.printStackTrace();}
    }
}
