package br.com.android.consultainstituicaofinanceira

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import br.com.android.consultainstituicaofinanceira.ui.banklist.BankListActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class BankListActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(BankListActivity::class.java, false, false)

    @Before
    fun setup(){
        rule.launchActivity(Intent())
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("br.com.android.consultainstituicaofinanceira", appContext.packageName)
    }

    @Test
    fun bankListIsVisible(){
        doWait()
        onView(withId(R.id.bankListFavoriteRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.bankListRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun checkBankListItemCount(){
        doWait()
        assert(getCountFromList(R.id.bankListFavoriteRecyclerView) > 0)
        assert(getCountFromList(R.id.bankListRecyclerView) > 0)
    }
}
