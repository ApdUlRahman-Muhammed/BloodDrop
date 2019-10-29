package com.example.android.blooddrop;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

/*import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;*/

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    public static final String User_Name = "ahmed90@gmail.com";
    public static final String password = "123456";

    @Rule
    public ActivityTestRule<MainActivity> mLoginActivity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSetup() throws IOException {

       /* onView(withId(R.id.username_field)).perform(replaceText("username"));
        onView(withId(R.id.password_field)).perform(replaceText("password"));
        onView(withId(R.id.login_button)).perform(click());*/

    }


}
