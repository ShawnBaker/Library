// Copyright © 2016-2017 Shawn Baker using the MIT License.
package ca.frozen.testlibrary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;

import ca.frozen.library.classes.LogFile;
import ca.frozen.library.views.ZoomPanTextureView;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener
{
    private Menu mainMenu = null;
    private ZoomPanTextureView textureView;
    private LogFile log;

    //******************************************************************************
    // onCreate
    //******************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = new LogFile(this, "MainActivity", "TestLib");
        log.info("onCreate");

        // set the texture listener
        log.info("create ZoomPanTextureView");
        textureView = (ZoomPanTextureView)findViewById(R.id.zoom_pan_texture_view);
        textureView.setSurfaceTextureListener(this);
    }

    //******************************************************************************
    // onCreateOptionsMenu
    //******************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mainMenu = menu;
        return true;
    }

    //******************************************************************************
    // onOptionsItemSelected
    //******************************************************************************
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        // log files
        if (id == R.id.action_log_files)
        {
            Intent intent = new Intent(this, LogFilesActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //******************************************************************************
    // onSurfaceTextureAvailable
    //******************************************************************************
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height)
    {
        log.info("onSurfaceTextureAvailable");
        Canvas canvas = textureView.lockCanvas();
        Rect dst = new Rect(0, 0, textureView.getWidth(), textureView.getHeight());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.house);
        canvas.drawBitmap(bitmap, null, dst, null);
        textureView.unlockCanvasAndPost(canvas);
        textureView.setVideoSize(bitmap.getWidth(), bitmap.getHeight());
    }

    //******************************************************************************
    // onSurfaceTextureSizeChanged
    //******************************************************************************
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height)
    {
    }

    //******************************************************************************
    // onSurfaceTextureDestroyed
    //******************************************************************************
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture)
    {
        return true;
    }

    //******************************************************************************
    // onSurfaceTextureUpdated
    //******************************************************************************
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture)
    {
    }
}
