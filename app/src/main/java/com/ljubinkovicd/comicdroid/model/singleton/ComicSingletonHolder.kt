package com.ljubinkovicd.comicdroid.model.singleton

import android.content.Context
import com.ljubinkovicd.comicdroid.model.Comic

/**
 * Created by ljubinkovicd on 11/8/17.
 */
class ComicSingletonHolder(context: Context) {

    private var mComics: MutableList<Comic> = mutableListOf()

    /** Singleton */
    companion object {
        private var sComicSingletonHolder: ComicSingletonHolder? = null

        fun get(context: Context): ComicSingletonHolder {
            if (sComicSingletonHolder == null) {
                sComicSingletonHolder = ComicSingletonHolder(context)
            }
            return sComicSingletonHolder as ComicSingletonHolder
        }
    }

    fun getComics(): List<Comic> {
        return mComics
    }

    fun setComics(heroes: MutableList<Comic>) {
        mComics = heroes
    }

    fun addComic(comic: Comic) {
        mComics.add(comic)
    }

    fun getComics(id: Int): Comic? {
        return mComics.firstOrNull { it.id?.equals(id) as Boolean }

        // this equals this:
//        for (comic: Comic in mComics) {
//            if (comic.id?.equals(id) as Boolean) {
//                return comic
//            }
//        }
//        return null
    }
}