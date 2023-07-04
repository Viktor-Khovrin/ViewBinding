package com.example.filmsSearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmsSearch.data.Entity.Film
import com.example.filmsSearch.databinding.FragmentFavoritesBinding
import com.example.filmsSearch.view.MainActivity
import com.example.filmsSearch.view.rv_adapters.FilmListRecyclerAdapter
import com.example.filmsSearch.view.rv_adapters.TopSpacingItemDecoration
import com.example.filmsSearch.view.viewmodel.FavoritesFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesFragment : Fragment() {
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private var bindingFavorites: FragmentFavoritesBinding? = null

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(FavoritesFragmentViewModel::class.java)
    }
    private lateinit var scope: CoroutineScope

    private val binding get() = bindingFavorites!!
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
        bindingFavorites = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scope = CoroutineScope(Dispatchers.IO).also {scope ->
            scope.launch{
                viewModel.filmsListFlow.collect{
                    withContext(Dispatchers.Main){
                        filmsDataBase = it.filter{it.isInFavorites}
                        filmsAdapter.addItems(it.filter{it.isInFavorites})
                    }
                }
            }
        }
        binding.favoritesRecycler
            .apply {
                filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
                adapter = filmsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                val decorator = TopSpacingItemDecoration(8)
                addItemDecoration(decorator)
            }
    }

    override fun onDestroyView() {
        bindingFavorites = null
        super.onDestroyView()
    }

    override fun onStop() {
        super.onStop()
        scope.cancel()
    }
}