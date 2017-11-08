package com.ljubinkovicd.comicdroid.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ljubinkovicd.comicdroid.R
import com.ljubinkovicd.comicdroid.helper.inflate
import com.ljubinkovicd.comicdroid.helper.loadImgFromUrl
import com.ljubinkovicd.comicdroid.model.Hero
import com.ljubinkovicd.comicdroid.model.HeroSingletonHolder
import com.ljubinkovicd.comicdroid.network.MarvelApiClient

import kotlinx.android.synthetic.main.fragment_hero_details.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ljubinkovicd on 11/6/17.
 */
class HeroDetailsFragment : Fragment() {

    private val ARG_HERO_ID: String = "hero_id"

    private lateinit var mMarvelApiClient: MarvelApiClient

    /** 209 strana objasnjenje zasto treba ovako. */
    fun newInstance(heroId: Int) : HeroDetailsFragment {
        val args: Bundle = Bundle()
        args.putSerializable(ARG_HERO_ID, heroId)

        val fragment: HeroDetailsFragment = HeroDetailsFragment()
        fragment.arguments = args

        return fragment
    }

    private lateinit var mHero: Hero
//    private var mNameField: TextView? = null
//    private var mNameDescription: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val heroId: Int = arguments.getSerializable(ARG_HERO_ID) as Int
        mHero = HeroSingletonHolder.get(activity).getHero(heroId)!!
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = container?.inflate(R.layout.fragment_hero_details)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMarvelApiClient = MarvelApiClient()

        hero_thumbnail.loadImgFromUrl(mHero.thumbnail?.path + "." + mHero.thumbnail?.extension)
        hero_name.text = mHero.name
        hero_description.text = if (mHero.description != "") { mHero.description } else { String.format(getString(R.string.no_description_for_hero_format), mHero.name) }
        hero_comics_button.text = mHero.comics?.available.toString()
        hero_comics_button.setOnClickListener {
            Toast.makeText(activity, "${mHero.name} is featured in: ${mHero.comics?.available} comics.", Toast.LENGTH_SHORT).show()
            mMarvelApiClient.getMarvelComics(mHero.id!!, getString(R.string.api_key))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ comicResponse ->
                        Log.d("STRIP: ", "$comicResponse")
                    },
                    { error ->
                        Log.d("STRIP-ERROR: ", error.printStackTrace().toString())
                    })
        }
    }
}