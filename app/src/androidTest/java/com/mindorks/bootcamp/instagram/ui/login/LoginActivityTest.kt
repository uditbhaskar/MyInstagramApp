package com.mindorks.bootcamp.instagram.ui.login

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.mindorks.bootcamp.instagram.R
import com.mindorks.bootcamp.instagram.TestComponentRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

class LoginActivityTest {
    private val component =
        TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    private val main = IntentsTestRule(LoginActivity::class.java,false,false)

    @get:Rule
    val chain = RuleChain.outerRule(component).around(main)

    @Before
    fun setUp(){

    }

    @Test
    fun testCheckViewsDisplay(){
        main.launchActivity(Intent(component.getContext(), LoginActivity::class.java))
        Espresso.onView(ViewMatchers.withId(R.id.layout_email))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.layout_password))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.bt_login))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}