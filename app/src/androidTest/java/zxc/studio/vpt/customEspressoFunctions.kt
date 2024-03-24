package zxc.studio.vpt

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


class customEspressoFunctions {

    fun withBackgroundTint(color: Int): Matcher<View> {
        return object : BoundedMatcher<View, View>(View::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with background tint: ")
                description.appendValue(color)
            }

            override fun matchesSafely(view: View): Boolean {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val c = view.backgroundTintList?.defaultColor
                    println("matches safetly $c")
                    val alpha: Int = Color.alpha(c!!)
                    val red: Int = Color.red(c)
                    val green: Int = Color.green(c)
                    val blue: Int = Color.blue(c)
                    val col = Color.argb(alpha,red, green, blue)
                    val argb = Color.argb(alpha, red, green, blue)
                    println("matches safetly: $col")
                    println(argb)
                    view.backgroundTintList?.defaultColor == color
                } else {
                    val background = view.background
                    if (background is ColorDrawable) {
                        background.color == color
                    } else {
                        false
                    }
                }
            }
        }
    }

    fun toastMatcher(){

    }

}