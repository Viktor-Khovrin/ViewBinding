package com.example.filmsSearch.view.rv_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsSearch.databinding.FilmItemBinding
import com.example.filmsSearch.data.Entity.Film
import com.example.filmsSearch.view.rv_viewholders.FilmViewHolder

//import kotlinx.android.synthetic.main.film_item.view.*

class FilmListRecyclerAdapter(private val clickListener: OnItemClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    inner class ViewHolder(val binding: FilmItemBinding):RecyclerView.ViewHolder(binding.root)
//    private lateinit var binding: FilmItemBinding
val items = mutableListOf<Film>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return FilmViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false))
        val binding = FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is FilmViewHolder -> {
                holder.onBind(items[position])
                holder.binding.itemContainer.setOnClickListener {clickListener.click(items[position])}
//                {
//                    itemView.item_container.setOnClickListener{
//                        clickListener.click(items[position])
//                    }
//                }
            }
        }
    }
    fun addItems(list: List<Film>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
    interface OnItemClickListener{
        fun click(film: Film)
    }

}