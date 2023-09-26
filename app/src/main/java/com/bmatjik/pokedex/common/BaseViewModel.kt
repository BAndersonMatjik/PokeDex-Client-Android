package com.bmatjik.pokedex.common

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T>: ViewModel() {
    abstract fun onEvent(event: T)
}