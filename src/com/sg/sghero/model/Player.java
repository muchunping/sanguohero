package com.sg.sghero.model;

import com.sg.sghero.db.Actor;
import com.sg.sghero.db.Equipment;
import com.sg.sghero.model.WearSlot.OnWearChangedListener;
import com.sg.sghero.model.WearSlot.SlotType;
import com.sg.sghero.util.Range;

public class Player extends Actor{
	private int level;
	private int experience;
	private int location;

	private int strength;
	private int wisdom;
	private int agility;
	private int physique;
	
	private Luggage luggage;
	private WearSlot wearslot;
	
	public Player(String name) {
		super();
		super.name = name;
		level = 1;
		experience = 0;
		location = 100;

		strength = 10;
		wisdom = 10;
		agility = 10;
		physique = 10;

		fightTraits.HP = new Range(100, 100);
		fightTraits.MP = new Range(100, 100);
		fightTraits.PA = 40;
		fightTraits.MA = 40;
		fightTraits.BAT = 1.5f;
		fightTraits.IAS = 0;
		fightTraits.ACC = 10;
		fightTraits.DOD = 10;
		
		luggage = new Luggage();
		wearslot = new WearSlot();
		wearslot.setListener(new OnWearChangedListener() {
			@Override
			public void wearChanged(Equipment older, Equipment newer) {
				recalculateFT();
			}
		});
	}
	
	public void equipmentOn(Equipment equip){
		wearslot.wear(equip);
	}
	
	public void equipmentOff(int slot){
		wearslot.strip(slot);
	}
	
	public void equipmentOff(SlotType type){
		equipmentOff(type.value);
	}
	
	public void recalculateFT(){
		wearslot.calculateWearTrait(fightTraits);
	}

	private static final int EXP_base = 55;
	private static final int EXP_D = 400;
	private static final int EXP_powbase = 2;

	public static int getRequiredExperienceForNextLevel(int currentLevel) {
		return (int) (EXP_base * Math.pow(currentLevel, EXP_powbase
				+ currentLevel / EXP_D));
	}
	
	public String getExperienceString(){
		return experience  + "/" + getRequiredExperienceForNextLevel(level);
	}

	public boolean canLevelup() {
		return experience >= getRequiredExperienceForNextLevel(level);
	}

	public int getLevel() {
		return level;
	}
	
	/**
	 * 升级：级别+1，经验值减去升级所需经验，其它设定（例如血量魔法全满，获得属性提升和可分配点数）
	 */
	public int levelUp() {
		experience -= getRequiredExperienceForNextLevel(level);
		level++;
		return level;
	}

	public int getExperience() {
		return experience;
	}

	public void addExperience(int experience) {
		this.experience += experience;
	}

	/**
	 * @return Scene code
	 */
	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getWisdom() {
		return wisdom;
	}

	public void setWisdom(int wisdom) {
		this.wisdom = wisdom;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getPhysique() {
		return physique;
	}

	public void setPhysique(int physique) {
		this.physique = physique;
	}

	public Luggage getLuggage() {
		return luggage;
	}

	public void setLuggage(Luggage luggage) {
		this.luggage = luggage;
	}

	public WearSlot getWearslot() {
		return wearslot;
	}
}
