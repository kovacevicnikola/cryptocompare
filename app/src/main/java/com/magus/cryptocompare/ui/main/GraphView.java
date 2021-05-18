package com.magus.cryptocompare.ui.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GraphView extends View {
    private Path path;
    private Paint paint;

    public GraphView(Context context) {
        super(context);
    }


    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void drawGraph(Path path, Paint paint) {
        this.paint = paint;
        this.path = path;
        invalidate();
    }

    @Override

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (path != null && paint != null) canvas.drawPath(path, paint);
    }

    class Point {
        float x, y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

    }


}