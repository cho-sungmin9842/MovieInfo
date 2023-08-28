package com.example.movieinfo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieinfo.databinding.ItemMovieBinding

class MovieAdapter(private val context: Context) :
    ListAdapter<Result, MovieAdapter.ViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, result: Result) {
            Glide.with(binding.poster)
                .load(if (result.posters != "") result.posters else result.stlls)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.poster)
            binding.title.text = "${result.title} (${result.prodYear})"
            val repRlsDate = result.repRlsDate
            binding.repRlsDate.text =
                if (repRlsDate == "") "개봉일 정보 없음"
                else "${repRlsDate.substring(0..3)}.${repRlsDate.substring(4..5)}.${
                    repRlsDate.substring(
                        6..repRlsDate.lastIndex
                    )
                }"
            binding.nation.text = result.nation
            binding.runtime.text = "${result.runtime}분"
            binding.genre.text = result.genre
            binding.rating.text = if (result.rating == "") "관람등급 없음" else result.rating
            binding.keyword.text = if (result.keywords == "") "키워드 정보 없음" else result.keywords
            var director = ""
            result.directors.director.forEachIndexed { index, name ->
                if (index == 0)
                    director += name.directorNm
                else
                    director = director + "," + name.directorNm
            }
            binding.director.text = if (director == "") "감독 정보 없음" else "감독\n$director"
            var actor = ""
            result.actors.actor.forEachIndexed { index, name ->
                if (index == 0)
                    actor += name.actorNm
                else
                    actor = actor + "," + name.actorNm
            }
            binding.actor.text = if (actor == "") "배우 정보 없음" else "배우\n$actor"
            binding.plot.text = "줄거리\n${result.plots.plot[0].plotText}"
            binding.poster.setOnClickListener {
                // 상세 영화정보를 보여주는 웹사이트로 이동(startAcitivity,intent사용)
                println(result.kmdbUrl)
                ContextCompat.startActivity(
                    context,
                    Intent(Intent.ACTION_VIEW, Uri.parse(result.kmdbUrl)),
                    null
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, currentList[position])
    }
}