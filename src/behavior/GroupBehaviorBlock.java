package behavior;

import organization.*;

import java.util.*;

public class GroupBehaviorBlock {
	
	private String name;
	private Group group;
	private ArrayList<GroupBehaviorBlock> nodes;
	
	public GroupBehaviorBlock(String name, Group grp) {
		group = grp;
		nodes = new ArrayList<GroupBehaviorBlock>();
	}
	
	// Do something
	public void act() {
		
	}

	public GroupBehaviorBlock next() {
		return nodes.get(nextBlock());
	}
	
	public int nextBlock() {
		return 0;
	}

}
