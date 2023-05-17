package com.example.filmsSearch

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Scene
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.example.filmsSearch.databinding.FragmentHomeBinding
import java.util.Locale

val filmsDataBase = listOf(
    Film("Stranger Things", R.drawable.stranger_things, "When a young boy disappears, his mother, a police chief and his friends must confront terrifying supernatural forces in order to get him back", 6.5f),
    Film("A Call to Spy", R.drawable.a_call_to_spy, "In the beginning of WWII, with Britain becoming desperate, Churchill orders his new spy agency - SOE - to recruit and train women as spies", 7.2f),
    Film("Big Daddy", R.drawable.big_daddy, "A lazy law-school grad adopts a kid to impress his girlfriend, but everything doesn't go as planned and he becomes the unlikely foster father", 6.9f),
    Film("Blue Jasmine", R.drawable.blue_jasmine, "A New York socialite, deeply troubled and in denial, arrives in San Francisco to impose upon her sister. She looks like a million dollars but isn't bringing money, peace, or love", 7.1f),
    Film("Boogie Nights", R.drawable.boogie_nights, "Back when sex was safe, pleasure was a business and business was booming, an idealistic porn producer aspires to elevate his craft to an art when he discovers a hot young talent", 8.1f),
    Film("Catch Me If You Can", R.drawable.catch_me_if_you_can, "This should be a description", 5.3f),
    Film("Deliverance", R.drawable.deliverance, "Intent on seeing the Cahulawassee River before it's dammed and turned into a lake, outdoor fanatic Lewis Medlock takes his friends on a canoeing trip they'll never forget into the dangerous American back-country", 4.4f),
    Film("Goodfellas", R.drawable.goodfellas, "The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito in the Italian-American crime syndicate", 3.3f),
)

class HomeFragment : Fragment() {
//    private lateinit var binding: FragmentHomeBinding
    private var bindingHome: FragmentHomeBinding? = null
    private val binding get() = bindingHome!!
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        binding= FragmentHomeBinding.inflate(inflater, container, false)
        bindingHome = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sceneRoot = Scene(binding.homeFragmentRoot)
//        val sceneRoot = Scene.getSceneForLayout(binding.root,R.layout.fragment_home,requireContext())
//        val scene= Scene.getSceneForLayout(binding.homeFragmentRoot, R.layout.fragment_home,requireContext())
        val searchSlide = Slide(Gravity.TOP).addTarget(binding.searchView)
        val recyclerSlide = Slide(Gravity.BOTTOM).addTarget(binding.mainRecycler)
        val customTransition = TransitionSet().apply {
            duration = 500
            addTransition(recyclerSlide)
            addTransition(searchSlide)
        }
        TransitionManager.go(sceneRoot, customTransition)
        initRecyclerView()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    private fun initRecyclerView() {
         bindingHome?.mainRecycler?.apply {
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(DECORATION_PADDING)
            addItemDecoration(decorator)
        }

        filmsAdapter.addItems(filmsDataBase)

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

    override fun onDestroyView() {
        bindingHome = null
        super.onDestroyView()
    }
    companion object{
        private const val DECORATION_PADDING = 8
    }
}