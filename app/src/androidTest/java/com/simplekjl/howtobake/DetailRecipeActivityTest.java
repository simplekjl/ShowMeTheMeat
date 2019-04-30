package com.simplekjl.howtobake;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.google.gson.Gson;
import com.simplekjl.howtobake.models.Recipe;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4ClassRunner.class)
public class DetailRecipeActivityTest {

    public static String RECIPE_KEY = "recipe";
    private final String recipeString = "{\"id\":1,\"name\":\"Nutella Pie\",\"ingredients\":" +
            "[{\"quantity\":2,\"measure\":\"CUP\",\"ingredient\":\"Graham Cracker crumbs\"}," +
            "{\"quantity\":6,\"measure\":\"TBLSP\",\"ingredient\":\"unsalted butter, melted\"}," +
            "{\"quantity\":0.5,\"measure\":\"CUP\",\"ingredient\":\"granulated sugar\"}," +
            "{\"quantity\":1.5,\"measure\":\"TSP\",\"ingredient\":\"salt\"},{\"quantity\":5,\"measure\":\"TBLSP\",\"ingredient\":\"vanilla\"},{\"quantity\":1,\"measure\":\"K\",\"ingredient\":\"Nutella or other chocolate-hazelnut spread\"},{\"quantity\":500,\"measure\":\"G\",\"ingredient\":\"Mascapone Cheese(room temperature)\"},{\"quantity\":1,\"measure\":\"CUP\",\"ingredient\":\"heavy cream(cold)\"},{\"quantity\":4,\"measure\":\"OZ\",\"ingredient\":\"cream cheese(softened)\"}],\"steps\":[{\"id\":0,\"shortDescription\":\"Recipe Introduction\",\"description\":\"Recipe Introduction\",\"videoURL\":\"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4\",\"thumbnailURL\":\"\"},{\"id\":1,\"shortDescription\":\"Starting prep\",\"description\":\"1. Preheat the oven to 350\\u00b0F. Butter a 9\\\" deep dish pie pan.\",\"videoURL\":\"\",\"thumbnailURL\":\"\"},{\"id\":2,\"shortDescription\":\"Prep the cookie crust.\",\"description\":\"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.\",\"videoURL\":\"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4\",\"thumbnailURL\":\"\"},{\"id\":3,\"shortDescription\":\"Press the crust into baking form.\",\"description\":\"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.\",\"videoURL\":\"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4\",\"thumbnailURL\":\"\"},{\"id\":4,\"shortDescription\":\"Start filling prep\",\"description\":\"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.\",\"videoURL\":\"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4\",\"thumbnailURL\":\"\"},{\"id\":5,\"shortDescription\":\"Finish filling prep\",\"description\":\"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.\",\"videoURL\":\"\",\"thumbnailURL\":\"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4\"},{\"id\":6,\"shortDescription\":\"Finishing Steps\",\"description\":\"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!\",\"videoURL\":\"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4\",\"thumbnailURL\":\"\"}],\"servings\":8,\"image\":\"\"}";
    private final int STEP_ID_WITH_VIDEO = 0;
    private final int STEP_ID__WITHOUT_VIDEO = 1;

    Recipe recipe = new Gson().fromJson(recipeString, Recipe.class);

    @Rule
    public ActivityTestRule<DetailRecipeActivity> mActivityRule =
            new ActivityTestRule<DetailRecipeActivity>(DetailRecipeActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    return new Intent(ApplicationProvider.getApplicationContext(),
                            DetailRecipeActivity.class).putExtra(RECIPE_KEY, recipe);
                }
            };

    @Before
    public void openDetailRecipeActivity() {
    }

    @Test
    public void clickOnRecyclerViewItem_opensRecipeDetailFragment() {
        onView(withId(R.id.rv_steps)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_steps)).perform(actionOnItemAtPosition(STEP_ID_WITH_VIDEO, click()));
        //validating we are showing the view pager
        onView(withId(R.id.step_view_pager)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnRecyclerViewItem_ShowsVideoView() {
        onView(withId(R.id.rv_steps)).perform(actionOnItemAtPosition(STEP_ID_WITH_VIDEO, click()));
        onView(allOf(withId(R.id.player_view), isDisplayed())).check(matches(isDisplayed()));

    }

    @Test
    public void clickOnStepWithoutVideo_HidesVideoView() {
        onView(withId(R.id.rv_steps)).perform(actionOnItemAtPosition(STEP_ID__WITHOUT_VIDEO, click()));
        onView(allOf(withId(R.id.player_view), isDisplayed())).check(doesNotExist());
    }

}
