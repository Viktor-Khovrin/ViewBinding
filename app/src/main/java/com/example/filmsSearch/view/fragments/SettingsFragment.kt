package com.example.filmsSearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.filmsSearch.R
import com.example.filmsSearch.databinding.FragmentSettingsBinding
import com.example.filmsSearch.utils.AnimationHelper
import com.example.filmsSearch.view.viewmodel.SettingsFragmentViewModel


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(SettingsFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Подключаем анимации и передаем номер позиции у кнопки в нижнем меню
        AnimationHelper.performFragmentCircularRevealAnimation(binding.settingsFragmentRoot, requireActivity(), 4)
        //Слушаем, какой у нас сейчас выбран вариант в настройках
        viewModel.categoryPropertyLifeData.observe(viewLifecycleOwner, Observer<String> {
            when(it) {
                getString(R.string.radio_button_popular) -> binding.radioGroup.check(R.id.radio_popular)
                getString(R.string.radio_button_top_rated) -> binding.radioGroup.check(R.id.radio_top_rated)
                getString(R.string.radio_button_genres) -> binding.radioGroup.check(R.id.radio_upcoming)
                getString(R.string.radio_button_playing) -> binding.radioGroup.check(R.id.radio_now_playing)
            }
        })
        //Слушатель для отправки нового состояния в настройки
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.radio_popular -> viewModel.putCategoryProperty(getString(R.string.radio_button_popular))
                R.id.radio_top_rated -> viewModel.putCategoryProperty(getString(R.string.radio_button_top_rated))
                R.id.radio_upcoming -> viewModel.putCategoryProperty(getString(R.string.radio_button_genres))
                R.id.radio_now_playing -> viewModel.putCategoryProperty(getString(R.string.radio_button_playing))
            }
        }
    }

//    companion object {
//        private const val POPULAR_CATEGORY = "popular"
//        private const val TOP_RATED_CATEGORY = "top_rated"
//        private const val UPCOMING_CATEGORY = "upcoming"
//        private const val NOW_PLAYING_CATEGORY = "now_playing"
//    }
}

