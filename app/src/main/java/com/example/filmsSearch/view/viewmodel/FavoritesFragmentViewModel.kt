package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsSearch.App
import com.example.filmsSearch.domain.Film
import com.example.filmsSearch.domain.Interactor
import javax.inject.Inject

class FavoritesFragmentViewModel : ViewModel() {
    @Inject
    lateinit var interactor: Interactor
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    private lateinit var viewModel: HomeFragmentViewModel

    fun init() {
        App.instance.dagger.inject(this)
        getFilmsListLiveData()
    }

    private fun getFilmsListLiveData() {
        filmsListLiveData.value = interactor.getFilmsLiveData()
//        viewModel = ViewModelProvider(requireActivity()).get(HomeFragmentViewModel::class.java)
//        viewModel.filmsListLiveData.observe(viewLifecycleOwner, Observer<List<Film>> {
//            filmsDataBase = it.filter{it.isInFavorites}
//        })
//        filmsListLiveData.value = interactor.getFilms
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }

}