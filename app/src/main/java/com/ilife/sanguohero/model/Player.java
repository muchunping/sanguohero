package com.ilife.sanguohero.model;

import com.ilife.sanguohero.db.Actor;
import com.ilife.sanguohero.db.Equipment;
import com.ilife.sanguohero.db.Scene;
import com.ilife.sanguohero.util.Range;

public class Player extends Actor {
	private int experience;
	private Scene location;

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
		experience = 1;

		strength = 10;
		wisdom = 10;
		agility = 10;
		physique = 10;

		fightTraits.HP = new Range(100, 100);
		fightTraits.MP = new Range(50, 40);
		fightTraits.PA = 40;
		fightTraits.MA = 40;
		fightTraits.BAT = 1.5f;
		fightTraits.IAS = 0;
		fightTraits.ACC = 10;
		fightTraits.DOD = 10;
		
		luggage = new Luggage();
		wearslot = new WearSlot();
		wearslot.setListener(new WearSlot.OnWearChangedListener() {
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
	
	public void equipmentOff(WearSlot.SlotType type){
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
	
	/**
	 * �����+1������ֵ��ȥ�����辭�飬�����趨������Ѫ��ħ��ȫ������������Ϳɷ������
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
	public Scene getLocation() {
		return location;
	}

	public void setLocation(Scene location) {
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
