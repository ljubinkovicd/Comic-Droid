package com.ljubinkovicd.comicdroid.activity

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.ljubinkovicd.comicdroid.fragment.ComicListFragment
import com.ljubinkovicd.comicdroid.fragment.SingleFragmentActivity

/**
 * Created by ljubinkovicd on 11/8/17.
 */
class ComicListActivity : SingleFragmentActivity() {

    private val EXTRA_HERO_ID = "com.ljubinkovicd.testappcleancode.activity.hero_id"

    fun newIntent(packageContext: Context, heroId: Int) : Intent {
        val intent = Intent(packageContext, ComicListActivity::class.java)
        intent.putExtra(EXTRA_HERO_ID, heroId)

        return intent
    }

    override fun createFragment(): Fragment {

        val heroId: Int = intent.getSerializableExtra(EXTRA_HERO_ID) as Int

        return ComicListFragment().newInstance(heroId)
    }
}