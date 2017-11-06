package com.ljubinkovicd.comicdroid.activity

import android.support.v4.app.Fragment
import com.ljubinkovicd.comicdroid.fragment.HeroListFragment
import com.ljubinkovicd.comicdroid.fragment.SingleFragmentActivity

/**
 * Created by ljubinkovicd on 11/5/17.
 */
class HeroListActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return HeroListFragment()
    }
}