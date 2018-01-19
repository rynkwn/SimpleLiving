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
	public GroupBehaviorActionData() { 
		intDat = 0;
		doubleDat = 0;
		stringDat = "";
		
		stringIntHashMapDat = new HashMap<String, Integer>();
	}
	
	public GroupBehaviorActionData(GroupBehaviorActionData copy) {
		intDat = copy.intDat;
		doubleDat = copy.doubleDat;
		stringDat = copy.stringDat;
		
		if(copy.blockData != null)
			blockData = new GroupBehaviorBlock(copy.blockData);
	}
	
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
	
	/////////////////////////
	//
	// Existence Methods
	//
	public boolean hasBlock() {
		return blockData != null;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(intDat + ", ");
		sb.append(doubleDat + ", ");
		sb.append(stringDat + ", ");
		sb.append(blockData + ", ");
		sb.append(stringIntHashMapDat + ", ");
				
		return sb.toString();
	}
	
}
