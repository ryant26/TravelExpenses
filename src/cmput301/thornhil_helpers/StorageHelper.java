package cmput301.thornhil_helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.google.gson.*;

import android.*;
import android.content.Context;
import android.provider.SyncStateContract.Constants;

public class StorageHelper {
	private static StorageHelper instance = null;
	private Gson gson = new Gson();
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
	
	private FileOutputStream openTripsStorageForWriting() throws FileNotFoundException{
		return context.openFileOutput(tripsFile, context.MODE_APPEND);	
	}
	
	private FileOutputStream openExpenseStorageforWriting() throws FileNotFoundException{
		return context.openFileOutput(expensesFile, context.MODE_APPEND);
	}
	
	private FileInputStream openTripsStorageForReading() throws FileNotFoundException{
		return context.openFileInput(tripsFile);
	}
	
	private FileInputStream openExpensesStorageForReading() throws FileNotFoundException{
		return context.openFileInput(expensesFile);
	}
}
