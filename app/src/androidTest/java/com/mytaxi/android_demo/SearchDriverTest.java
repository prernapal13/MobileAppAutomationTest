package com.mytaxi.android_demo;

import android.support.test.espresso.action.ViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.mytaxi.android_demo.base.BaseTest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.close;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchDriverTest extends BaseTest {

    @Test
    public void testSearchAndCallDriver() {
        Log.i("@LOGIN", "Login into myTaxi App");
        onView(withId(R.id.edt_username)).perform(typeText(loginCredentialMap.get("username")));
        onView(withId(R.id.edt_password)).perform(typeText(loginCredentialMap.get("password")));
        onView(withId(R.id.btn_login)).perform(click());

        Log.i("@VERIFY SUCCESSFUL LOGIN", "Verify username in drawer layout and close drawer layout after verification");
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withId(R.id.nav_username)).check(matches(withText(loginCredentialMap.get("username"))));
        onView(withId(R.id.drawer_layout)).perform(close());

        Log.i("@SEARCH DRIVER", "Search driver by " + searchString);
        onView(withId(R.id.textSearch)).perform(typeText(searchString));

        Log.i("@SELECT SECOND DRIVER", "Fetch 2nd driver name from autocomplete dropdown and select");
        String driverName = getDriverNameAtIndex(2);  // get 2nd driver dynamically from autocomplete dropdown list.
        onView(withText(driverName))
                .inRoot(withDecorView(not(is(mainActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed())).perform(click());

        Log.i("@DRIVER VERIFICATION",
                "Verify selected driver's (" + driverName + ") name on Driver Profile Page.");
        onView(withId(R.id.textViewDriverName)).check(matches(withText(driverName)));

        Log.i("@CALLING DRIVER", "Click on call button on Driver Profile Page.");
        onView(withId(R.id.fab)).check(matches(isClickable())).perform(ViewActions.click());
    }
}