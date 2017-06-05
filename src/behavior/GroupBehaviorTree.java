package behavior;

import java.util.*;

import organization.*;
import util.Pair;

public class GroupBehaviorTree {
	
	public Group group;
	
	//public ArrayList<GroupBehaviorBlock> blocks[];
	public String currentAction;
	public ArrayList<String[]> blocks;
	public int next; // Which of the behavior blocks in the next list of arrays should be executed next?
	public int index;
	
	public GroupBehaviorTree(ArrayList<String[]> blocks) {
		group = null;
		next = 0;
		index = 0;
		this.blocks = blocks;
	}

	public GroupBehaviorTree(Group grp) {
		this.group = grp;
		next = 0;
		index = 0;
		
		blocks = new ArrayList<String[]>();
	}
	
	public GroupBehaviorTree(Group grp, GroupBehaviorTree tree) {
		group = grp;
		next = 0;
		index = 0;
		blocks = tree.blocks;
	}
	
	// Executes until an END_TURN.
	public void execute() {
		
		while(! act()) {
			//Pair<Integer, Boolean> actionResults = blocks.get(index)[next].act();
			//keepExecuting = ! actionResults.right();
			//next = actionResults.left();
			index = index + 1 % blocks.size();
			currentAction = blocks.get(index)[next];
		}
		
	}
	
	public boolean act() {
		
		String parameter = currentAction.substring(currentAction.indexOf('='));
		
		switch(currentAction) {
			case "CHECK_FERTILITY":
			double threshold = Double.parseDouble(parameter);
			
			if(group.residentTile.minRatio() <= threshold) {
				next = 1;
				return false;				
			}
			
			next = 0;
			return false;
			
		case "MOVE_RANDOM":
			group.randomMove();
			next = 0;
			return false;
		case "SKIP":
			next = 0;
			return false;
		case "END_TURN":
			next = 0;
			return false;
		}
		
		return false;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < blocks.size(); i++) {
			for(int j = 0; j < blocks.get(i).length; j++) {
				sb.append(blocks.get(i)[j] + ", ");
			}
			sb.append(";");
		}
		return sb.toString();
	}
}
