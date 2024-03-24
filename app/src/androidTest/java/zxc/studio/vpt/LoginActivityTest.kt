package zxc.studio.vpt

import android.graphics.Color
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressouitestexamples.ToastMatcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import zxc.studio.vpt.ui.login.LoginActivity


@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest {

    var activityScenario = ActivityScenario.launch(LoginActivity::class.java)
    var customEspressoFunctions = customEspressoFunctions()

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(LoginActivity::class.java)
    }

    @Test
    fun test_loginActivity_visibility(){
        onView(withId(R.id.loginActivity_container)).check(matches(isDisplayed()))
        onView(withId(R.id.loginActivity_image_SubtractTop)).check(matches(isDisplayed()))
        onView(withId(R.id.loginActivity_image_SubtractBottom)).check(matches(isDisplayed()))
        onView(withId(R.id.loginActivity_linearLayout_VPTSign)).check(matches(isDisplayed()))
        onView(withId(R.id.loginActivity_textView_VPTSignV)).check(matches(isDisplayed()))
        onView(withId(R.id.loginActivity_textView_VPTSignP)).check(matches(isDisplayed()))
        onView(withId(R.id.loginActivity_textView_VPTSignT)).check(matches(isDisplayed()))
        onView(withId(R.id.loginActivity_textView_loginOrSignUp)).check(matches(isDisplayed()))
        onView(withId(R.id.loginActivity_linearLayout_loginDetails)).check(matches(isDisplayed()))
        onView(withId(R.id.loginActivity_editText_emailInput)).check(matches(isDisplayed()))
        onView(withId(R.id.loginActivity_editText_passwordInput)).check(matches(isDisplayed()))
        onView(withId(R.id.loginActivity_button_emailLogin)).check(matches(isDisplayed()))
        onView(withId(R.id.loginActivity_button_emailSignUp)).check(matches(isDisplayed()))
        onView(withId(R.id.loginActivity_button_forgotPassword)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navigateToSignUp(){
        onView(withId(R.id.loginActivity_button_emailSignUp)).perform(click())
        onView(withId(R.id.signUpActivity_linearLayout_signUpDetails)).check(matches(isDisplayed()))
    }
    @Test
    fun test_pressBack(){
        onView(withId(R.id.loginActivity_button_emailSignUp)).perform(click())
        Espresso.pressBack()
        onView(withId(R.id.loginActivity_container)).check(matches(isDisplayed()))
    }

    @Test
    fun test_editTextBackgroundTint_noData() {
        onView(withId(R.id.loginActivity_button_emailLogin)).perform(click())
        onView(withId(R.id.loginActivity_editText_emailInput)).check(matches(customEspressoFunctions.withBackgroundTint(Color.RED)))
        onView(withId(R.id.loginActivity_editText_passwordInput)).check(matches(customEspressoFunctions.withBackgroundTint(Color.RED)))
    }

    @Test
    fun test_editTextBackgroundTint_emailData() {
        onView(withId(R.id.loginActivity_editText_emailInput)).perform(typeText("o@o.com"))
        onView(withId(R.id.loginActivity_button_emailLogin)).perform(click())
        onView(withId(R.id.loginActivity_editText_emailInput)).check(matches(customEspressoFunctions.withBackgroundTint(-3355444)))
        onView(withId(R.id.loginActivity_editText_passwordInput)).check(matches(customEspressoFunctions.withBackgroundTint(Color.RED)))
    }

    @Test
    fun test_editTextBackgroundTint_passwordData() {
        onView(withId(R.id.loginActivity_editText_passwordInput)).perform(typeText("o@o.com"))
        onView(withId(R.id.loginActivity_button_emailLogin)).perform(click())
        onView(withId(R.id.loginActivity_editText_emailInput)).check(matches(customEspressoFunctions.withBackgroundTint(Color.RED)))
        onView(withId(R.id.loginActivity_editText_passwordInput)).check(matches(customEspressoFunctions.withBackgroundTint(-3355444)))
    }

    @Test
    fun test_forgotEmailPress_noEmail(){
        onView(withId(R.id.loginActivity_button_forgotPassword)).perform(click())
        onView(withText("Please provide your email")).inRoot(ToastMatcher())
                .check(matches(isDisplayed()))

    }
    //"Please provide your email"

    @Test
    fun test_forgotEmailPress_invalidEmail(){
        onView(withId(R.id.loginActivity_button_forgotPassword)).perform(click())
        onView(withId(R.id.loginActivity_editText_emailInput)).perform(typeText("o@o."))
        onView(withText("Invalid email")).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    @Test
    fun test_forgotEmailPress_validEmail(){
        onView(withId(R.id.loginActivity_button_forgotPassword)).perform(click())
        onView(withId(R.id.loginActivity_editText_emailInput)).perform(typeText("o@o.com"))
        onView(withText("Email sent")).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }




}