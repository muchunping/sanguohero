package cn.igame.sanguoheros.ui.fragment;

import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.model.Goods;
import cn.igame.sanguoheros.ui.HomeActivity;
import cn.igame.sanguoheros.ui.MainActivity;

/**
 * 用RecyclerView实现的行囊界面
 * Created by Administrator on 2015/11/5.
 */
public class InventoryFragment extends Fragment {

    //行囊要显示的物品列表
    private ArrayList<Goods> goodsList = new ArrayList<>();

    //行囊每一行显示物品数，用于定义行囊宽度
    private int spanCount;

    //行囊最大可见行数，用于定义行囊高度
    private int maxLineCount;

    //是否显示行囊四周的间距
    private boolean showEdgeSpace = true;

    //行囊中物品之间的间距
    private int gapSpace;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //定义行囊中物品之间的间距
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        gapSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, displayMetrics);

        //定义最大行数,列数
        maxLineCount = 7;
        spanCount = 6;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                if (goodsList.size() <= position) {
                    holder.imageView.setImageBitmap(null);
                    holder.amountView.setVisibility(View.GONE);
                    return;
                }
                final Goods goods = goodsList.get(position);
                holder.imageView.setImageResource(goods.getImageDescriptor());
//                if (goods.amount > 0) {
//                    holder.amountView.setText(String.valueOf(goods.amount));
//                    holder.amountView.setVisibility(View.VISIBLE);
//                } else {
//                    holder.amountView.setVisibility(View.GONE);
//                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getActivity() != null) {
                            ((HomeActivity) getActivity()).showGoodsDetail(goods);
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                int minCount = 5 * spanCount;
                //不足一行补足一行
                int remainder = goodsList.size() % spanCount;
                int count = remainder == 0 ? goodsList.size() : goodsList.size() + spanCount - remainder;
                return Math.max(count, minCount);
            }

        });

        return view;

    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList.clear();
        this.goodsList.addAll(goodsList);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView amountView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            amountView = view.findViewById(R.id.textView);
        }
    }
}
