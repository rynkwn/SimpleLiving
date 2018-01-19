package behavior;

import java.util.HashMap;

/*
 * A data class, holding possible parameters for a behavior action.
 */
public class GroupBehaviorActionData {
	public String json;
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
		
		stringIntHashMapDat = new HashMap<String, Integer>();
		setStringIntHashMap(copy.stringIntHashMapDat);
	}
	
	//////////////////////////
	//
	// Setter Methods
	//
	public void setInt(int val) { intDat = val; }
	public void setDouble(double val) { doubleDat = val; }
	public void setString(String val) { stringDat = val; }
	public void setBlockData(GroupBehaviorBlock val) { blockData = val;	}
	public void setStringIntHashMap(HashMap<String, Integer> map) {
		for(String s : map.keySet())
			stringIntHashMapDat.put(s,  map.get(s));
	}
	
	
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
	
	public HashMap<String, Integer> getStringIntHashMap() {
		return stringIntHashMapDat;
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
