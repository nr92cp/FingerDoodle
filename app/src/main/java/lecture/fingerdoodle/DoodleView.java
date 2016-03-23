package lecture.fingerdoodle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

public class DoodleView extends View {

    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private int paintColor = Color.BLACK;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;

   public DoodleView(Context context, AttributeSet attrs){
       super(context, attrs);
       initDoodle();
   }

    private void initDoodle(){
        drawPath = new Path();
        drawPaint = new Paint();

        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setAlpha(255);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public int getColor() {
        return drawPaint.getColor();
    }

    public void setColor() {
        invalidate();

        if (getColor() == Color.BLACK) {
            drawPaint.setColor(Color.BLUE);
            Toast.makeText(this.getContext(), "Now Doodling in Blue", Toast.LENGTH_SHORT).show();
        } else if (getColor() == Color.BLUE) {
            drawPaint.setColor(Color.MAGENTA);
            Toast.makeText(this.getContext(), "Now Doodling in Magenta", Toast.LENGTH_SHORT).show();
        } else if (getColor() == Color.MAGENTA) {
            drawPaint.setColor(Color.YELLOW);
            Toast.makeText(this.getContext(), "Now Doodling in Yellow", Toast.LENGTH_SHORT).show();
        } else if (getColor() == Color.YELLOW) {
            drawPaint.setColor(Color.RED);
            Toast.makeText(this.getContext(), "Now Doodling in Red", Toast.LENGTH_SHORT).show();
        } else if (getColor() == Color.RED) {
            drawPaint.setColor(Color.BLACK);
            Toast.makeText(this.getContext(), "Now Doodling in Black", Toast.LENGTH_SHORT).show();
        } else {
            drawPaint.setColor(Color.BLACK);
            Toast.makeText(this.getContext(), "Now Doodling in Black", Toast.LENGTH_SHORT).show();
        }
    }

    public float getSize() {
        return drawPaint.getStrokeWidth();
    }

    public void setSize() {
        invalidate();

        if (getSize() == 20) {
            drawPaint.setStrokeWidth(40);
            Toast.makeText(this.getContext(), "Now Doodling with Large Lines", Toast.LENGTH_SHORT).show();
        } else if (getSize() == 40) {
            drawPaint.setStrokeWidth(60);
            Toast.makeText(this.getContext(), "Now Doodling with Extra Large Lines", Toast.LENGTH_SHORT).show();
        } else if (getSize() == 60) {
            drawPaint.setStrokeWidth(10);
            Toast.makeText(this.getContext(), "Now Doodling with Small Lines", Toast.LENGTH_SHORT).show();
        } else if (getSize() == 10) {
            drawPaint.setStrokeWidth(20);
            Toast.makeText(this.getContext(), "Now Doodling with Medium Lines", Toast.LENGTH_SHORT).show();
        } else {
            drawPaint.setStrokeWidth(20);
            Toast.makeText(this.getContext(), "Now Doodling with Medium Lines", Toast.LENGTH_SHORT).show();
        }
    }

    public float getCurrAlpha() {
        return drawPaint.getAlpha();
    }

    public void setCurrAlpha() {
        if (getCurrAlpha() == 255) {
            drawPaint.setAlpha(100);
            Toast.makeText(this.getContext(), "Now Doodling with High Opacity", Toast.LENGTH_SHORT).show();
        } else if (getCurrAlpha() == 100) {
            drawPaint.setAlpha(50);
            Toast.makeText(this.getContext(), "Now Doodling with Medium Opacity", Toast.LENGTH_SHORT).show();
        } else if (getCurrAlpha() == 50) {
            drawPaint.setAlpha(10);
            Toast.makeText(this.getContext(), "Now Doodling with Low Opacity", Toast.LENGTH_SHORT).show();
        } else if (getCurrAlpha() == 10) {
            drawPaint.setAlpha(255);
            Toast.makeText(this.getContext(), "Now Doodling with Complete Opacity", Toast.LENGTH_SHORT).show();
        } else {
            drawPaint.setAlpha(255);
            Toast.makeText(this.getContext(), "Now Doodling with Complete Opacity", Toast.LENGTH_SHORT).show();
        }
    }

    public void clearCanvas() {
        invalidate();
        drawCanvas.drawColor(Color.WHITE);
        Toast.makeText(this.getContext(), "Doodler Cleared", Toast.LENGTH_SHORT).show();
    }
}
