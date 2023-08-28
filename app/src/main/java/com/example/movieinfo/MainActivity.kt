package com.example.movieinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfo.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val movieAdapter by lazy {
        MovieAdapter(this)
    }
    private val movieService by lazy {
        MovieRepository().retrofit.create(MovieInfoService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        binding.movieReyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL))
        }
        binding.radioGroup.isVisible = false
        getMovieList()
        radioListener()
        contentEditTextListener()
    }

    private fun contentEditTextListener() {
        binding.content.addTextChangedListener {
            if (it?.isEmpty() == true) {
                getMovieList()
                binding.radioGroup.clearCheck()
                binding.radioGroup.isVisible = false
            } else
                binding.radioGroup.isVisible = true
        }
    }

    private fun radioListener() {
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.title -> {
                    if (binding.content.text.isEmpty()) return@setOnCheckedChangeListener
                    getMovieListForCategory(title = binding.content.text.toString())
                }

                R.id.actor -> {
                    if (binding.content.text.isEmpty()) return@setOnCheckedChangeListener
                    getMovieListForCategory(actor = binding.content.text.toString())
                }

                R.id.director -> {
                    if (binding.content.text.isEmpty()) return@setOnCheckedChangeListener
                    getMovieListForCategory(director = binding.content.text.toString())
                }

                else -> getMovieList()
            }
        }
    }

    private fun getMovieList() {
        movieService.getMovieList("kmdb_new2", this.getString(R.string.api_key))
            .enqueue(object : Callback<MovieEntity> {
                override fun onResponse(call: Call<MovieEntity>, response: Response<MovieEntity>) {
                    println("response code=${response.code()}, response body=${response.body()}")
                    if (response.isSuccessful)
                        movieAdapter.submitList(response.body()?.data?.get(0)?.result)
                    else
                        movieAdapter.submitList(emptyList())
                }

                override fun onFailure(call: Call<MovieEntity>, t: Throwable) {
                    println(t.message)
                    t.printStackTrace()
                }
            })
    }

    private fun getMovieListForCategory(
        title: String = "",
        actor: String = "",
        director: String = ""
    ) {
        movieService.getMovieListForCategory(
            collection = "kmdb_new2", serviceKey = getString(R.string.api_key),
            title = title, actor = actor, director = director
        ).enqueue(object : Callback<MovieEntity> {
            override fun onResponse(call: Call<MovieEntity>, response: Response<MovieEntity>) {
                println("response code=${response.code()}, response body=${response.body()}")
                if (response.isSuccessful)
                    movieAdapter.submitList(response.body()?.data?.get(0)?.result)
                else
                    movieAdapter.submitList(emptyList())
            }

            override fun onFailure(call: Call<MovieEntity>, t: Throwable) {
                println(t.message)
                t.printStackTrace()
            }
        })
    }
}