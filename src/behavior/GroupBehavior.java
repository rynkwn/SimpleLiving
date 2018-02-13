package behavior;

import java.util.HashMap;

import deprecated.GroupBehaviorBlock;
import organization.Group;
import organization.Project;
import organization.ProjectType;
import world.World;

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
			
			// Create a project, where we randomly select a local wild species
			// and harvest as much as we can in a single turn.
			HashMap<String, Integer> localWildlife = grp.ecoTile.localWildlife();
			
			if(localWildlife.size() > 0) {
				Set<String> localSpecies = localWildlife.keySet();
				int numSpecies = localSpecies.size();

				// Randomly select a species to harvest.
				int randomSelection = new Random().nextInt(numSpecies);
				Iterator<String> iter = localSpecies.iterator();

				for(int i = 0; i< randomSelection - 1; i++) {
					iter.next();
				}

				String targetSpec = iter.next();

				int availableSpec = localWildlife.get(targetSpec);
				int maxHarvestable = Project.maxAmountProducible(ProjectType.GATHER, targetSpec, grp.availableLabor);
				int targetHarvest = Math.min(availableSpec, maxHarvestable);
				
				if(targetHarvest > 0) {
					Project harvest = new Project(ProjectType.GATHER, grp, targetSpec, targetHarvest);
					grp.addProject(harvest);
				}
			}
			
			
			break;
		}
	}
}
