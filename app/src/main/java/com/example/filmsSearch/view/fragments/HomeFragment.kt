package com.example.filmsSearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmsSearch.data.Entity.Film
import com.example.filmsSearch.databinding.FragmentHomeBinding
import com.example.filmsSearch.utils.AnimationHelper
import com.example.filmsSearch.utils.AutoDisposable
import com.example.filmsSearch.utils.addTo
import com.example.filmsSearch.view.MainActivity
import com.example.filmsSearch.view.rv_adapters.FilmListRecyclerAdapter
import com.example.filmsSearch.view.rv_adapters.TopSpacingItemDecoration
import com.example.filmsSearch.view.viewmodel.HomeFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import java.util.Locale

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

        binding.searchView.setOnClickListener{
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText?.isEmpty() == true){
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                val result = filmsDataBase.filter {
                    it.title.lowercase(Locale.getDefault()).contains(
                        newText?.lowercase(Locale.getDefault())
                        .toString())
                }
                filmsAdapter.addItems(result)
                return true
            }
        })
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

    override fun onStop() {
        super.onStop()
    }
    companion object{
        private const val DECORATION_PADDING = 8
    }
}