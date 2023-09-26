package com.example.mila.ui.activities.startup

import androidx.compose.runtime.Stable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class StartupViewModel(
    private val scope: CoroutineScope
) : ViewModel() {
    private val _watcher = MutableLiveData<Boolean?>(null)
    val watcher: LiveData<Boolean?> = _watcher


}