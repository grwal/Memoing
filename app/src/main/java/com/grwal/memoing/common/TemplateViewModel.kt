package com.grwal.memoing.common

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class TemplateViewModel(application: Application) : BaseViewModel(application) {
    fun applicationContext(): Context = getApplication<Application>().applicationContext
    protected val eventSender = Channel<CommonEvent>()
    val eventReceiver = eventSender.receiveAsFlow().conflate()
    fun closeApp() {
        viewModelScope.launch { eventSender.send(CommonEvent.OnCloseApp) }
    }

    fun backScreen() {
        viewModelScope.launch {
            eventSender.send(CommonEvent.OnBackScreen)
        }
    }

    fun getString(idResource: Int) = getApplication<Application>().getString(idResource)
    sealed class CommonEvent {
        class OnNavigation(val destination: Int, val bundle: Bundle? = null) : CommonEvent()
        class OnOpenAnotherApp(val packageName: String) : CommonEvent()
        object OnCloseApp : CommonEvent()
        object OnBackScreen : CommonEvent()
        class OnShowToast(
            val content: String,
            val duration: Int,
            val type: Int,
        ) : CommonEvent()
    }
}