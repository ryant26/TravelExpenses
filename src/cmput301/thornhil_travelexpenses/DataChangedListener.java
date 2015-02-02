package cmput301.thornhil_travelexpenses;

import java.util.ArrayList;


public interface DataChangedListener <T>{
	public void dataItemChanged(ArrayList<Integer> item);
	public void dataItemCreated(ArrayList<Integer> item);
	public void dataItemsDeleted(ArrayList<Integer> itemPositions);
}
