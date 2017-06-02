package behavior;

import organization.*;

import java.util.*;

public class GroupBehaviorBlock {
	
	private String name;
	private ArrayList<String> parameters;
	private Group group;
	private ArrayList<GroupBehaviorBlock> nodes;
	
	public GroupBehaviorBlock(String name, ArrayList<String> parameters, Group grp) {
		group = grp;
		nodes = new ArrayList<GroupBehaviorBlock>();
	}
	
	// Do something
	public void act() {
		if(name.equals("generic_nomad")) {
			
		}
	}

	public GroupBehaviorBlock next() {
		return nodes.get(nextBlock());
	}
	
	public int nextBlock() {
		return 0;
	}

}
