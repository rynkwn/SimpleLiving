package behavior;

import organization.*;
import util.*;

import java.util.*;

public class GroupBehaviorBlock {
	
	private String name;
	private String parameter;
	private Group group;
	
	public GroupBehaviorBlock(String name, String parameter, Group grp) {
		this.name = name;
		group = grp;
		this.parameter = parameter;
	}
	
	// Returns <INDEX OF NEXT BLOCK TO EXECUTE (Normally 0 or 1 for IF blocks), END_TURN>
	public Pair<Integer, Boolean> act() {
		switch(name) {
		case "CHECK_FERTILITY":
			double threshold = Double.parseDouble(parameter);
			
			if(group.residentTile.minRatio() <= threshold) {
				return new Pair<Integer, Boolean>(1, false);				
			}
			
			return new Pair<Integer, Boolean>(0, false);
			
		case "MOVE_RANDOM":
			group.randomMove();
			return new Pair<Integer, Boolean>(0, false);
		case "SKIP":
			
			return new Pair<Integer, Boolean>(0, false);
		case "END_TURN":
			return new Pair<Integer, Boolean>(0, true);
		case "CHECK_SIZE":
			return new Pair<Integer, Boolean>(0, false);
		case "ATTACK_HOSTILE_GROUPS":
			return new Pair<Integer, Boolean>(0, false);
		}
		
		return new Pair<Integer, Boolean>(0, false);
	}

	public int next() {
		return 0;
	}
	
	public int nextBlock() {
		return 0;
	}

}
