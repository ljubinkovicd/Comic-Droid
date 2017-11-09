package com.ljubinkovicd.comicdroid.model.singleton

import android.content.Context
import com.ljubinkovicd.comicdroid.model.Hero

/**
 * Created by ljubinkovicd on 11/5/17.
 */
class HeroSingletonHolder(context: Context) {

//    private var mHeroes: List<Hero> = emptyList()
    private var mHeroes: MutableList<Hero> = mutableListOf()

    /** Singleton */
    companion object {
        private var sHeroSingletonHolder: HeroSingletonHolder? = null

        fun get(context: Context): HeroSingletonHolder {
            if (sHeroSingletonHolder == null) {
                sHeroSingletonHolder = HeroSingletonHolder(context)
            }
            return sHeroSingletonHolder as HeroSingletonHolder
        }
    }

    fun getHeroes(): List<Hero> {
        return mHeroes
    }

    fun setHeroes(heroes: MutableList<Hero>) {
        mHeroes = heroes
    }

    fun addHero(hero: Hero) {
        mHeroes.add(hero)
    }

    fun getHero(id: Int): Hero? {
        return mHeroes.firstOrNull { it.id?.equals(id) as Boolean }

        // this equals this:
//        for (hero: Hero in mHeroes) {
//            if (hero.id?.equals(id) as Boolean) {
//                return hero
//            }
//        }
//        return null
    }
}