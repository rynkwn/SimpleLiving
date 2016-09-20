package Item;

public class Resource {
	private long amount;
	
	public Resource(long amount) {
		this.amount = amount;
	}
	
	public boolean decrement(long val) {
		if (amount >= val) {
			amount -= val;
			return true;
		}
		
		return false;
	}
	
	public void increment(long val) {
		amount += val;
	}
	
	public long getAmount(){ return amount; }
}
