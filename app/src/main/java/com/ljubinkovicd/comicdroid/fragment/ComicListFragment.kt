package com.ljubinkovicd.comicdroid.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.Toast
import com.ljubinkovicd.comicdroid.R
import com.ljubinkovicd.comicdroid.activity.HeroPagerActivity
import com.ljubinkovicd.comicdroid.helper.inflate
import com.ljubinkovicd.comicdroid.helper.loadImgFromUrl
import com.ljubinkovicd.comicdroid.model.Comic
import com.ljubinkovicd.comicdroid.model.singleton.ComicSingletonHolder
import com.ljubinkovicd.comicdroid.network.MarvelApiClient
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

import kotlinx.android.synthetic.main.list_item_comic.view.*

/**
 * Created by ljubinkovicd on 11/8/17.
 */
class ComicListFragment : Fragment() {

    private val ARG_HERO_ID: String = "hero_id"

    private val TAG: String = "ComicListFragment"

    /** 209 strana objasnjenje zasto treba ovako. */
    fun newInstance(heroId: Int) : ComicListFragment {
        val args: Bundle = Bundle()
        args.putSerializable(ARG_HERO_ID, heroId)

        val fragment: ComicListFragment = ComicListFragment()
        fragment.arguments = args

        return fragment
    }

    private lateinit var comicLab: ComicSingletonHolder

    private var mComicRecyclerView: RecyclerView? = null
    private var mAdapter: ComicAdapter? = null

    private lateinit var mMarvelApiClient: MarvelApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // will explicitly receive a call to onCreateOptionsMenu(...)

        comicLab = ComicSingletonHolder.get(activity)

//        requestComics()
    }

    override fun onDestroy() {
        super.onDestroy()
        comicLab.setComics(mutableListOf())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View? = inflater?.inflate(R.layout.fragment_comic_list, container, false)

        mComicRecyclerView = view?.findViewById(R.id.comic_recycler_view)
        mComicRecyclerView?.layoutManager = GridLayoutManager(activity, 2)

//        updateUI()
//        setupAdapter()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupAdapter()

        val heroId: Int = arguments.getSerializable(ARG_HERO_ID) as Int
        requestComics(heroId)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.fragment_comic_list, menu)

        val searchItem: MenuItem = menu!!.findItem(R.id.menu_item_search)
        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "QueryTextChange: $newText")

                var searchText = newText?.toLowerCase()
                var newComicList: MutableList<Comic> = mutableListOf()

                val comics = comicLab.getComics()

                for (comic: Comic in comics) {
                    val comicTitle = comic.title?.toLowerCase()
                    if (comicTitle?.contains(searchText!!) as Boolean) {
                        newComicList.add(comic)
                    }
                }

                mAdapter?.setFilter(newComicList)

                return true
            }
        })
    }

    private inner class ComicAdapter constructor(comics: List<Comic>) : RecyclerView.Adapter<ComicHolder>() {

        private var mComics: MutableList<Comic> = mutableListOf()

        init {
            mComics = comics as MutableList<Comic>
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicHolder = ComicHolder(parent.inflate(R.layout.list_item_comic))

        override fun onBindViewHolder(holder: ComicHolder, position: Int) = holder.bind(mComics[position])

        override fun getItemCount(): Int = mComics.size

        fun setFilter(localComicList: MutableList<Comic>) {

            mComics = mutableListOf()
            mComics.addAll(localComicList)

            mAdapter!!.notifyDataSetChanged()
        }
    }

    private inner class ComicHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var mComic: Comic

        fun bind(comic: Comic) = with(itemView) {
            mComic = comic
            comic_title.text = mComic.title
            val imgUrl = mComic.thumbnail?.path + "." + mComic.thumbnail?.extension
            comic_thumbnail.loadImgFromUrl(imgUrl)

            itemView.setOnClickListener(this@ComicHolder)
        }

        override fun onClick(v: View?) {
            Toast.makeText(activity, "Comic with id ${mComic.id.toString()} and a title of ${mComic.title} clicked", Toast.LENGTH_SHORT).show()
//            val intent: Intent = HeroDetailsActivity().newIntent(activity, mComic.id!!)
//            val intent: Intent = HeroPagerActivity().newIntent(activity, mComic.id!!)
//            startActivity(intent)
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
            val comics = comicLab.getComics()
            mAdapter = ComicAdapter(comics)
            mComicRecyclerView?.adapter = mAdapter
        }
    }

    // TODO: CHANGE FUNCTION SIGNATURE (GET RID OF DEFAULT heroId VALUE)
    private fun requestComics(heroId: Int) {
        comicLab = ComicSingletonHolder.get(activity)

        mMarvelApiClient = MarvelApiClient()
        mMarvelApiClient.getMarvelComics(heroId, getString(R.string.api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ comicResponse ->
                    Log.d(TAG, "$comicResponse")
                    val description = comicResponse.description ?: "No description for ${comicResponse.title}"
                    comicLab.addComic(
                        Comic(
                            comicResponse?.creators,
                            comicResponse?.issueNumber,
                            comicResponse?.isbn,
                            description,
                            comicResponse?.variants,
                            comicResponse?.title,
                            comicResponse?.diamondCode,
                            comicResponse?.characters,
                            comicResponse?.urls,
                            comicResponse?.ean,
                            comicResponse?.collections,
                            comicResponse?.modified,
                            comicResponse?.id,
                            comicResponse?.prices,
                            comicResponse?.events,
                            comicResponse?.collectedIssues,
                            comicResponse?.pageCount,
                            comicResponse?.thumbnail,
                            comicResponse?.images,
                            comicResponse?.stories,
                            comicResponse?.textObjects,
                            comicResponse?.digitalId,
                            comicResponse?.format,
                            comicResponse?.upc,
                            comicResponse?.dates,
                            comicResponse?.resourceURI,
                            comicResponse?.variantDescription,
                            comicResponse?.issn,
                            comicResponse?.series
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