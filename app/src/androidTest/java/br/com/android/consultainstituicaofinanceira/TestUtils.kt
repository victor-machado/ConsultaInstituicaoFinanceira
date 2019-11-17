package br.com.android.consultainstituicaofinanceira

import android.view.View
import androidx.annotation.IdRes
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.junit.internal.matchers.TypeSafeMatcher

fun getCountFromList(@IdRes listViewId: Int): Int {
    var count = 0

    val matcher = object : TypeSafeMatcher<View>() {
        override fun matchesSafely(item: View): Boolean {
            count = (item as RecyclerView).size
            return true
        }

        override fun describeTo(description: Description) {}
    }

    Espresso.onView(ViewMatchers.withId(listViewId)).check(ViewAssertions.matches(matcher))

    val result = count
    count = 0
    return result
}

fun doWait(){
    Thread.sleep(2000)
}