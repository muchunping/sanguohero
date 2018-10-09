package cn.igame.sanguoheros.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.model.Goods;

public class GoodsDetailFragment extends Fragment {
    private TextView nameView;
    private ImageView avatarView;
    private TextView fieldsView;
    private TextView suitView;
    private TextView levelView;
    private TextView descriptionView;

    private Goods goods;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_goods_detail, container, false);
        nameView = root.findViewById(R.id.nameView);
        avatarView = root.findViewById(R.id.avatarView);
        fieldsView = root.findViewById(R.id.fieldsView);
        suitView = root.findViewById(R.id.suitView);
        levelView = root.findViewById(R.id.useLevelView);

        nameView.setText(goods.getName());

        return root;
    }

    public void setGoods(Goods goods){
        this.goods = goods;
    }
}
