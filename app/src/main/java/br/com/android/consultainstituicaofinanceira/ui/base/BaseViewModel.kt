package br.com.android.consultainstituicaofinanceira.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(dispatcher: CoroutineDispatcher) : ViewModel(), CoroutineScope {

    // Coroutine's background job
    private val job = Job()

    override val coroutineContext: CoroutineContext = job + dispatcher

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }
}