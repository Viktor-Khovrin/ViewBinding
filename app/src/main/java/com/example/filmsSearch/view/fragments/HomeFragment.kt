package com.example.filmsSearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.db_module.entity.Film
import com.example.filmsSearch.databinding.FragmentHomeBinding
import com.example.filmsSearch.utils.AnimationHelper
import com.example.filmsSearch.utils.AutoDisposable
import com.example.filmsSearch.utils.addTo
import com.example.filmsSearch.view.MainActivity
import com.example.filmsSearch.view.rv_adapters.FilmListRecyclerAdapter
import com.example.filmsSearch.view.rv_adapters.TopSpacingItemDecoration
import com.example.filmsSearch.view.viewmodel.HomeFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import java.util.Locale
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {
    private var bindingHome: FragmentHomeBinding? = null
    private val binding get() = bindingHome!!
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(HomeFragmentViewModel::class.java)
    }
    private lateinit var scope: CoroutineScope
    private val autoDisposable = AutoDisposable()

    private var filmsDataBase = listOf<Film>()
        set(value) {
            if (field == value) return
            field = value
            filmsAdapter.addItems(field)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingHome = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.homeFragmentRoot, requireActivity(), 1)
        initPullToRefresh()
        initRecyclerView()
        initSearchView()
        viewModel.showProgressBar
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.progressBar.isVisible = it
            }
            .addTo(autoDisposable)
        viewModel.filmsListFlow
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                list ->
                filmsDataBase = list
                filmsAdapter.addItems(list)
            }
            .addTo(autoDisposable)
    }

    private fun initSearchView() {
        binding.searchView.setOnClickListener{
            binding.searchView.isIconified = false
        }

        Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(subscriber: ObservableEmitter<String>) {
                binding.searchView.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String): Boolean {
                        filmsAdapter.items.clear()
                        subscriber.onNext(newText)
                        return false
                    }

                    override fun onQueryTextSubmit(query: String): Boolean {
                        subscriber.onNext(query)
                        return false
                    }
                })
            }
        })
            .subscribeOn(Schedulers.io())
            .map { it.lowercase(Locale.getDefault()).trim()}
            .debounce(500, TimeUnit.MILLISECONDS)
//            .filter {
//                viewModel.getFilms()
//                it.isNotBlank()
//            }
            .flatMap {
                viewModel.getSearchResult(1, it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onError = {
                    Toast.makeText(requireContext(),"Error in search results!", Toast.LENGTH_LONG)
                        .show()
                },
                onNext = {
                    filmsAdapter.addItems(it)
                }
                    )
            .addTo(autoDisposable)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        autoDisposable.bindTo(lifecycle)
        @Suppress("DEPRECATION")
        retainInstance = true
    }

    private fun initRecyclerView() {
         bindingHome?.mainRecycler?.apply {
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(DECORATION_PADDING)
            addItemDecoration(decorator)
        }

    }

    private fun initPullToRefresh() {
        binding.pullToRefresh.setOnRefreshListener {
            filmsAdapter.items.clear()
            viewModel.interactor.setWrongCurrentQueryTime()
            viewModel.getFilms()
            binding.pullToRefresh.isRefreshing = false
        }
    }
    override fun onDestroyView() {
        bindingHome = null
        super.onDestroyView()
    }

    companion object{
        private const val DECORATION_PADDING = 8
    }
}