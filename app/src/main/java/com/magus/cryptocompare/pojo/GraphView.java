package com.magus.cryptocompare.pojo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GraphView extends View {
    private PathDataModel path;
    private Paint paint;

    public GraphView(Context context) {
        super(context);
    }


    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void drawGraph(PathDataModel path, Paint paint) {
        this.paint = paint;
        this.path = path;
        invalidate();
    }

    @Override

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (path != null && paint != null) {
            canvas.drawPath(path.getPath(), paint);
            for (int i = 0; i < path.getBackground().getChartHeight() / path.getBackground().getPixelInterval(); i++) {
                canvas.drawLine(0, i * path.getBackground().getPixelInterval(), path.getBackground().getChartWidth(), i * path.getBackground().getPixelInterval(), path.getBackground().getPaint());
            }
            // draw vertical lines
            for (int i = 0; i < path.getBackground().getChartWidth() / path.getBackground().getPixelInterval(); i++) {
                canvas.drawLine(i * path.getBackground().getPixelInterval(), 0, i * path.getBackground().getPixelInterval(), path.getBackground().getChartHeight(), path.getBackground().getPaint());
            }
            canvas.drawRect(path.getBackground().getRect(), path.getBackground().getPaint());
        }


    }
}