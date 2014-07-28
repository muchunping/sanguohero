package com.sg.sghero.model;

import java.util.ArrayList;
import java.util.List;

import com.sg.sghero.db.Props;
import com.sg.sghero.util.ILog;


/**
 * 包裹类，玩家的包裹
 */
public class Luggage {
	private List<Props> props = new ArrayList<Props>();
	private int capacity = 100;
	
	public boolean putProps(Props prop){
		if(isFull()){
			ILog.e("背包已满，无法放入" + prop.getName());
			return false;
		}
		if(props.contains(prop)){
			if(prop.isStackable())
				prop.amountPlus();
		}else{
			props.add(prop);
		}
		ILog.i(prop.getName() + "被放置到背包中");
		return true;
	}
	
	public boolean removeProps(Props prop){
		if(!props.contains(prop)){
			ILog.e("背包中没有" + prop.getName());
			return false;
		}
		if(prop.getAmount() > 1){
			prop.amountReduce();
		}else{
			props.remove(prop);
		}
		ILog.i("从背包中移除了一个物品(" + prop.getName() + ")");
		return true;
	}
	
	public List<Props> getAllProps(){
		return props;
	}
	
	public boolean isEmpty(){
		return props.isEmpty();
	}
	
	public boolean isFull(){
		return props.size() >= capacity;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}
