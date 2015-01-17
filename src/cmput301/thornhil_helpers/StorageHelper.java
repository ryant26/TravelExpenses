package cmput301.thornhil_helpers;

import java.io.Closeable;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.io.OutputStreamWriter;
import java.io.WriteAbortedException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

import android.*;
import android.content.Context;
import android.provider.SyncStateContract.Constants;

public class StorageHelper {
	private static StorageHelper instance = null;
	private Context context;
	private final static String tripsFile = "TripsStorage.txt";
	private final static String expensesFile = "ExpensesStorage.txt";
	
	private StorageHelper(Context context){
		this.context = context;
	}
	
	public static StorageHelper getInstance(Context context){
		if (instance == null){
			instance = new StorageHelper(context);
		}
		return instance;
	}
	
	public void storeAllTrips(Iterable<String> trips) throws FileNotFoundException, IOException{
		BufferedWriter writer = openTripsStorageForWriting();
		writeStrings(writer, trips);
		writer.close();
	}
	
	public void storeAllExpenses(Iterable<String> expenses) throws FileNotFoundException, IOException {
		BufferedWriter writer = openExpenseStorageforWriting();
		writeStrings(writer, expenses);
		writer.close();
	}
	
	public List<String> getAllTrips() throws FileNotFoundException, IOException{
		BufferedReader reader = openTripsStorageForReading();
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
	
	private BufferedWriter openTripsStorageForWriting() throws FileNotFoundException{
		return openStorageForWriting(tripsFile);
	}
	
	private BufferedWriter openExpenseStorageforWriting() throws FileNotFoundException{
		return openStorageForWriting(expensesFile);
	}
	
	private BufferedReader openTripsStorageForReading() throws FileNotFoundException{
		return openStorageForReading(tripsFile);
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
}
