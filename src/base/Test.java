/*
 * A test file to pick and choose out tiny pieces of the Simulator for testing! 
 */

package base;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;

import item.*;
import ecology.*;
import entities.*;
import data.*;
import organization.*;
import world.*;
import util.*;
import behavior.*;

public class Test {
	
	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();
		
		
		//Species spec = gson.fromJson(new FileReader("src/data/locust_minor.temp"), Species.class);
		//spec.processJSON();
		
		GroupBehaviorTree volchTree = new GroupBehaviorTree();
		ArrayList<GroupBehaviorBlock> blocks = new ArrayList<GroupBehaviorBlock>();
		
		// Parameters and response for Checking fertility.
		GroupBehaviorActionData d1 = new GroupBehaviorActionData();
		d1.setDouble(.1);
		
		GroupBehaviorBlock b1a = new GroupBehaviorBlock(GroupBehaviorAction.MOVE_RANDOM);
		d1.setBlockData(b1a);
		
		GroupBehaviorBlock b1 = new GroupBehaviorBlock(GroupBehaviorAction.CHECK_FERTILITY, d1);
		blocks.add(b1);
		
		// Create a project.
		// In this case, choose a random wild creature in the tile, and then
		// harvest as much as possible based on available labor.
		
		// Check if group too large.
		GroupBehaviorActionData sizeThreshold = new GroupBehaviorActionData();
		sizeThreshold.setInt(10);
		
		GroupBehaviorActionData splitPct = new GroupBehaviorActionData();
		splitPct.setDouble(.5);
		
		GroupBehaviorBlock splitOffAct = new GroupBehaviorBlock(GroupBehaviorAction.SPLIT_BY_PERCENTAGE, splitPct);
		
		sizeThreshold.setBlockData(splitOffAct);
		GroupBehaviorBlock checkSize = new GroupBehaviorBlock(GroupBehaviorAction.CHECK_SIZE, sizeThreshold);
		
		
		//blocks.add(checkSize);
		
		// End turn.
		GroupBehaviorBlock end = new GroupBehaviorBlock(GroupBehaviorAction.END_TURN);
		blocks.add(end);
		
		
		volchTree.addBlocks(blocks);
		System.out.println(gson.toJson(volchTree));
	}
}

