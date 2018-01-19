package deprecated;

import organization.*;

import java.io.*;
import java.util.*;

import com.google.gson.Gson;

import ecology.WildSpecies;
import util.KeyValueReader;

public class GroupBehaviorReader {
	
	public static HashMap<String, GroupBehaviorTree> behaviors;
	
	public static void readBehaviorData(String dirPath) {
		initializeReader();
		readAllBehaviorFiles(dirPath);
	}
	
	public static void initializeReader() {
		behaviors = new HashMap<String, GroupBehaviorTree>();
	}
	
	public static void readAllBehaviorFiles(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		
		for(File file : files) {
			if(file.getName().endsWith(".behavior")) {
				readBehaviorFile(file);
			}
		}
	}
	
	public static void readBehaviorFile(File file) {
		
		try {
			Gson gson = new Gson();
			
			GroupBehaviorTree behaviorTree = gson.fromJson(new FileReader(file), GroupBehaviorTree.class);
			behaviors.put(file.getName(), behaviorTree);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static GroupBehaviorTree getBehavior(String name) {
		return behaviors.get(name);
	}
	
	public static GroupBehaviorTree makeInstanceOf(String name, Group grp) {
		return new GroupBehaviorTree(grp, behaviors.get(name));
	}
	
	// Dumps the structure of GroupBehaviorReader.
		public static String debugDump() {
			StringBuilder sb = new StringBuilder();
			
			sb.append("Beginning debugDump for GroupBehaviorReader..." + "\n\n");
			
			for(String behaviorName : behaviors.keySet()) {
				sb.append(behaviorName + "\n" + "_______________________" + "\n");
				sb.append(behaviors.get(behaviorName).toString());
			}
			
			return sb.toString();
		}

}