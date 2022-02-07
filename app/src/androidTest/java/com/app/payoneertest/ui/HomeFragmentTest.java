package com.app.payoneertest.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.app.payoneertest.R;
import com.app.payoneertest.utils.ToastMatcher;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class HomeFragmentTest {

    @Test
    public void progress_bar_is_shown() {
        FragmentScenario<HomeFragment> scenario = FragmentScenario.launchInContainer(
                HomeFragment.class,
                null,
                R.style.Theme_MaterialComponents_Light_NoActionBar
        );

        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
    }

    @Test
    public void code_input_layout_is_not_visible() {
        FragmentScenario<HomeFragment> scenario = FragmentScenario.launchInContainer(
                HomeFragment.class,
                null,
                R.style.Theme_MaterialComponents_Light_NoActionBar
        );

        onView(withId(R.id.input_layout)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void input_layout_is_shown() {
        FragmentScenario<HomeFragment> scenario = FragmentScenario.launchInContainer(
                HomeFragment.class,
                null,
                R.style.Theme_MaterialComponents_Light_NoActionBar
        );

        //after call to server would be done
        assertTrue(ForkJoinPool.commonPool().awaitQuiescence(10, TimeUnit.SECONDS));

        onView(withId(R.id.input_layout))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }

    @Test
    public void invalid_input_shows_error_toast_message() {
        FragmentScenario<HomeFragment> scenario = FragmentScenario.launchInContainer(
                HomeFragment.class,
                null,
                R.style.Theme_MaterialComponents_Light_NoActionBar
        );

        //after call to server would be done
        assertTrue(ForkJoinPool.commonPool().awaitQuiescence(10, TimeUnit.SECONDS));

        onView(withId(R.id.code_input))
                .perform(typeText("mandalorian"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

        onView(withId(R.id.code_input))
                .perform(clearText())
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

        onView(withId(R.id.code_input))
                .perform(typeText(""))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

    }

}