package ru.easycode.zerotoheroandroidtdd

class Count {
    private var step = 0

    fun increment(number: String): String {
        val intNum = number.toInt()
        return (step + intNum).toString()
    }

    companion object {
        fun Base(step: Int): Count {
            return Count().also {
                if (step in 0 downTo -1) throw IllegalStateException("step should be positive, but was $step")
                it.step = step
            }
        }
    }
}