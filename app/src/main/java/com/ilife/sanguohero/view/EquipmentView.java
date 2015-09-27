package com.ilife.sanguohero.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.ilife.sanguohero.R;
import com.ilife.sanguohero.model.Player;

/**
 * 装备界面
 * Created by Administrator on 2015/9/26.
 */
public class EquipmentView extends RelativeLayout {
    public EquipmentView(Context context) {
        super(context);
        init(context);
    }

    public EquipmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EquipmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context c){
        LayoutInflater.from(c).inflate(R.layout.layout_equipment, this);
    }

    public void setPlayer(Player player){

    }
}
