package zxc.studio.vpt

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import zxc.studio.vpt.ui.login.LoginActivity

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.hamcrest.Description
import org.hamcrest.Matcher

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest {

    var activityScenario = ActivityScenario.launch(LoginActivity::class.java)

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
    fun test_forgotPassword(){
        onView(withId(R.id.loginActivity_button_forgotPassword)).perform(click())
        onView(withId(R.id.loginActivity_editText_emailInput))
    }

    @Test
    fun test_pressBack(){
        onView(withId(R.id.loginActivity_button_emailSignUp)).perform(click())
        Espresso.pressBack()
        onView(withId(R.id.loginActivity_container)).check(matches(isDisplayed()))
    }

    @Test
    fun test_SignInPress_noData(){
        onView(withId(R.id.loginActivity_button_emailLogin)).perform(click())
//        onView(withId(R.id.loginActivity_editText_emailInput)).check(matches(hasTextColor(R.color.expected_text_color)))?
        onView(withId(R.id.loginActivity_editText_emailInput)).check(matches(hasBackgroundTintColor(ContextCompat.getColor(getInstrumentation().context, R.color.expected_text_color))))

    }

    fun hasBackgroundTintColor(expectedColor: Int): Matcher<View> {
        return object : BoundedMatcher<View, View>(View::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with background tint color: ")
                description.appendValue(expectedColor)
            }

            override fun matchesSafely(view: View): Boolean {
                if (view.backgroundTintList == null) {
                    return false
                }
                return view.backgroundTintList!!.defaultColor == expectedColor
            }
        }
    }




}