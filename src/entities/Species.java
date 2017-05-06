package entities;

import java.util.*;

import util.Range;
import util.ChanceOutcomes;

class Species {
	public String name;
	public int gestationPeriod;
	public Range numberOffspringProduced;
	public long initialSize;
	public long finalSize;
	public ChanceOutcomes<String> offspring;
	public Body initialBodyStructure;
	public String behaviorFile;
	public ArrayList<String> tags;
	
	public Species(String name,
				   int gestationPeriod,
				   Range numberOffspringProduced,
				   long initialSize,
				   long finalSize,
				   ChanceOutcomes<String> offspring,
				   Body initialBodyStructure,
				   String behaviorFile,
				   ArrayList<String> tags) {
		
		this.name = name;
		this.gestationPeriod = gestationPeriod;
		this.numberOffspringProduced = numberOffspringProduced;
		this.initialSize = initialSize;
		this.finalSize = finalSize;
		this.offspring = offspring;
		this.initialBodyStructure = initialBodyStructure;
		this.behaviorFile = behaviorFile;
		this.tags = tags;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("name: " + name + "\n");
		sb.append("gestationPeriod: " + gestationPeriod + "\n");
		sb.append("numberOffspringProduced: " + numberOffspringProduced.toString() + "\n");
		sb.append("initialSize: " + initialSize + "\n");
		sb.append("finalSize: " + finalSize + "\n");
		sb.append("offspring: " + offspring.toString() + "\n");
		sb.append("initialBodyStructure: " + initialBodyStructure.toString() + "\n");
		sb.append("behaviorFile: " + behaviorFile + "\n");
		sb.append("tags: " + tags.toString() + "\n");
		
		return sb.toString();
	}
}