package com.example.movieinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val contentLiveData = MutableLiveData<String>()
    val _contentLiveData: LiveData<String> = contentLiveData
    private val categoryLiveData = MutableLiveData<Category>()
    val _categoryLiveData: LiveData<Category> = categoryLiveData
    private val radioButtonIdLiveData = MutableLiveData<Int>()
    val _radioButtonIdLiveData: LiveData<Int> = radioButtonIdLiveData

    init {
        contentLiveData.value = ""
        categoryLiveData.value = Category.empty
        radioButtonIdLiveData.value = -1
    }
    // 내용 라이브데이터 변경
    fun setContent(content: String) {
        contentLiveData.value = content
    }
    // 카테고리 라이브데이터 변경
    fun setCategory(category: Category) {
        categoryLiveData.value = category
    }
    // 선택한 라디오버튼 아이디 라이브데이터 변경
    fun setRadioButtonId(id: Int) {
        radioButtonIdLiveData.value = id
    }
}