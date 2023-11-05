package ru.easycode.zerotoheroandroidtdd

interface Count: UiState {
    fun increment(number: String): UiState

    class Base(private val step: Int, private val max: Int): Count {
        private var value = 0

        init {
            if (max < 1)  throw IllegalStateException("max should be positive, but was $max")
            if (step < 1) throw IllegalStateException("step should be positive, but was $step")
            if (max < step) throw IllegalStateException("max should be more than step")
        }

        override fun increment(number: String): UiState {
            value = number.toInt() + step
            return if (value + step <= max) {
                UiState.Base(value.toString())
            } else {
                UiState.Max(value.toString())
            }
        }
    }
}

interface UiState {
    data class Base(val text: String): UiState
    data class Max(val text: String): UiState
}