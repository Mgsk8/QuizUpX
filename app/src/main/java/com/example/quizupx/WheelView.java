package com.example.quizupx;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class WheelView extends View {

    private String[] items = {"Cultura", "Ciencia", "Música", "Series", "F/V", "Sorpresa"};
    private int[] colors = {Color.parseColor("#FF7043"), Color.parseColor("#42A5F5"),
            Color.parseColor("#66BB6A"), Color.parseColor("#AB47BC"),
            Color.parseColor("#26C6DA"), Color.parseColor("#FFD600")};

    private Paint paintSegment = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF bounds = new RectF();
    private float rotationAngle = 0f;

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(28f);
        paintText.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float w = getWidth();
        float h = getHeight();
        float radius = Math.min(w, h) / 2f;
        bounds.set(w/2 - radius, h/2 - radius, w/2 + radius, h/2 + radius);

        int count = items.length;
        float sweep = 360f / count;

        canvas.save();
        canvas.rotate(rotationAngle, w / 2, h / 2);

        for (int i = 0; i < count; i++) {
            paintSegment.setColor(colors[i % colors.length]);
            canvas.drawArc(bounds, i * sweep, sweep, true, paintSegment);

            float angle = (i + 0.5f) * sweep;
            float textX = (float)(w / 2 + radius * 0.6 * Math.cos(Math.toRadians(angle)));
            float textY = (float)(h / 2 + radius * 0.6 * Math.sin(Math.toRadians(angle)));
            canvas.drawText(items[i], textX, textY, paintText);
        }

        canvas.restore();
    }

    public void spinTo(int index) {
        int count = items.length;
        float sweep = 360f / count;
        float targetAngle = 360f * 5 + (count - index) * sweep + sweep / 2;

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "rotationAngle", rotationAngle, targetAngle);
        animator.setDuration(4000);
        animator.start();
    }

    public void setRotationAngle(float angle) {
        this.rotationAngle = angle % 3600;
        invalidate();
    }

    public float getRotationAngle() {
        return rotationAngle;
    }
}
