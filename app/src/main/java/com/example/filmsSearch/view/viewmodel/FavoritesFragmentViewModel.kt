package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsSearch.domain.Film
import org.koin.core.component.KoinComponent

class FavoritesFragmentViewModel:ViewModel(), KoinComponent {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
//    private var homeFragmentLiveData:HomeFragmentViewModel = ViewModelProvider. .get(HomeFragmentViewModel.class)
//    private val interactor: Interactor by inject()
//    private var interactor: Interactor = App.instance.interactor
//    init {

//        interactor.getFilmsFromApi(1, object : HomeFragmentViewModel.ApiCallback {
//            override fun onSuccess(films: List<Film>) {
//        hfLiveData= HomeFragment().viewModel.filmsListLiveData
//        filmsListLiveData.postValue(HomeFragment().getInstance().getData())
//            }

//            override fun onFailure() {
//            }
//        })
//    }
}