package com.mu.sanguo.heroes.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import cn.igame.sanguoheros.R;
import com.mu.sanguo.heroes.model.Equipment;
import com.mu.sanguo.heroes.model.FightProperty;

/**
 * 装备信息界面
 * Created by Administrator on 2015/12/17.
 */
public class EquipmentFragment extends DialogFragment {
    private TextView nameView;
    private TextView levelView;
    private TextView typeView;
    private TextView rareView;
    private TextView qualityView;
    private ImageView imageView;
    private TextView descriptionView;
    private TextView basePropertyView;
    private TextView extraPropertyView;

    private Equipment equipment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.custom_dialog_style);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragemnt_equipment, container, false);
        this.nameView = (TextView) view.findViewById(R.id.equipmentNameView);
        this.levelView = (TextView) view.findViewById(R.id.equipmentLevelView);
        this.typeView = (TextView) view.findViewById(R.id.equipmentTypeView);
        this.rareView = (TextView) view.findViewById(R.id.equipmentRareView);
        this.qualityView = (TextView) view.findViewById(R.id.equipmentQualityView);
        this.imageView = (ImageView) view.findViewById(R.id.equipmentImageView);
        this.descriptionView = (TextView) view.findViewById(R.id.equipmentDescriptionView);
        this.basePropertyView = (TextView) view.findViewById(R.id.equipmentBasePropertyView);
        this.extraPropertyView = (TextView) view.findViewById(R.id.equipmentExtraPropertyView);
        fillData();
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = getResources().getDimensionPixelSize(R.dimen.px_496);
            dialog.getWindow().setAttributes(params);
        }
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;

    }

    private void fillData() {
        if (equipment == null) return;
        this.nameView.setText(equipment.getName());
        this.imageView.setImageResource(equipment.getImageDescriptor());
        this.descriptionView.setText(equipment.getDescription());
        this.levelView.setText(String.format("等级：%d", equipment.getLevel()));
        this.typeView.setText(String.format("类型：%s", equipment.getType()));
        StringBuilder builder = new StringBuilder("珍品：");
        for (int i = 0; i < equipment.getRareStar(); i++) {
            builder.append("★");
        }
        this.rareView.setText(builder.toString());
        builder = new StringBuilder();
        FightProperty fightProperty = equipment.getFightProperty();
        if (fightProperty.attackPoint != 0) {
            builder.append(fightProperty.attackPoint > 0 ? "+ " : "- ")
                    .append(fightProperty.attackPoint)
                    .append("攻击点数");
        }
        if (fightProperty.defensePoint != 0) {
            if (builder.length() > 0)
                builder.append("\n");

            builder.append(fightProperty.defensePoint > 0 ? "+ " : "- ")
                    .append(fightProperty.defensePoint)
                    .append("防御点数");
        }
        if (fightProperty.healthPoint != 0) {
            if (builder.length() > 0)
                builder.append("\n");

            builder.append(fightProperty.healthPoint > 0 ? "+ " : "- ")
                    .append(fightProperty.healthPoint)
                    .append("生命点数");
        }
        if (fightProperty.manaPoint != 0) {
            if (builder.length() > 0)
                builder.append("\n");

            builder.append(fightProperty.manaPoint > 0 ? "+ " : "- ")
                    .append(fightProperty.manaPoint)
                    .append("魔法点数");
        }
        if (fightProperty.speedPoint != 0) {
            if (builder.length() > 0)
                builder.append("\n");

            builder.append(fightProperty.speedPoint > 0 ? "+ " : "- ")
                    .append(fightProperty.speedPoint)
                    .append("速度点数");
        }
        this.basePropertyView.setText(builder.toString());
    }
}
