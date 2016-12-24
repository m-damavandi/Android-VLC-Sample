package com.damavandi.androidVlcSample;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.rasad.damavandi.myvlc.R;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IVLCVout.Callback {
    private static final String TAG = "JavaActivity";
    private static final String SAMPLE_URL = "http://192.168.1.203:80/note5.mp4";
//    private static final String SAMPLE_URL1 = "http://192.168.1.203:80/acer.mov";
    private static final int SURFACE_BEST_FIT = 0;
    private static final int SURFACE_FIT_HORIZONTAL = 1;
    private static final int SURFACE_FIT_VERTICAL = 2;
    private static final int SURFACE_FILL = 3;
    private static final int SURFACE_16_9 = 4;
    private static final int SURFACE_4_3 = 5;
    private static final int SURFACE_ORIGINAL = 6;
    private static int CURRENT_SIZE = SURFACE_BEST_FIT;

    private FrameLayout mVideoSurfaceFrame = null;
//    private FrameLayout mVideoSurfaceFrame1 = null;
    private SurfaceView mVideoSurface = null;
//    private SurfaceView mVideoSurface1 = null;

    private final Handler mHandler = new Handler();

    private LibVLC mLibVLC = null;
    private MediaPlayer mMediaPlayer = null;
//    private MediaPlayer mMediaPlayer2 = null;
    private int mVideoHeight = 0;
    private int mVideoWidth = 0;
    private int mVideoVisibleHeight = 0;
    private int mVideoVisibleWidth = 0;
    private int mVideoSarNum = 0;
    private int mVideoSarDen = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<String> args = new ArrayList<>();
        args.add("-vvv");
        mLibVLC = new LibVLC(this, args);
        mMediaPlayer = new MediaPlayer(mLibVLC);
//        mMediaPlayer2 = new MediaPlayer(mLibVLC);

        mVideoSurfaceFrame = (FrameLayout) findViewById(R.id.video_surface_frame);
//        mVideoSurfaceFrame1 = (FrameLayout) findViewById(R.id.video_surface_frame1);
        mVideoSurface = (SurfaceView) findViewById(R.id.video_surface);
//        mVideoSurface1 = (SurfaceView) findViewById(R.id.video_surface1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mLibVLC.release();
    }

    @Override
    protected void onStart() {
        super.onStart();

        final IVLCVout vlcVout = mMediaPlayer.getVLCVout();
//        final IVLCVout vlcVout2 = mMediaPlayer2.getVLCVout();
        vlcVout.setVideoView(mVideoSurface);
//        vlcVout2.setVideoView(mVideoSurface1);
        vlcVout.attachViews();
//        vlcVout2.attachViews();
        mMediaPlayer.getVLCVout().addCallback(this);
//        mMediaPlayer2.getVLCVout().addCallback(this);

        Media media = new Media(mLibVLC, Uri.parse(SAMPLE_URL));
//        Media media1 = new Media(mLibVLC, Uri.parse(SAMPLE_URL1));
        mMediaPlayer.setMedia(media);
//        mMediaPlayer2.setMedia(media1);
        media.release();
        mMediaPlayer.play();
//        mMediaPlayer2.play();
//        mMediaPlayer.setRate(.5f);

    }

    @Override
    protected void onStop() {
        super.onStop();

        mMediaPlayer.stop();
        mMediaPlayer.getVLCVout().detachViews();
        mMediaPlayer.getVLCVout().removeCallback(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onNewLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {
        mVideoWidth = width;
        mVideoHeight = height;
        mVideoVisibleWidth = visibleWidth;
        mVideoVisibleHeight = visibleHeight;
        mVideoSarNum = sarNum;
        mVideoSarDen = sarDen;
    }

    @Override
    public void onSurfacesCreated(IVLCVout vlcVout) {
    }

    @Override
    public void onSurfacesDestroyed(IVLCVout vlcVout) {
    }
}
