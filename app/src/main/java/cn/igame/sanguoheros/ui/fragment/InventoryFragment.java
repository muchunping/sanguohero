package cn.igame.sanguoheros.ui.fragment;

import android.app.Fragment;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.util.Logger;

/**
 * 用RecyclerView实现的行囊界面
 * Created by Administrator on 2015/11/5.
 */
public class InventoryFragment extends Fragment {
    /**
     * 物品类，简易的，用于演示
     */
    public static class Goods {
        String name;       //物品名
        Bitmap image;      //物品图片(描述者)
        int amount;        //物品数量

        public Goods(String name, Bitmap image, int amount) {
            this.name = name;
            this.image = image;
            this.amount = amount;
        }
    }

    int[] imageList = new int[]{
            R.drawable.pic_2300050_l,
            R.drawable.pic_2300060_l,
            R.drawable.pic_2300070_l,
            R.drawable.pic_2300090_l,
            R.drawable.pic_2300100_l,
            R.drawable.pic_2300140_l,
            R.drawable.pic_2300160_l,
            R.drawable.pic_2300170_l
    };

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
        //添加一些物品
        Random random = new Random();
        for (int i = 0; i < 42; i++) {
            Goods goods = new Goods("", null, random.nextInt(10));
            goodsList.add(goods);
        }

        //定义行囊中物品之间的间距
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        gapSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, displayMetrics);

        //定义最大行数,列数
        maxLineCount = 7;
        spanCount = 5;

        //解析NPC列表
        XmlResourceParser xrp = getResources().getXml(R.xml.system_actor);
        ArrayList<SystemActor> actorList = new ArrayList<>();
        try {
            int eventType;
            while ((eventType = xrp.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String s = xrp.getName();
                    if (s.equals("item")) {
                        SystemActor systemActor = new SystemActor();
                        systemActor.createFromXml(xrp);
                        actorList.add(systemActor);
                    }
                }
            }

            for (SystemActor actor : actorList) {
                Logger.dL(Logger.LOG_TAG, actor.toString());
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),
                spanCount, GridLayoutManager.VERTICAL, false) {

            //让RecyclerView宽高适应内容
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                                  int widthSpec, int heightSpec) {
                final int widthMode = View.MeasureSpec.getMode(widthSpec);
                final int heightMode = View.MeasureSpec.getMode(heightSpec);
                final int widthSize = View.MeasureSpec.getSize(widthSpec);
                final int heightSize = View.MeasureSpec.getSize(heightSpec);

                int width;
                int height;

                View itemView = recycler.getViewForPosition(0);
                int childWith = 0;
                int childHeight = 0;
                if (itemView != null) {
                    int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    itemView.measure(spec, spec);
                    childWith = itemView.getMeasuredWidth();
                    childHeight = itemView.getMeasuredHeight();
                }

                switch (widthMode) {
                    case View.MeasureSpec.EXACTLY:
                        width = widthSize;
                        break;
                    case View.MeasureSpec.AT_MOST:
                    case View.MeasureSpec.UNSPECIFIED:
                    default:
                        width = childWith * getSpanCount() + gapSpace * (getSpanCount() + 1)
                                + getPaddingLeft() + getPaddingRight();
                        break;
                }

                switch (heightMode) {
                    case View.MeasureSpec.EXACTLY:
                        height = heightSize;
                        break;
                    case View.MeasureSpec.AT_MOST:
                    case View.MeasureSpec.UNSPECIFIED:
                    default:
                        int line = getItemCount() / getSpanCount() + (getItemCount() % getSpanCount() == 0 ? 0 : 1);
                        int maxLine = (heightSize - getPaddingTop() - getPaddingBottom() + gapSpace) / (childHeight + gapSpace);
                        maxLine = maxLineCount == 0 ? maxLine : maxLineCount;
                        line = line > maxLine ? maxLine : line;
                        height = childHeight * line + gapSpace * (line + 1)
                                + getPaddingBottom() + getPaddingTop();
                        break;
                }

                setMeasuredDimension(width, height);
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                int position = parent.getChildAdapterPosition(view); // item position
                GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
                int spanCount = layoutManager.getSpanCount();
                int column = position % spanCount; // item column

                //由于默认情况下，RecyclerView会把多余的宽度平均分配到每个item的后面
                if (showEdgeSpace) {
                    //所以在最左边加一个间距，间距的空间从每个item后面的间距中抽取
                    outRect.left = gapSpace - column * gapSpace / spanCount;
                    outRect.right = (column + 1) * gapSpace / spanCount;

                    if (position < spanCount) { // top edge
                        outRect.top = gapSpace;
                    }
                    outRect.bottom = gapSpace; // item bottom
                } else {
                    //所以把最右边的间距去掉，间距的空间分配到每个item后面
                    outRect.left = column * gapSpace / spanCount;
                    outRect.right = gapSpace - (column + 1) * gapSpace / spanCount;
                    if (position >= spanCount) {
                        outRect.top = gapSpace; // item top
                    }
                }
            }
        });
        recyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            Random random = new Random();

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
                Goods goods = goodsList.get(position);
                holder.imageView.setImageResource(imageList[random.nextInt(imageList.length)]);
                if (goods.amount > 0) {
                    holder.amountView.setText(String.valueOf(goods.amount));
                    holder.amountView.setVisibility(View.VISIBLE);
                } else {
                    holder.amountView.setVisibility(View.GONE);
                }
            }

            @Override
            public int getItemCount() {
                //不足一行补足一行
                int remainder = goodsList.size() % spanCount;
                return remainder == 0 ? goodsList.size() : goodsList.size() + spanCount - remainder;
            }

        });

        return view;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView amountView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            amountView = (TextView) view.findViewById(R.id.textView);
        }
    }

    class SystemActor {
        String name;
        String action;

        public void createFromXml(XmlResourceParser xrp) {
            name = getResources().getString(xrp.getAttributeResourceValue(null, "name", R.string.app_name));
            action = getResources().getString(xrp.getAttributeResourceValue(null, "action", R.string.app_name));
        }

        @Override
        public String toString() {
            return "SystemActor{" +
                    "name='" + name + '\'' +
                    ", action='" + action + '\'' +
                    '}';
        }
    }
}
