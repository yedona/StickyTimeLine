package com.yedona.stickytimeline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author: yedona
 * @date: 2021/2/22 14:37
 * @description
 */
public class StickyTimeLineItemDecoration extends RecyclerView.ItemDecoration {

    private final List<DateBean> list;

    Context context;

    Paint blackPaint = new Paint();

    Paint redPaint = new Paint();

    //时间线的宽度
    private int timeLineWidth = 200;

    public StickyTimeLineItemDecoration(Context context, List<DateBean> list) {
        this.context = context;
        this.list = list;

        blackPaint.setTextSize(28);
        blackPaint.setStrokeWidth(5);
        blackPaint.setColor(Color.BLACK);


        redPaint.setTextSize(28);
        redPaint.setStrokeWidth(5);
        redPaint.setColor(Color.RED);


    }


    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);


        if (parent.getLayoutManager() == null || !(parent.getLayoutManager() instanceof GridLayoutManager)
                || ((GridLayoutManager) parent.getLayoutManager()).getSpanCount() == 0) {
            return;
        }

        int childCount = parent.getChildCount();


        Rect mBounds = new Rect();

        for (int i = 0; i < childCount; i++) {


            View childView = parent.getChildAt(i);

            parent.getDecoratedBoundsWithMargins(childView, mBounds);
            GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();

            int po = parent.getChildLayoutPosition(childView);

            int spanIndex = gridLayoutManager.getSpanSizeLookup().getSpanIndex(po, gridLayoutManager.getSpanCount());

            float measuredHeight = childView.getMeasuredHeight() * 1.0f;


            float y = measuredHeight / 2;

            if (spanIndex == 0) {

                if (po == 0) {
                    //第一个直接绘制标题
                    c.drawText(list.get(po).date, 0, y + mBounds.top, blackPaint);

                } else if (list.get(po).getGroupTag().equals(list.get(po - 1).getGroupTag())) {
                    //如果和上一个一样，就绘制时间线
                    c.drawLine(100, mBounds.top, 100, mBounds.bottom, blackPaint);

                } else {
                    //如果不一样，就会绘制标题
                    c.drawText(list.get(po).date, 0, y + mBounds.top, blackPaint);

                }
            }
        }
    }


    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();


        if (layoutManager != null) {
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (parent.findViewHolderForLayoutPosition(firstVisibleItemPosition) != null) {

                View firstChildView = parent.findViewHolderForLayoutPosition(firstVisibleItemPosition).itemView;

                float height = firstChildView.getMeasuredHeight();

                float height1 = height;
                float height2 = height / 2;

                //获取当前第一个可见item的滑动偏移量，来确定悬浮的位置
                for (int i = 1; i < layoutManager.getSpanCount() + 1; i++) {
                    if (i + firstVisibleItemPosition < list.size()) {
                        if (!list.get(i + firstVisibleItemPosition).getGroupTag().equals(list.get(firstVisibleItemPosition).getGroupTag())) {
                            height1 = height + firstChildView.getTop();
                            height2 = height / 2 + firstChildView.getTop();
                            break;
                        }
                    }
                }

                //画个颜色区分 悬浮的那个
                c.drawRect(0, 0, timeLineWidth, height1, redPaint);
                c.drawText(list.get(firstVisibleItemPosition).date, 0, height2, blackPaint);

            }

        }


    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getLayoutManager() == null || !(parent.getLayoutManager() instanceof GridLayoutManager)
                || ((GridLayoutManager) parent.getLayoutManager()).getSpanCount() == 0) {
            return;
        }

        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();

        //重新设置每个item的大小，让其保持同等宽度
        int width = ((parent.getMeasuredWidth() - timeLineWidth) / gridLayoutManager.getSpanCount());
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);


        int po = parent.getChildLayoutPosition(view);
        int spanIndex = gridLayoutManager.getSpanSizeLookup().getSpanIndex(po, gridLayoutManager.getSpanCount());

        //设置每个item的偏移量
        for (int i = 0; i < gridLayoutManager.getSpanCount(); i++) {

            if (i == 0) {
                //第一列向右偏移  timeLineWidth 的大小
                outRect.set(timeLineWidth, 0, 0, 0);
            } else {
                //剩下的控件按照spanIndex 偏移相应的距离
                outRect.set((int) (timeLineWidth * 1.0f / gridLayoutManager.getSpanCount() * (gridLayoutManager.getSpanCount() - spanIndex)),
                        0, 0, 0);
            }
        }

    }


}
