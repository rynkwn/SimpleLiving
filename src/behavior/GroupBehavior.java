package behavior;

import deprecated.GroupBehaviorBlock;
import organization.Group;

/*
 * When passed in a group behavior type and a group, proceeds to process group behaviors.
 */
public class GroupBehavior {
	
	public static void execute(Group grp, GroupBehaviorType type) {
		switch(type) {
		case VOLCH_PEACEFUL:
			// Check fertility of tile.
			double fertilityThreshold = .10;
			
			if(grp.residentTile.minRatio() <= fertilityThreshold) {
				// If true, then move to a random tile.
				grp.randomMove();
			}
			
			// Create a project.
			
			break;
		}
	}
}
