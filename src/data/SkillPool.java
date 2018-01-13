package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import entities.Entity;

/*
 * Like the LaborPool, the SkillPool represents skills (and associated 
 * experience in those skills). Also provides a number of helper methods
 * for interacting with these skills.
 */
public class SkillPool {
	
	// Combat-related skills.
	
	// These skills affect to-hit chances with
	// the relevant weapons. Dodging reduces an opponents
	// to-hit chance.
	public static final String SKILL_MELEE = "Melee";
	public static final String SKILL_RANGED = "Ranged";
	public static final String SKILL_DODGING = "Dodging";
	
	// Strength increases melee damage, and also affects
	// carrying capacity.
	public static final String SKILL_STRENGTH = "Strength";
	
	// Speed affects run speed in combat as well as overworld speed.
	public static final String SKILL_SPEED = "Speed";
	
	
	// Labor-related skills
	
	// Skills related to trapping, hunting, and gathering.
	public static final String SKILL_NATURALISM = "Naturalism";
	public static final String SKILL_ENGINEERING = "Engineering";
	
	// <Skills -> Level>, and <Skills -> Experience held>
	public HashMap<String, Integer> skills;
	public HashMap<String, Long> experience;
	
	public SkillPool(int minLevel, int maxLevel) {
		skills = new HashMap<String, Integer>();
		experience = new HashMap<String, Long>();
		
		randomizeSkills(minLevel, maxLevel);
	}
	
	/*
	 * Assigns skills randomly, up to some maximum level.
	 * 
	 * Every skill value is randomly set in [minLevel, maxLevel]
	 */
	public void randomizeSkills(int minLevel, int maxLevel) {
		Random rand = new Random();
		
		for(String skill : skillList()) {
			set(skill, rand.nextInt(maxLevel - minLevel + 1) + minLevel);
			zeroExperience(skill);
		}
	}
	
	/*
	 * Sets skill value (and initializes associated experience counter as well if relevant.)
	 */
	public void set(String skillName, int level) {
		skills.put(skillName, level);
		
		if(!experience.containsKey(skillName))
			experience.put(skillName, 0L);
	}
	
	// Try to get the relevant skill value of this entity.
	public int get(String skillName) {
		if(skills.containsKey(skillName)) {
			return skills.get(skillName);
		} else {
			return 0;
		}
	}
	
	/*
	 * Zeroes experience for a given skill.
	 */
	public void zeroExperience(String skillName) {
		if(experience.containsKey(skillName)) {
			experience.put(skillName, 0L);
		}
	}
	
	/* 
	 * Adds experience for a specific skill.
	 * Experiment requirement for a skill increase is 10^{skill level} (
	 * or some other exponential increase.) When a skill is used, we should
	 * probably increase experience (or in some other fashion.) And then check
	 * if that's sufficient for a level up. If it is, then we reset experience
	 * for that skill.
	 */
	public void addExperience(String skillName, long exp) {
		long curExpValue = exp;
		int curLevel = 0;
		
		if(experience.containsKey(skillName)) {
			curLevel = skills.get(skillName);
			curExpValue += experience.get(skillName);
		} else {
			// If we have no experience for it, we assume
			// we also don't have the skill.
			skills.put(skillName, 0);
			experience.put(skillName, 0L);
		}
		
		long expNeededToLevel = Entity.experienceToLevel(curLevel);
		
		int levelsGained = 0;
		long expRemaining = curExpValue;
		
		while(expRemaining >= expNeededToLevel) {
			levelsGained++;
			expRemaining -= expNeededToLevel;
			expNeededToLevel = Entity.experienceToLevel(curLevel + levelsGained);
		}
		
		skills.put(skillName, curLevel + levelsGained);
		experience.put(skillName, expRemaining);
	}
	
	
	/*
	 * Return a list representation of all skills.
	 */
	public static ArrayList<String> skillList() {
		ArrayList<String> skills = new ArrayList<String>();
		
		// Combat skills
		skills.add(SKILL_MELEE);
		skills.add(SKILL_RANGED);
		skills.add(SKILL_DODGING);
		
		// Base attribute skills.
		skills.add(SKILL_STRENGTH);
		skills.add(SKILL_SPEED);
		
		// Labor related skills
		skills.add(SKILL_NATURALISM);
		skills.add(SKILL_ENGINEERING);
		
		return skills;
	}
}
