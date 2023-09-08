package com.example.movieinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfo.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: MyViewModel by viewModels()
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
        // 검색한 내용이 없고 카테고리 라디오 버튼을 선택하지 않은 경우
        if (viewModel.contentLiveData.value == "" && viewModel.categoryLiveData.value == Category.empty && viewModel.radioButtonIdLiveData.value == -1) {
            binding.radioGroup.isVisible = false
            getMovieList()
            radioListener()
            contentEditTextListener()
        }
        // 검색할 내용과 해당 카테고리에 맞는 라디오 버튼을 클릭한 경우
        else {
            binding.radioGroup.isVisible = true
            binding.radioGroup.check(viewModel.radioButtonIdLiveData.value ?: return)
            when (viewModel.categoryLiveData.value) {
                Category.empty -> {}
                Category.actor -> {
                    getMovieListForCategory(actor = viewModel.contentLiveData.value ?: return)
                }

                Category.title -> {
                    getMovieListForCategory(title = viewModel.contentLiveData.value ?: return)
                }

                Category.director -> {
                    getMovieListForCategory(director = viewModel.contentLiveData.value ?: return)
                }

                else -> {}
            }
            radioListener()
            contentEditTextListener()
        }
    }

    // EditTextView 내용변경리스너
    private fun contentEditTextListener() {
        binding.content.addTextChangedListener {
            // 아무것도 입력하지 않은 경우 랜덤한 영화 정보를 보여줌
            if (it?.isEmpty() == true) {
                getMovieList()
                viewModel.setContent("")
                viewModel.setCategory(Category.empty)
                viewModel.setRadioButtonId(-1)
                binding.radioGroup.clearCheck()
                binding.radioGroup.isVisible = false
            } else {
                binding.radioGroup.isVisible = true
            }
        }
    }

    // 라디오그룹 선택변경리스너
    private fun radioListener() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.title -> {
                    if (binding.content.text.isEmpty()) return@setOnCheckedChangeListener
                    viewModel.setContent(binding.content.text.toString())
                    viewModel.setCategory(Category.title)
                    viewModel.setRadioButtonId(R.id.title)
                    getMovieListForCategory(title = binding.content.text.toString())
                }

                R.id.actor -> {
                    if (binding.content.text.isEmpty()) return@setOnCheckedChangeListener
                    viewModel.setContent(binding.content.text.toString())
                    viewModel.setCategory(Category.actor)
                    viewModel.setRadioButtonId(R.id.actor)
                    getMovieListForCategory(actor = binding.content.text.toString())
                }

                R.id.director -> {
                    if (binding.content.text.isEmpty()) return@setOnCheckedChangeListener
                    viewModel.setContent(binding.content.text.toString())
                    viewModel.setCategory(Category.director)
                    viewModel.setRadioButtonId(R.id.director)
                    getMovieListForCategory(director = binding.content.text.toString())
                }

                else -> getMovieList()
            }
        }
    }

    // 랜덤한 영화 리스트를 가져오는 함수
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

    // 카테고리에 맞게 영화 리스트를 가져오는 함수
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