package com.simplekjl.howtobake.utils;

import com.simplekjl.howtobake.R;

import java.util.ArrayList;
import java.util.List;

public class BakingImagesAssets {
    private static final List<Integer> backgrounds = new ArrayList<Integer>() {{
        add(R.drawable.bakery);
        add(R.drawable.heart);
        add(R.drawable.eggs);
        add(R.drawable.chocolate);
        add(R.drawable.eggs2);
        add(R.drawable.heart);
    }};

    //getter

    public static List<Integer> getBackgrounds() {
        return backgrounds;
    }
}
