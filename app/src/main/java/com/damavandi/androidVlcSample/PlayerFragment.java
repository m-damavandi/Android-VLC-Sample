package com.damavandi.androidVlcSample;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.rasad.damavandi.myvlc.R;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

public class PlayerFragment extends Fragment implements IVLCVout.Callback {

    private static final String TAG = "PlayerFragment";
    private static final String SAMPLE_URL = "http://techslides.com/demos/sample-videos/small.mp4";

    private SurfaceView mVideoSurface = null;

    private LibVLC mLibVLC = null;
    private MediaPlayer mMediaPlayer = null;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ArrayList<String> args = new ArrayList<>();
        args.add("-vvv");
        mLibVLC = new LibVLC(context, args);
        mMediaPlayer = new MediaPlayer(mLibVLC);

        mVideoSurface = (SurfaceView) view.findViewById(R.id.video_surface);

        mMediaPlayer.setEventListener(new MediaPlayer.EventListener() {
            @Override
            public void onEvent(MediaPlayer.Event event) {
                switch (event.type){
                    case MediaPlayer.Event.Buffering:
                        Log.d(TAG, "onEvent: Buffering");
                        break;
                    case MediaPlayer.Event.EncounteredError:
                        Log.d(TAG, "onEvent: EncounteredError");
                        break;
                    case MediaPlayer.Event.EndReached:
                        Log.d(TAG, "onEvent: EndReached");
                        break;
                    case MediaPlayer.Event.ESAdded:
                        Log.d(TAG, "onEvent: ESAdded");
                        break;
                    case MediaPlayer.Event.ESDeleted:
                        Log.d(TAG, "onEvent: ESDeleted");
                        break;  case MediaPlayer.Event.MediaChanged:
                        Log.d(TAG, "onEvent: MediaChanged");
                        break;
                    case MediaPlayer.Event.Opening:
                        Log.d(TAG, "onEvent: Opening");
                        break;
                    case MediaPlayer.Event.PausableChanged:
                        Log.d(TAG, "onEvent: PausableChanged");
                        break;
                    case MediaPlayer.Event.Paused:
                        Log.d(TAG, "onEvent: Paused");
                        break;
                    case MediaPlayer.Event.Playing:
                        Log.d(TAG, "onEvent: Playing");
                        break;
                    case MediaPlayer.Event.PositionChanged:
//                        Log.d(TAG, "onEvent: PositionChanged");
                        break;
                    case MediaPlayer.Event.SeekableChanged:
                        Log.d(TAG, "onEvent: SeekableChanged");
                        break;
                    case MediaPlayer.Event.Stopped:
                        Log.d(TAG, "onEvent: Stopped");
                        break;
                    case MediaPlayer.Event.TimeChanged:
//                        Log.d(TAG, "onEvent: TimeChanged");
                        break;
                    case MediaPlayer.Event.Vout:
                        Log.d(TAG, "onEvent: Vout");
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mLibVLC.release();
    }

    @Override
    public void onStart() {
        super.onStart();
        final IVLCVout vlcVout = mMediaPlayer.getVLCVout();
        vlcVout.setVideoView(mVideoSurface);
        vlcVout.attachViews();
        mMediaPlayer.getVLCVout().addCallback(this);
        Media media = new Media(mLibVLC, Uri.parse(SAMPLE_URL));
        
        mMediaPlayer.setMedia(media);
        media.release();
        mMediaPlayer.play();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMediaPlayer.stop();
        mMediaPlayer.getVLCVout().detachViews();
        mMediaPlayer.getVLCVout().removeCallback(this);
    }

    @Override
    public void onNewLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {
    }

    @Override
    public void onSurfacesCreated(IVLCVout vlcVout) {
    }

    @Override
    public void onSurfacesDestroyed(IVLCVout vlcVout) {
    }
}
