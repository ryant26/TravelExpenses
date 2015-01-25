package cmput301.thornhil_travelexpenses;


public interface DataChangedListener <T>{
	public void dataItemChanged(T item);
	public void dataItemCreated(T item);
}
