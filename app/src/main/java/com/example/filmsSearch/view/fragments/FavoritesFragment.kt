package com.example.filmsSearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmsSearch.databinding.FragmentFavoritesBinding
import com.example.filmsSearch.domain.Film
import com.example.filmsSearch.view.MainActivity
import com.example.filmsSearch.view.rv_adapters.FilmListRecyclerAdapter
import com.example.filmsSearch.view.rv_adapters.TopSpacingItemDecoration
import com.example.filmsSearch.view.viewmodel.FavoritesFragmentViewModel

class FavoritesFragment : Fragment() {
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private var bindingFavorites: FragmentFavoritesBinding? = null
    private val binding get() = bindingFavorites!!

    private val viewModelFavorites by lazy {
        ViewModelProvider.NewInstanceFactory().create(FavoritesFragmentViewModel::class.java)
    }

    private var filmsDataBase = listOf<Film>()
        //Используем backing field
        set(value) {
            //Если придет такое же значение, то мы выходим из метода
            if (field == value) return
            //Если пришло другое значение, то кладем его в переменную
            field = value.filter { it.isInFavorites }
//            field = value.filter {it.isInFavorites}
            //Обновляем RV адаптер
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
        viewModelFavorites.filmsListLiveDataFavorite.observe(viewLifecycleOwner, Observer<List<Film>> {
            filmsDataBase = it
        })
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
//        val result = filmsDataBase.filter {it.isInFavorites}

//        filmsAdapter.addItems(result)
        filmsAdapter.addItems(filmsDataBase)
    }

    override fun onDestroyView() {
        bindingFavorites = null
        super.onDestroyView()
    }
}