package com.example.movieinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val _contentLiveData = MutableLiveData<String>()
    val contentLiveData: LiveData<String> = this._contentLiveData
    private val _categoryLiveData = MutableLiveData<Category>()
    val categoryLiveData: LiveData<Category> = this._categoryLiveData
    private val _radioButtonIdLiveData = MutableLiveData<Int>()
    val radioButtonIdLiveData: LiveData<Int> = this._radioButtonIdLiveData

    init {
        this._contentLiveData.value = ""
        this._categoryLiveData.value = Category.empty
        this._radioButtonIdLiveData.value = -1
    }
    // 내용 라이브데이터 변경
    fun setContent(content: String) {
        this._contentLiveData.value = content
    }
    // 카테고리 라이브데이터 변경
    fun setCategory(category: Category) {
        this._categoryLiveData.value = category
    }
    // 선택한 라디오버튼 아이디 라이브데이터 변경
    fun setRadioButtonId(id: Int) {
        this._radioButtonIdLiveData.value = id
    }
}