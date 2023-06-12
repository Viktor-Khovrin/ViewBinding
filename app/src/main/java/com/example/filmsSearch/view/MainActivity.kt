package com.example.filmsSearch.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.filmsSearch.R
import com.example.filmsSearch.databinding.ActivityMainBinding
import com.example.filmsSearch.domain.Film
import com.example.filmsSearch.view.fragments.DetailsFragment
import com.example.filmsSearch.view.fragments.FavoritesFragment
import com.example.filmsSearch.view.fragments.HomeFragment
import com.example.filmsSearch.view.fragments.SelectionsFragment
import com.example.filmsSearch.view.fragments.SettingsFragment
import com.example.filmsSearch.view.viewmodel.HomeFragmentViewModel

class MainActivity : AppCompatActivity() {

    private var timePressed = 0L
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var viewModel: HomeFragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        supportActionBar?.hide()
        initNavigation()
        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .commit()
    }

    fun launchDetailsFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        val fragment = DetailsFragment()
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initNavigation(){
        mainBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favorites -> {
                val tag = "favorites"
                val fragment = checkFragmentExistence(tag)
                changeFragment( fragment?: FavoritesFragment(), tag)
                    true
                }
                R.id.selections -> {
                    /*Toast.makeText(this,R.string.menu_selections_title, Toast.LENGTH_SHORT).show()*/
                    val tag = "selections"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: SelectionsFragment(), tag)
                    true
                }
                R.id.watch_later -> {
                    Toast.makeText(this, R.string.menu_watch_later_title, Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.settings -> {
                    val tag = "settings"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: SettingsFragment(), tag)
                    true
                }
                else -> false
            }
        }
    }

    private fun checkFragmentExistence(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed(){
        if (supportFragmentManager.backStackEntryCount == 0){
            if (timePressed + BACK_PRESSED_TIMEOUT > System.currentTimeMillis()){
                super.onBackPressed()
                finish()
            }else {
                Toast.makeText(this, R.string.on_back_pressed, Toast.LENGTH_SHORT).show()
                timePressed = System.currentTimeMillis()
            }
        }else{
            super.onBackPressed()
        }

    }

    companion object{
        const val BACK_PRESSED_TIMEOUT = 2000
    }


}