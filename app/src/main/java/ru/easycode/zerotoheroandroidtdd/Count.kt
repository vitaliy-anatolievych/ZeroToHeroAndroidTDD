package ru.easycode.zerotoheroandroidtdd

interface Count: UiState {
    fun initial(number: String): UiState
    fun increment(number: String): UiState
    fun decrement(number: String): UiState

    class Base(private val step: Int, private val max: Int, private val min: Int): Count {
        private var value = 0

        init {
            if (max < 1)  throw IllegalStateException("max should be positive, but was $max")
            if (step < 1) throw IllegalStateException("step should be positive, but was $step")
            if (max < step) throw IllegalStateException("max should be more than step")
            if (max < min) throw IllegalStateException("max should be more than min")
        }

        override fun initial(number: String): UiState {
            return when(number.toInt()) {
                max -> {
                    UiState.Max(number)
                }
                min -> {
                    UiState.Min(number)
                }
                else -> {
                    UiState.Base(number)
                }
            }
        }

        override fun increment(number: String): UiState {
            value = number.toInt() + step
            return if (value + step <= max) {
                UiState.Base(value.toString())
            } else {
                UiState.Max(value.toString())
            }
        }

        override fun decrement(number: String): UiState {
            value = number.toInt() - step
            return if (value - step >= min) {
                UiState.Base(value.toString())
            } else {
                UiState.Min(value.toString())
            }
        }
    }
}

interface UiState {
    data class Base(val text: String): UiState
    data class Max(val text: String): UiState
    data class Min(val text: String): UiState
}