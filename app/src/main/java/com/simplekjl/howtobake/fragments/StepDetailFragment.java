package com.simplekjl.howtobake.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.simplekjl.howtobake.R;
import com.simplekjl.howtobake.databinding.FragmentStepDetailBinding;
import com.simplekjl.howtobake.models.Step;

public class StepDetailFragment extends Fragment {

    public static final String STEP_KEY = "step";
    static final String POSITION_KEY = "position";
    private final String PLAYER_STATE = "playerState";
    private final String PLAYBACK_POSITION_KEY = "playback_position";
    //binding
    private FragmentStepDetailBinding mBinding;
    private int position;
    private SimpleExoPlayer player;
    private long playbackPosition = 0;
    private Configuration configuration;
    private Step mStep;
    private Uri mVideoUri;
    private boolean mPlayWhenReady = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION_KEY);
            mPlayWhenReady = savedInstanceState.getBoolean(PLAYER_STATE);
            mStep = savedInstanceState.getParcelable(STEP_KEY);
        } else if (bundle != null) {
            position = bundle.getInt(POSITION_KEY);
            mStep = bundle.getParcelable(STEP_KEY);
        }

        configuration = getActivity().getResources().getConfiguration();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_step_detail, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //access data from datasource

        if (position == 0) {
            mBinding.tvIngredientItem.setVisibility(View.GONE);
        } else {
            mBinding.tvIngredientItem.setText(mStep.getDescription());
        }
        //get URI of the preview video
        mVideoUri = getUriVideo(mStep);

        if (!mVideoUri.equals(Uri.EMPTY)) {
            initialisePlayer(mVideoUri);
        }

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && !mVideoUri.equals(Uri.EMPTY)) {
            mBinding.playerView.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT)
            );
            if (!RecipeDetailFragment.isTwoPanel) {
                fullScreen();
            }
        }
    }

    private Uri getUriVideo(Step mStep) {
        Uri videoUri;
        if (!TextUtils.isEmpty(mStep.getVideoURL())) {
            videoUri = Uri.parse(mStep.getVideoURL());
        } else {
            videoUri = Uri.parse(mStep.getThumbnailURL());
        }

        if (videoUri == null || videoUri.equals(Uri.EMPTY)) {
            mBinding.playerView.setVisibility(View.GONE);
        }
        return videoUri;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(STEP_KEY);
            Uri uri = getUriVideo(mStep);
            if (!uri.equals(Uri.EMPTY)) {
                initialisePlayer(uri);
            }
        }
        super.onViewStateRestored(savedInstanceState);
    }

    // region ExoPlayer
    private void initialisePlayer(Uri videoUri) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            mBinding.playerView.setPlayer(player);

            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(
                            getContext(),
                            Util.getUserAgent(getContext(),
                                    getResources().getString(R.string.app_name)));
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);

            player.prepare(mediaSource);

            if (getUserVisibleHint()) {
                player.setPlayWhenReady(mPlayWhenReady);
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
        mBinding.playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

        // Remove padding from layout
        mBinding.rootView.setPadding(0, 0, 0, 0);
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
    public void onResume() {
        super.onResume();
        if (mVideoUri != null) {
            initialisePlayer(mVideoUri);
            player.setPlayWhenReady(mPlayWhenReady);
            player.seekTo(playbackPosition);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer(); // Release exoPlayer
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            fragmentVisibility(true);
        } else {
            fragmentVisibility(false);
        }
    }

    private void fragmentVisibility(boolean visible) {
        if (player != null) {
            if (visible) {
                player.seekTo(playbackPosition);
                player.setPlayWhenReady(mPlayWhenReady);
            } else {
                updateStartPosition();
                player.setPlayWhenReady(mPlayWhenReady);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (player != null) {
            outState.putLong(PLAYBACK_POSITION_KEY, player.getCurrentPosition());
            boolean isPlayWhenReady = player.getPlayWhenReady();
            outState.putBoolean(PLAYER_STATE, isPlayWhenReady);
        }
        if (mStep != null) {
            outState.putParcelable(STEP_KEY, mStep);
            outState.putInt(POSITION_KEY, position);
        }
    }
    // endregion

}
