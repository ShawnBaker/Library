package ca.frozen.testlibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import ca.frozen.library.views.ZoomPanTextureView;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener
{
    private ZoomPanTextureView textureView;

    //******************************************************************************
    // onCreate
    //******************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the texture listener
        textureView = (ZoomPanTextureView)findViewById(R.id.zoom_pan_texture_view);
        textureView.setSurfaceTextureListener(this);
    }

    //******************************************************************************
    // onSurfaceTextureAvailable
    //******************************************************************************
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height)
    {
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
