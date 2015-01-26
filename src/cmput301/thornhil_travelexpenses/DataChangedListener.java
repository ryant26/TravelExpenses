package cmput301.thornhil_travelexpenses;

import java.util.ArrayList;


public interface DataChangedListener <T>{
	public void dataItemChanged(T item);
	public void dataItemCreated(T item);
	public void dataItemsDeleted(ArrayList<Integer> itemPositions);
}
