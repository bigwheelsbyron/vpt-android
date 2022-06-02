package zxc.studio.vpt
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test

import org.junit.runner.RunWith
import zxc.studio.vpt.ui.login.LoginActivity

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest {

    @Test
    fun test_isActivityInView(){
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.loginActivity_container)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navigateToSignUp(){
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.loginActivity_button_emailLogin)).perform(click())
        onView(withId(R.id.signUpActivity_container)).check(matches(isDisplayed()))
    }

    @Test
    fun test_forgotPassword(){
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.loginActivity_button_forgotPassword)).perform(click())

    }





}