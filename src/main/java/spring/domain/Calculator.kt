package spring.domain

import org.springframework.stereotype.Component

@Component
class Calculator {
    fun sumTwoNumber(x: Int, y:Int): Int {
        return x + y
    }
}