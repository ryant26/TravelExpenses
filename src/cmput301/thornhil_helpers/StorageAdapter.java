package cmput301.thornhil_helpers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cmput301.thornhil_dataClasses.Cache;
import cmput301.thornhil_dataClasses.DataItem;
import cmput301.thornhil_dataClasses.Expense;
import cmput301.thornhil_dataClasses.Claim;

import com.google.gson.*;

public class StorageAdapter {
	private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	private StorageHelper storageHelper;
	
	public StorageAdapter(Context context){
		storageHelper = storageHelper.getInstance(context);
	}
	
	public Cache getCacheFromFile() throws IOException{
		return storageHelper.testReadCache();
	}
	
	public void storeCacheToFile(Cache cache) throws IOException {
		storageHelper.testStoreCache(cache);
	}
}
