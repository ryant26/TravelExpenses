//	Copyright 2015 Ryan Thornhill
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package cmput301.thornhil_helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cmput301.thornhil_dataClasses.DataItem;
import cmput301.thornhil_dataClasses.Expense;
import cmput301.thornhil_dataClasses.Claim;

import com.google.gson.*;

public class StorageAdapter {
	private Gson gson = new Gson();
	private StorageHelper storageHelper;
	
	public StorageAdapter(Context context){
		storageHelper = storageHelper.getInstance(context);
	}
	
	public void storeAllClaims(Iterable<Claim> claims) throws IOException{
		storageHelper.storeAllClaims(toJson(claims));
	}
	
	public void storeAllExpenses(Iterable<Expense> expenses) throws IOException{
		storageHelper.storeAllExpenses(toJson(expenses));
	}
	
	public List<Claim> getAllClaims() throws IOException{
		return toDataItem(storageHelper.getAllClaims(), Claim.class);
	}
	
	public List <Expense> getAllExpenses() throws IOException{
		return toDataItem(storageHelper.getAllExpenses(), Expense.class);
	}
	
	private <T extends DataItem> List<T> toDataItem(Iterable<String> data, Class<T> type){
		ArrayList<T> output = new ArrayList<T>();
		for (String d : data){
			output.add(unpackData(d, type));
		}
		return output;
	}
	
	private <T extends DataItem> List<String> toJson(Iterable<T> data){
		ArrayList<String> output = new ArrayList<String>();
		for (T d : data){
			output.add(packData(d));
		}
		return output;
	}
	
	private <T extends DataItem> String packData(T data){
		return gson.toJson(data);
	}
	
	private <T extends DataItem> T unpackData(String json, Class<T> myClass){
		return gson.fromJson(json, myClass);
	}
}
