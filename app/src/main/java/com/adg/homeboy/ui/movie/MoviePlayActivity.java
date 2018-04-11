package com.adg.homeboy.ui.movie;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseActivity;
import com.adg.homeboy.repository.model.MovieDetail;
import com.adg.homeboy.repository.model.MovieModel;
import com.adg.homeboy.repository.net.RetrofitHelper;
import com.adg.homeboy.repository.response.MoviePlayResp;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.pili.pldroid.player.PLMediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuxiaoyu on 2017/12/24.
 */

public class MoviePlayActivity extends BaseActivity {

    private static final String TAG = MoviePlayActivity.class.getSimpleName();
    private PLMediaPlayer mMediaPlayer;
    SurfaceView mSurfaceView;

    private int mSurfaceWidth = 0;
    private int mSurfaceHeight = 0;
    private boolean mIsStopped = false;
    private long mLastUpdateStatTime = 0;
    private Toast mToast = null;

    GridLayoutManager layoutManager;
    EasyRecyclerView mRecyclerView;
    MovieDetailAdapter adapter;

    int id;

    String mVideoPath = "https://cdn.letv-cdn.com/20180320/6ClJpTRx/index.m3u8";
//    String mVideoPath = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        windowsParams();
        setContentView(R.layout.activity_movie_play);

        id = getIntent().getIntExtra("id", -1);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
//        setVideoHeight();
        mSurfaceView.getHolder().addCallback(mCallback);

        mSurfaceWidth = getResources().getDisplayMetrics().widthPixels;
        mSurfaceHeight = getResources().getDisplayMetrics().heightPixels;

        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        mRecyclerView = (EasyRecyclerView) findViewById(R.id.recycler);
        layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItem(position) instanceof MovieModel) {
                    return 1;
                } else if (adapter.getItem(position) instanceof MovieDetail) {
                    return 3;
                }
                return 0;
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        FrameLayout frameLayout = new FrameLayout(this);
        ProgressBar progressBar = new ProgressBar(this);
        FrameLayout.LayoutParams vlp = new FrameLayout.LayoutParams(200, 200);
        vlp.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(vlp);
        frameLayout.addView(progressBar);

        mRecyclerView.setProgressView(frameLayout);
        adapter = new MovieDetailAdapter(this);
        mRecyclerView.setAdapterWithProgress(adapter);

        getData();
    }

    private void getData() {
        Call<MoviePlayResp> detail = RetrofitHelper.getMoiveApi().getSingle(id);
        detail.enqueue(new Callback<MoviePlayResp>() {
            @Override
            public void onResponse(Call<MoviePlayResp> call, Response<MoviePlayResp> response) {
                if (response.isSuccessful()) {
                    List<Object> obs = new ArrayList<Object>();
                    obs.add(response.body().data.get(0));
                    adapter.addAll(obs);
                    mVideoPath = response.body().data.get(0).playurl;
                    prepare();
                }
            }

            @Override
            public void onFailure(Call<MoviePlayResp> call, Throwable t) {

            }
        });

//        MoviePlayResp.MoviePlayModel resp = AmazingStore.getMoviePlayResp().data;
//        List<Object> list = new ArrayList<>();
//        MovieDetail detail = new MovieDetail();
//        detail.name = resp.name;
//        detail.des = "简介：" + resp.des;
//        detail.length = resp.length;
//        detail.score = resp.score;
//        list.add(detail);
//        for (int i = 0; i < resp.list.size(); i++) {
//            list.add(resp.list.get(i));
//        }

//        adapter.addAll(list);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.abandonAudioFocus(null);
    }

    private void windowsParams() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
//            prepare();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // release();
            releaseWithoutStop();
        }
    };

    public void releaseWithoutStop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.setDisplay(null);
        }
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void prepare() {
        if (mMediaPlayer != null) {
            mMediaPlayer.setDisplay(mSurfaceView.getHolder());
            return;
        }

        try {
            mMediaPlayer = new PLMediaPlayer(this);
            mMediaPlayer.setDebugLoggingEnabled(true);
            mMediaPlayer.setLooping(getIntent().getBooleanExtra("loop", false));
            mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
            mMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
            mMediaPlayer.setOnErrorListener(mOnErrorListener);
            mMediaPlayer.setOnInfoListener(mOnInfoListener);
            mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
            mMediaPlayer.setDebugLoggingEnabled(true);
            // set replay if completed
            // mMediaPlayer.setLooping(true);
            mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            mMediaPlayer.setDataSource(mVideoPath);
            mMediaPlayer.setDisplay(mSurfaceView.getHolder());
            mMediaPlayer.prepareAsync();
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PLMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLMediaPlayer.OnVideoSizeChangedListener() {
        public void onVideoSizeChanged(PLMediaPlayer mp, int width, int height) {
            Log.i(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height);
            // resize the display window to fit the screen
            if (width != 0 && height != 0) {
                float ratioW = (float) width / (float) mSurfaceWidth;
                float ratioH = (float) height / (float) mSurfaceHeight;
                float ratio = Math.max(ratioW, ratioH);
                width = (int) Math.ceil((float) width / ratio);
                height = (int) Math.ceil((float) height / ratio);
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(width, height);
                layout.gravity = Gravity.CENTER;
                mSurfaceView.setLayoutParams(layout);
            }
        }
    };

    private PLMediaPlayer.OnPreparedListener mOnPreparedListener = new PLMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(PLMediaPlayer mp, int preparedTime) {
            Log.i(TAG, "On Prepared ! prepared time = " + preparedTime + " ms");
            mMediaPlayer.start();
            mIsStopped = false;
        }
    };

    private PLMediaPlayer.OnInfoListener mOnInfoListener = new PLMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(PLMediaPlayer mp, int what, int extra) {
            Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case PLMediaPlayer.MEDIA_INFO_BUFFERING_START:
//                    mLoadingView.setVisibility(View.VISIBLE);
                    break;
                case PLMediaPlayer.MEDIA_INFO_BUFFERING_END:
//                    mLoadingView.setVisibility(View.GONE);
                    break;
                case PLMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
//                    mLoadingView.setVisibility(View.GONE);
                    showToastTips("first video render time: " + extra + "ms");
                    break;
                case PLMediaPlayer.MEDIA_INFO_VIDEO_GOP_TIME:
                    Log.i(TAG, "Gop Time: " + extra);
                    break;
                case PLMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
//                    mLoadingView.setVisibility(View.GONE);
                    break;
                case PLMediaPlayer.MEDIA_INFO_SWITCHING_SW_DECODE:
                    Log.i(TAG, "Hardware decoding failure, switching software decoding!");
                    break;
                case PLMediaPlayer.MEDIA_INFO_METADATA:
                    Log.i(TAG, mMediaPlayer.getMetadata().toString());
                    break;
                case PLMediaPlayer.MEDIA_INFO_VIDEO_BITRATE:
                case PLMediaPlayer.MEDIA_INFO_VIDEO_FPS:
                    updateStatInfo();
                    break;
                case PLMediaPlayer.MEDIA_INFO_CONNECTED:
                    Log.i(TAG, "Connected !");
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private PLMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new PLMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(PLMediaPlayer mp, int percent) {
            Log.d(TAG, "onBufferingUpdate: " + percent + "%");
            long current = System.currentTimeMillis();
            if (current - mLastUpdateStatTime > 3000) {
                mLastUpdateStatTime = current;
                updateStatInfo();
            }
        }
    };

    /**
     * Listen the event of playing complete
     * For playing local file, it's called when reading the file EOF
     * For playing network stream, it's called when the buffered bytes played over
     * <p>
     * If setLooping(true) is called, the player will restart automatically
     * And ｀onCompletion｀ will not be called
     */
    private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer mp) {
            Log.d(TAG, "Play Completed !");
            showToastTips("Play Completed !");
            finish();
        }
    };

    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    /**
                     * SDK will do reconnecting automatically
                     */
                    showToastTips("IO Error !");
                    return false;
                case PLMediaPlayer.ERROR_CODE_OPEN_FAILED:
                    showToastTips("failed to open player !");
                    break;
                case PLMediaPlayer.ERROR_CODE_SEEK_FAILED:
                    showToastTips("failed to seek !");
                    break;
                default:
                    showToastTips("unknown error !");
                    break;
            }
            finish();
            return true;
        }
    };

    private void showToastTips(final String tips) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(MoviePlayActivity.this, tips, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    private void updateStatInfo() {
        long bitrate = mMediaPlayer.getVideoBitrate() / 1024;
        final String stat = bitrate + "kbps, " + mMediaPlayer.getVideoFps() + "fps";
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mStatInfoTextView.setText(stat);
                Log.i("updateStatInfo", stat);
            }
        });
    }
}
