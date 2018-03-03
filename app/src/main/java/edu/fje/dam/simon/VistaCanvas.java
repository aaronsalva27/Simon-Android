package edu.fje.dam.simon;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by arand on 26/02/2018.
 */

public class VistaCanvas extends View
{
    private float x;
    private float y;
    private String cadena;

    private int color;
    public VistaCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.VistaCanvas,
                0, 0);

        try {
            x = a.getFloat(R.styleable.VistaCanvas_x, 0);
            cadena = a.getString(R.styleable.VistaCanvas_cadena);

        } finally {
            a.recycle();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        canvas.drawRect(x, y, 1250, 350, paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        //paint.setTextAlign(Paint.Align align);
        canvas.drawText(cadena, 400, 255, paint);

    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
