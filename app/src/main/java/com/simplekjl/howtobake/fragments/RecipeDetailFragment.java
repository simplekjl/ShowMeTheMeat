package com.simplekjl.howtobake.fragments;

import android.content.Context;
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
import com.simplekjl.howtobake.adapters.IngredientAdapter;
import com.simplekjl.howtobake.adapters.StepAdapter;
import com.simplekjl.howtobake.databinding.FragmentRecipeDetailBinding;
import com.simplekjl.howtobake.models.Ingredient;
import com.simplekjl.howtobake.models.Step;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String INGREDIENTS_LIST = "ingredients";
    private static final String STEPS_LIST = "steps";

    private List<Ingredient> mIngredientsList;
    private List<Step> mStepsList;
    //adapters
    private IngredientAdapter mIngredientAdapter;
    private StepAdapter mStepAdapter;

    private OnFragmentInteractionListener mListener;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Ingredient List.
     * @param param2 step List.
     * @return A new instance of fragment RecipeDetailFragment.
     */

    public static RecipeDetailFragment newInstance(String param1, String param2) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putString(INGREDIENTS_LIST, param1);
        args.putString(STEPS_LIST, param2);
        fragment.setArguments(args);
        return fragment;
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

        mIngredientAdapter = new IngredientAdapter(mIngredientsList);
        //adding into the Rcycler views
        mBinding.rvIngredients.setAdapter(mIngredientAdapter);
        mBinding.rvIngredients.setNestedScrollingEnabled(false);
        //steps adapter
        mStepAdapter = new StepAdapter(mStepsList);
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

    public void setIngredientsList(List<Ingredient> mIngredientsList) {
        this.mIngredientsList = mIngredientsList;
    }

    public void setStepsList(List<Step> mStepsList) {
        this.mStepsList = mStepsList;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
