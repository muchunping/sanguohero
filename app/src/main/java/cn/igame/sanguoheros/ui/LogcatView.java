package cn.igame.sanguoheros.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.TypedValue;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * !Created by muchunping on 2018/8/27.
 */
public class LogcatView extends android.support.v7.widget.AppCompatTextView {
    /*
     * 最大日志数，超过该值，会丢弃日志内容队列中最前面的日志
     */
    private static final int MAX_NUM_LOG = 20;
    /*
     * 用于记录日志内容的队列
     */
    private Queue<CharSequence> logQueue = new ArrayDeque<>(MAX_NUM_LOG);

    /*
     * 偏移量，用来修正textView底部比较大的空隙
     */
    private int offset;

    public LogcatView(Context context) {
        this(context, null);
    }

    public LogcatView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 16842884);
    }

    public LogcatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setMovementMethod(ScrollingMovementMethod.getInstance());
        offset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void append(CharSequence text, int start, int end) {
        //移除日志后的行数变化
        int linesChange = 0;
        //如果日志队列已达最大值，则移除最开始的一条日志
        if (logQueue.size() >= MAX_NUM_LOG) {
            int lines = getLineCount();
            CharSequence remove = logQueue.remove();
            Editable editable = getEditableText();
            editable.delete(0, remove.length());
            linesChange = getLineCount() - lines;
        }
        logQueue.add(text);
        //如果当前处于文本最底部，则当添加新日志时依然滚动到最底部
        boolean canScroll = true;
        if (getLayout() != null) {
            canScroll = getScrollY() >= getLayout().getLineTop(getLineCount()) - getMeasuredHeight() - offset;
        }

        super.append(text, start, end);

        if (getLayout() != null) {
            if (canScroll) {
                //使滚动到最底部
                int scrollValue = getLayout().getLineTop(getLineCount()) - getMeasuredHeight() - offset;
                if (scrollValue > 0) {
                    scrollTo(0, scrollValue);
                } else {
                    scrollTo(0, 0);
                }
            } else if (logQueue.size() >= MAX_NUM_LOG) {
                //使当前内容不随着日志到移除而滚动
                int scrollY = getScrollY() + getLineHeight() * linesChange;
                scrollTo(0, scrollY < 0 ? 0 : scrollY);
            }
        }
    }

}
