package com.simplekjl.howtobake.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.simplekjl.howtobake.R;

public class StepDetailFragment extends Fragment {

    static final String POSITION_KEY = "position";
    private final String PLAYBACK_POSITION_KEY = "playback_position";
    private int position;
    private SimpleExoPlayer player;
    private long playbackPosition = 0;
    private Configuration configuration;
    private PlayerView mPlayer;
    private TextView mDescription;
    private LinearLayout mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_detail,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPlayer = view.findViewById(R.id.exo_play);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    // region ExoPlayer
    private void initialisePlayer(Uri videoUri) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            mPlayer.setPlayer(player);

            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(
                            getContext(),
                            Util.getUserAgent(getContext(),
                                    getResources().getString(R.string.app_name)));
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);

            player.prepare(mediaSource);

            if (getUserVisibleHint()) {
                player.setPlayWhenReady(true);
                player.seekTo(playbackPosition);
            }
        }
    }

    private void releasePlayer() {
        if (player != null) {
            updateStartPosition();
            player.stop();
            player.release();
            player = null;
        }
    }

    private void updateStartPosition() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
        }
    }


    private void fullScreen() {
        hideSystemUI();
        mPlayer.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

        // Remove padding from layout
        mRootView.setPadding(0, 0, 0, 0);
    }

    private void hideSystemUI() {

        // Hide the app bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        // Hide tab layout
        getActivity().findViewById(R.id.step_tab).setVisibility(View.GONE);

        // Hide system nav and go full immersive
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE);

        getActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    // endregion

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
