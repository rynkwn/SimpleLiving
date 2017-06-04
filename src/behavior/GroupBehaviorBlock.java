package behavior;

import organization.*;

import java.util.*;

public class GroupBehaviorBlock {
	
	private String name;
	private Object parameter;
	private Group group;
	
	public GroupBehaviorBlock(String name, Object parameter, Group grp) {
		this.name = name;
		group = grp;
		this.parameter = parameter;
	}
	
	// Do something
	public void act() {
		switch(name) {
		case "CHECK_FERTILITY":
			break;
		case "END_TURN":
			break;
		case "CHECK_SIZE":
			break;
		case "ATTACK_HOSTILE_GROUPS":
			break;
		}
	}

	public int next() {
		return 0;
	}
	
	public int nextBlock() {
		return 0;
	}

}
