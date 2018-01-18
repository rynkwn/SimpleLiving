package behavior;

import java.util.*;

import organization.*;
import util.Pair;

public class GroupBehaviorTree {
	
	public Group group;
	
	public GroupBehaviorBlock currentAction;
	public ArrayList<GroupBehaviorBlock> blocks;
	public int next; // Which of the behavior blocks in the next list of arrays should be executed next?
	public int index;
	
	/*
	 * Ultimately, what do I want?
	 * 
	 * I want to be able to execute a series of actions. Those actions should
	 * take in parameters because they may be context sensitive.
	 * 
	 * Check fertility of tile { If above threshold, perform another action }
	 * Create a project { Details about project }
	 * Check group size { Perform action if meets some condition. }
	 */
	
	public GroupBehaviorTree() {
		group = null;
		next = 0;
		index = 0;
		this.blocks = new ArrayList<GroupBehaviorBlock>();
	}

	public GroupBehaviorTree(Group grp) {
		this.group = grp;
		next = 0;
		index = 0;
		
		this.blocks = new ArrayList<GroupBehaviorBlock>();
	}
	
	public GroupBehaviorTree(Group grp, GroupBehaviorTree tree) {
		group = grp;
		next = 0;
		index = 0;
		blocks = tree.blocks;
		
		// TODO: Copy the blocks in the other GroupBehaviorTree.
		//currentAction = blocks.get(0)[0];
		
	}
	
	public void setGroupForBlocks(Group grp) {
		for(GroupBehaviorBlock block : blocks) {
			block.setGroup(grp);
		}
	}
	
	// Executes until an END_TURN.
	public void execute() {
		
		do {
			index = (index + 1) % blocks.size();
			currentAction = blocks.get(index);
			
		} while (currentAction.act());
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < blocks.size(); i++) {
			sb.append(blocks.get(i).toString() + ", ");
			sb.append(";");
		}
		return sb.toString();
	}
}
