package com.ljubinkovicd.comicdroid.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ljubinkovicd.comicdroid.R
import com.ljubinkovicd.comicdroid.activity.HeroPagerActivity
import com.ljubinkovicd.comicdroid.helper.inflate
import com.ljubinkovicd.comicdroid.helper.loadImgFromUrl
import com.ljubinkovicd.comicdroid.model.Hero
import com.ljubinkovicd.comicdroid.model.HeroSingletonHolder
import com.ljubinkovicd.comicdroid.network.MarvelApiClient
import kotlinx.android.synthetic.main.list_item_hero.view.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ljubinkovicd on 11/5/17.
 */
class HeroListFragment : Fragment() {

    private val TAG: String = "HeroListFragment"

    private lateinit var heroLab: HeroSingletonHolder

    private var mHeroRecyclerView: RecyclerView? = null
    private var mAdapter: HeroAdapter? = null
    private var mMarvelHeroes: MutableList<Hero> = mutableListOf()

    private lateinit var mMarvelApiClient: MarvelApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        heroLab = HeroSingletonHolder.get(activity)

//        requestHeroes()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View? = inflater?.inflate(R.layout.fragment_hero_list, container, false)

        mHeroRecyclerView = view?.findViewById(R.id.hero_recycler_view)
        mHeroRecyclerView?.layoutManager = LinearLayoutManager(activity)

//        updateUI()
//        setupAdapter()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupAdapter()
        requestHeroes()
    }

    private inner class HeroAdapter constructor(val heroes: List<Hero>) : RecyclerView.Adapter<HeroHolder>() {

        private var mHeroes: List<Hero> = emptyList()

        init {
            mHeroes = heroes
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroHolder = HeroHolder(parent.inflate(R.layout.list_item_hero))

        override fun onBindViewHolder(holder: HeroHolder, position: Int) = holder.bind(mHeroes[position])

        override fun getItemCount(): Int = mHeroes.size

    }

    private inner class HeroHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var mHero: Hero

        fun bind(hero: Hero) = with(itemView) {
            mHero = hero
            hero_name.text = mHero.name
            val imgUrl = mHero.thumbnail?.path + "." + mHero.thumbnail?.extension
            hero_thumbnail.loadImgFromUrl(imgUrl)

            itemView.setOnClickListener(this@HeroHolder)
        }

        override fun onClick(v: View?) {
            Toast.makeText(activity, "Hero with id ${mHero.id.toString()} and a name of ${mHero.name} clicked", Toast.LENGTH_SHORT).show()
//            val intent: Intent = HeroDetailsActivity().newIntent(activity, mHero.id!!)
            val intent: Intent = HeroPagerActivity().newIntent(activity, mHero.id!!)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
//        updateUI()
        setupAdapter()

    }

    /** Custom methods */
    private fun setupAdapter() {
        if (isAdded) {
            val heroes = heroLab.getHeroes()
            mAdapter = HeroAdapter(heroes)
            mHeroRecyclerView?.adapter = mAdapter
        }
    }

    private fun requestHeroes() {
        heroLab = HeroSingletonHolder.get(activity)

        mMarvelApiClient = MarvelApiClient()
        mMarvelApiClient.getMarvelHeroes(getString(R.string.api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ heroResponse ->
                    Log.d(TAG, "$heroResponse")
                    val description = heroResponse.description ?: "No description for ${heroResponse.name}"
                    heroLab.addHero(
                        Hero(
                            heroResponse.thumbnail,
                            heroResponse.urls,
                            heroResponse.stories,
                            heroResponse.series,
                            heroResponse.comics,
                            heroResponse.name,
                            description,
                            heroResponse.modified,
                            heroResponse.id,
                            heroResponse.resourceURI,
                            heroResponse.events
                        )
                    )
                },
                { error ->
                    Log.d(TAG, error.message)
                },
                {
                    setupAdapter()
                })
    }
}
