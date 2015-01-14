package cmput301.thornhil_helpers;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.google.gson.*;

import android.*;
import android.content.Context;
import android.provider.SyncStateContract.Constants;

public class StorageHelper {
	private static StorageHelper instance = null;
	private Context context;
	private final static String tripsFile = "TripsStorage.txt";
	private final static String expensesFile = "ExpensesStorage.txt";
	
	protected StorageHelper(Context context){
		this.context = context;
	}
	
	public static StorageHelper getInstance(Context context){
		if (instance == null){
			instance = new StorageHelper(context);
		}
		return instance;
	}
	
	
	public BufferedWriter openTripsStorageForWriting() throws FileNotFoundException{
		return openStorageForWriting(tripsFile);
	}
	
	public BufferedWriter openExpenseStorageforWriting() throws FileNotFoundException{
		return openStorageForWriting(expensesFile);
	}
	
	public BufferedReader openTripsStorageForReading() throws FileNotFoundException{
		return openStorageForReading(tripsFile);
	}
	
	public BufferedReader openExpensesStorageForReading() throws FileNotFoundException{
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
