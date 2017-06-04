package behavior;

import java.util.*;

import organization.*;

public class GroupBehaviorTree {
	
	public Group group;
	
	//public ArrayList<GroupBehaviorBlock> blocks[];
	public ArrayList<String[]> blocks;
	public Object data; // Individual group behavior blocks will cast this to something that makes sense.
	public int next; // Which of the behavior blocks in the next list of arrays should be executed next?

	public GroupBehaviorTree(Group grp) {
		this.group = grp;
		next = 0;
		
		blocks = new ArrayList<String[]>();
		data = null;
	}
	
	public void act(String command) {
		switch(command) {
		case "CHECK_FERTILITY":
			break;
		case "RANDOMLY_MOVE":
			
			break;
		case "SKIP":
			
			break;
		case "END_TURN":
			break;
		case "CHECK_SIZE":
			break;
		case "ATTACK_HOSTILE_GROUPS":
			break;
		}
	}
}
