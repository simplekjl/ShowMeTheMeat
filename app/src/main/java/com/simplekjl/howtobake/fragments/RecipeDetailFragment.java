package com.simplekjl.howtobake.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simplekjl.howtobake.R;
import com.simplekjl.howtobake.RecipeVideoActivity;
import com.simplekjl.howtobake.adapters.IngredientAdapter;
import com.simplekjl.howtobake.adapters.StepAdapter;
import com.simplekjl.howtobake.databinding.FragmentRecipeDetailBinding;
import com.simplekjl.howtobake.models.Recipe;
import com.simplekjl.howtobake.utils.OnItemClickListener;

public class RecipeDetailFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String INGREDIENTS_LIST = "ingredients";
    private static final String STEPS_LIST = "steps";

    public static boolean isTablet = false;
    public static Recipe mRecipe;
    //adapters
    private IngredientAdapter mIngredientAdapter;
    private StepAdapter mStepAdapter;
    private OnFragmentInteractionListener mListener;
    private OnItemClickListener mClickAdapterListener;

    public RecipeDetailFragment(Recipe recipe) {
        // Required empty public constructor
        mRecipe = recipe;
    }
    public RecipeDetailFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentRecipeDetailBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_detail, container, false);
        View view = mBinding.getRoot();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mBinding.rvIngredients.setLayoutManager(linearLayoutManager);

        mIngredientAdapter = new IngredientAdapter(mRecipe.getIngredientsList());
        //adding into the Rcycler views
        mBinding.rvIngredients.setAdapter(mIngredientAdapter);
        mBinding.rvIngredients.setNestedScrollingEnabled(false);
        mClickAdapterListener = new OnItemClickListener<Integer>() {
            @Override
            public void onItemClick(Integer position) {
                Intent intent = new Intent(getActivity(), RecipeVideoActivity.class);
                getActivity().startActivity(intent);
            }
        };
        //steps adapter
        mStepAdapter = new StepAdapter(mRecipe.getStepsList(), mClickAdapterListener);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mBinding.rvSteps.setLayoutManager(linearLayoutManager2);
        mBinding.rvSteps.setAdapter(mStepAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
