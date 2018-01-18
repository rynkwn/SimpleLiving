package behavior;

import java.util.HashMap;

/*
 * A data class, holding possible parameters for a behavior action.
 */
public class GroupBehaviorActionData {
	public int intDat;
	public double doubleDat;
	public String stringDat;
	
	public GroupBehaviorBlock blockData;
	public HashMap<String, Integer> stringIntHashMapDat;
	
	// A dummy constructor.
	public GroupBehaviorActionData() { }
	
	//////////////////////////
	//
	// Setter Methods
	//
	public void setInt(int val) { intDat = val; }
	public void setDouble(double val) { doubleDat = val; }
	public void setString(String val) { stringDat = val; }
	public void setBlockData(GroupBehaviorBlock val) { blockData = val;	}
	
	
	/////////////////////////
	//
	// Getter Methods
	//
	public int getInt() {
		return intDat;
	}
	
	public double getDouble() {
		return doubleDat;
	}
	
	public String getString() {
		return stringDat;
	}
	
	public GroupBehaviorBlock getBlock() {
		return blockData;
	}
	
	
}
