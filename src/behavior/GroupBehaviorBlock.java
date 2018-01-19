package behavior;

import organization.*;
import util.*;

import java.util.*;

public class GroupBehaviorBlock {
	
	private GroupBehaviorAction action;
	private GroupBehaviorActionData gbad;
	private Group group;
	
	public GroupBehaviorBlock(GroupBehaviorAction actionName) {
		this.action = actionName;
		gbad = new GroupBehaviorActionData();
	}
	
	public GroupBehaviorBlock(GroupBehaviorAction actionName, GroupBehaviorActionData gbad) {
		this.action = actionName;
		this.gbad = new GroupBehaviorActionData(gbad);
	}
	
	public GroupBehaviorBlock(GroupBehaviorBlock copyBlock) {
		this.action = copyBlock.action;
		this.gbad = new GroupBehaviorActionData(copyBlock.gbad);
		this.group = copyBlock.group;
	}
	
	/*
	 * Set the group for this GroupBehaviorBlock
	 */
	public void setGroup(Group grp) {
		group = grp;
		if(gbad.hasBlock())
			gbad.getBlock().setGroup(grp);
			
	}
	
	// Returns <INDEX OF NEXT BLOCK TO EXECUTE (Normally 0 or 1 for IF blocks), END_TURN>
	// Return TRUE if we should keep going. Return FALSE if we perform an END_TURN action.
	public boolean act() {
		switch(action) {
		case CHECK_FERTILITY:
			double fertilityThreshold = gbad.getDouble();
			
			if(group.residentTile.minRatio() <= fertilityThreshold) {
				// If true, then perform the followup action.
				GroupBehaviorBlock response = gbad.getBlock();
				return response.act();
			}
			
			return true;
			
		case CHECK_SIZE:
			int sizeThreshold = gbad.getInt();
			
			if(group.size() >= sizeThreshold) {
				GroupBehaviorBlock response = gbad.getBlock();
				return response.act();
			}
			
		case END_TURN:
			return false;

		case MOVE_RANDOM:
			group.randomMove();
			return true;
			
		case SPLIT_BY_PERCENTAGE:
			double pct = gbad.getDouble();
			group.split(pct);
			return true;
		}
		
		return true;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(action);
		sb.append(": " + gbad.toString());
		
		return sb.toString();
	}
}