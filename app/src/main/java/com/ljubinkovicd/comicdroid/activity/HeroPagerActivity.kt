package com.ljubinkovicd.comicdroid.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.ljubinkovicd.comicdroid.R
import com.ljubinkovicd.comicdroid.fragment.HeroDetailsFragment
import com.ljubinkovicd.comicdroid.model.Hero
import com.ljubinkovicd.comicdroid.model.HeroSingletonHolder

import kotlinx.android.synthetic.main.activity_hero_pager.*

/**
 * Created by ljubinkovicd on 11/6/17.
 */

/** 220 strana objasnjenje */
class HeroPagerActivity : AppCompatActivity() {

    private val EXTRA_HERO_ID = "com.ljubinkovicd.testappcleancode.activity.hero_id"

    private lateinit var mViewPager: ViewPager
    private lateinit var mHeroes: List<Hero>

    fun newIntent(packageContext: Context, heroId: Int) : Intent {
        val intent = Intent(packageContext, HeroPagerActivity::class.java)
        intent.putExtra(EXTRA_HERO_ID, heroId)

        return intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_pager)

        val heroId: Int = intent.getSerializableExtra(EXTRA_HERO_ID) as Int

//        mViewPager = findViewById(R.id.hero_view_pager)
        mViewPager = hero_view_pager // might cause null object reference ???
        mHeroes = HeroSingletonHolder.get(this).getHeroes()

        val fragmentManager: FragmentManager = supportFragmentManager
        mViewPager.adapter = object : FragmentStatePagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment {
                val hero: Hero = mHeroes[position]

                return HeroDetailsFragment().newInstance(hero.id!!)
            }

            override fun getCount(): Int = mHeroes.size
        }

        for (i in 0 until mHeroes.size) {
            if (mHeroes[i].id == heroId) {
                mViewPager.currentItem = i

                break
            }
        }

    }
}