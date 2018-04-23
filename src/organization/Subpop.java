package organization;

import data.LaborPool;
import entities.BiologicalProduct;
import entities.Body;
import entities.SpeciesReader;
import entities.Species;

import java.util.HashMap;
import java.util.HashSet;

import util.ChanceOutcomes;
import util.Range;

public class Subpop {
	public String species;
	
	// The contentment of the pop, from 0.0 to 200.0
	public double happiness;
	
	// Population distribution by age.
	public long child;
	public long juvenile;
	public long adult;
	public long elderly;
	
	// A double that tracks pop growth. This way, a small pop can gradually increase.
	public double popBuildUp;
	public double childToJuvenile;
	public double juvenileToAdult;
	public double adultToElderly;
	
	public Subpop(String species, double happiness, 
			long childPop, long juvenilePop, long adultPop, long elderlyPop) {
		this.species = species;
		
		this.happiness = happiness;
		
		this.child = childPop;
		this.juvenile = juvenilePop;
		this.adult = adultPop;
		this.elderly = elderlyPop;
		
		this.popBuildUp = 0.0;
	}
	
	/*
	 * Return a list of needs for this population. Missing these needs will
	 * have a significant impact on population growth and happiness.
	 */
	public HashMap<String, Long> getNeeds() {
		HashMap<String, Long> needs = new HashMap<String, Long>();
		
		Species spec = SpeciesReader.getSpecies(species);
		
		long initialSize = spec.initialSize;
		long adultSize = spec.finalSize;
		int timeTillMaturation = spec.timeTillMaturation;
		
		// 25% of time spent as a child
		// 25% spent as juvenile.
		// Then adult until elderly (not yet implemented.)
		
		long childSize = (adultSize - initialSize) / 2;
		long juvenileSize = adultSize;
		
		// Figure out sizes of children, juvenile, adults, and elderly
		// We estimate up! So children consume at the higher end of their growth until
		// they become juveniles.
		// Maturation is split 50% between children and juvenile. Adults 
		
		// TODO: Figure out consumption per child/juvenile/adult/elderly,
		// using size of 
		
		return needs;
	}
	
	/*
	 * Return a list of wants for this population. Missing these will not affect pop growth,
	 * but will impact happiness.
	 */
	public HashMap<String, Long> getWants() {
		
	}
	
	
	/*
	 * Returns a map of Species -> children to be processed by the Population.
	 * Also moves pops to different buckets (child -> juvenile, e.t.c.)
	 * 
	 * We assume that we're moving forward by 1 turn.
	 */
	public HashMap<String, Long> newPops() {
		HashMap<String, Long> pops = new HashMap<String, Long>();
		
		Species spec = SpeciesReader.getSpecies(species);
		
		// First, increase popBuildUp.
		// gestPeriod determines normal growth. Specifically just 1/gestPeriod.
		int gestPeriod = spec.gestationPeriod;
		Range numOffspringProduced = spec.numberOffspringProduced;
		int averageOffspring = (numOffspringProduced.max - numOffspringProduced.min) / 2;
		
		// Then, you multiply by Number of Pops * numberOffspringProduced
		// Then for each Offspring Species, you produce a number.
		// Simple!
		double normalGrowth = (1.0 / gestPeriod * averageOffspring);
		popBuildUp += (normalGrowth * adult);
		long newPops = (long) popBuildUp;
		popBuildUp -= newPops;

		HashMap<String, Double> offspringSpecies = spec.offspring.getReverseMap();
		for(String specName : offspringSpecies.keySet()) {
			pops.put(specName, (long) (newPops * offspringSpecies.get(specName)));
		}
		
		
		// Then, move child -> Juvenile.
		double childGrowth = (1.0 / (spec.timeTillMaturation / 2)) * child;
		childToJuvenile += childGrowth;
		long childrenGrownToJuvenile = (long) childToJuvenile;
		childToJuvenile -= childrenGrownToJuvenile;
		child -= childrenGrownToJuvenile;
		juvenile += childrenGrownToJuvenile;
		
		// Move juvenile -> adult
		double juvenileGrowth = (1.0 / (spec.timeTillMaturation / 2)) * juvenile;
		juvenileToAdult += juvenileGrowth;
		long juvenileGrownToAdult = (long) juvenileToAdult;
		juvenileToAdult -= juvenileGrownToAdult;
		juvenile -= juvenileToAdult;
		adult += juvenileToAdult;
		
		// No current setup for growth to elderly.
		// TODO.
		
		return pops;
	}
	
	/*
	 * Returns the amount of labor produced by this subpop for this turn.
	 */
	public LaborPool(?) getLabor() {
		
	}

}
