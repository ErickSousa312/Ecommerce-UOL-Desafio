package spring.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CalculatorUnitTest {
    @Test
    fun testSum (){
        val calculator = Calculator()
        val sum = 2
        val sum2 = 3
        Assertions.assertThat(calculator.sumTwoNumber(sum,sum2)).isEqualTo(sum+sum2)
    }
}