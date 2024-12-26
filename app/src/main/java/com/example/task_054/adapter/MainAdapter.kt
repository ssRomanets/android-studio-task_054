package com.example.task_054.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task_054.databinding.ListFilmItemBinding
import com.example.task_054.models.FilmItem
import com.squareup.picasso.Picasso

class MainAdapter: PagingDataAdapter<FilmItem, MainAdapter.MainViewHolder>(DIFF_CALLBACK) {

    inner class MainViewHolder(val binding: ListFilmItemBinding): RecyclerView.ViewHolder(binding.root)
    companion object {
        var DIFF_CALLBACK = object : DiffUtil.ItemCallback<FilmItem>() {
            override fun areContentsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean = oldItem.id == newItem.id
            override fun areItemsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            Picasso.get().load(item?.posterUrl).into(filmIV);
            filmNameTV.text = item?.nameFilm
            filmTypeTV.text = item?.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ListFilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

}