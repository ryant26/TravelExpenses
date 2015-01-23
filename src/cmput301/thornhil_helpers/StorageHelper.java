package cmput301.thornhil_helpers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

import cmput301.thornhil_dataClasses.Cache;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

public class StorageHelper {
	private static StorageHelper instance = null;
	private Context context;
	private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	private final static String claimsFile = "ClaimsStorage.txt";
	private final static String expensesFile = "ExpensesStorage.txt";
	private final static String cacheFile = "CacheStorage.txt";
	
	private StorageHelper(Context context){
		this.context = context;
	}
	
	public void testStoreCache(Cache cache) throws IOException{
		OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(cacheFile, 0));
		gson.toJson(cache, osw);
		osw.flush();
		osw.close();
	}
	
	public Cache testReadCache() throws IOException{
		Type type = new TypeToken<Cache>(){}.getType();
		InputStreamReader reader = new InputStreamReader(context.openFileInput(cacheFile));
		Cache ret =  gson.fromJson(reader, type);
		reader.close();
		return ret;
	}
	
	public static StorageHelper getInstance(Context context){
		if (instance == null){
			instance = new StorageHelper(context);
		}
		return instance;
	}
	/*
	public void storeAllClaims(Iterable<String> claims) throws FileNotFoundException, IOException{
		BufferedWriter writer = openClaimsStorageForWriting();
		writeStrings(writer, claims);
		writer.close();
	}
	
	public void storeAllExpenses(Iterable<String> expenses) throws FileNotFoundException, IOException {
		BufferedWriter writer = openExpenseStorageforWriting();
		writeStrings(writer, expenses);
		writer.close();
	}
	
	public List<String> getAllClaims() throws FileNotFoundException, IOException{
		BufferedReader reader = openClaimsStorageForReading();
		List<String> output = getData(reader);
		reader.close();
		return output;
	}
	
	public List<String> getAllExpenses() throws FileNotFoundException, IOException{
		BufferedReader reader = openExpensesStorageForReading();
		List<String> outList = getData(reader);
		reader.close();
		return outList;
	}
	
	
	public List<String> getData(BufferedReader reader) throws IOException{
		ArrayList<String> output = new ArrayList<String>();
		while (reader.ready()){
			output.add(reader.readLine());
		}
		return output;
	}
	
	private void writeStrings(BufferedWriter writer, Iterable<String>data) throws IOException{
		for (String s : data){
			writer.write(s + "\n");
		}
	}
	
	private BufferedWriter openClaimsStorageForWriting() throws FileNotFoundException{
		return openStorageForWriting(claimsFile);
	}
	
	private BufferedWriter openExpenseStorageforWriting() throws FileNotFoundException{
		return openStorageForWriting(expensesFile);
	}
	
	private BufferedReader openClaimsStorageForReading() throws FileNotFoundException{
		return openStorageForReading(claimsFile);
	}
	
	private BufferedReader openExpensesStorageForReading() throws FileNotFoundException{
		return openStorageForReading(expensesFile);
	}
	
	private BufferedReader openStorageForReading(String name) throws FileNotFoundException{
		FileInputStream fis = context.openFileInput(name);
		return new BufferedReader(new InputStreamReader(fis));
	}
	
	private BufferedWriter openStorageForWriting(String name) throws FileNotFoundException{
		FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE);
		return new BufferedWriter(new OutputStreamWriter(fos));
	}
	*/
}
