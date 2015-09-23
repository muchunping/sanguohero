package com.ilife.sanguohero.model;

import com.ilife.sanguohero.db.Props;
import com.ilife.sanguohero.util.ILog;

import java.util.ArrayList;
import java.util.List;

/**
 * ����࣬��ҵİ��
 */
public class Luggage {
	private List<Props> props = new ArrayList<Props>();
	private int capacity = 100;
	
	public boolean putProps(Props prop){
		if(isFull()){
			ILog.e("���������޷�����" + prop.getName());
			return false;
		}
		if(props.contains(prop)){
			if(prop.isStackable())
				prop.amountPlus();
		}else{
			props.add(prop);
		}
		ILog.i(prop.getName() + "�����õ�������");
		return true;
	}
	
	public boolean removeProps(Props prop){
		if(!props.contains(prop)){
			ILog.e("������û��" + prop.getName());
			return false;
		}
		if(prop.getAmount() > 1){
			prop.amountReduce();
		}else{
			props.remove(prop);
		}
		ILog.i("�ӱ������Ƴ���һ����Ʒ(" + prop.getName() + ")");
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
