package lecture.fingerdoodle;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.UUID;

public class DoodleActivity extends Activity {

    private DoodleView doodleView;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle);

        doodleView = (DoodleView)findViewById(R.id.doodle);

        Button clearButton = (Button)findViewById(R.id.buttonClear);
        View.OnClickListener toClear = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.clearCanvas();
            }
        };
        clearButton.setOnClickListener(toClear);

        Button colorButton = (Button)findViewById(R.id.buttonColor);
        View.OnClickListener toColor = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.setColor();
            }
        };
        colorButton.setOnClickListener(toColor);

        Button sizeButton = (Button)findViewById(R.id.buttonBrushSize);
        View.OnClickListener toSize = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.setSize();
            }
        };
        sizeButton.setOnClickListener(toSize);

        Button opacityButton = (Button)findViewById(R.id.buttonOpacity);
        View.OnClickListener toOpacity = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.setCurrAlpha();
            }
        };
        opacityButton.setOnClickListener(toOpacity);

        Button saveButton = (Button)findViewById(R.id.buttonSave);
        View.OnClickListener toSave = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyStoragePermissions(DoodleActivity.this);
                MediaStore.Images.Media.insertImage(DoodleActivity.this.getContentResolver(),
                        doodleView.getDrawingCache(), UUID.randomUUID().toString() + ".png", "doodle");
                DoodleActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                Log.w("Doodler", "after add image");
                Toast.makeText(DoodleActivity.this.getApplicationContext(), "Doodle Saved to Gallery", Toast.LENGTH_SHORT).show();
            }
        };
        saveButton.setOnClickListener(toSave);


    }

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
            Log.w("Doodler", "made it here");
        }
    }
}
