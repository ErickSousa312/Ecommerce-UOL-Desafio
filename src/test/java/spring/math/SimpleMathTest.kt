package spring.math

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SimpleMathTest {
    @ParameterizedTest
    @MethodSource("paramsTest")
    fun simpleMathSumTest(num1: Double, num2: Double, num3: Double) {
        val calc = SimpleMath()
        assertThat(calc.sum(num1, num2)).isEqualTo(num1 + num2).withFailMessage("Sum function is incorrect")
    }

    @Test
    fun simpleMathDivisionTe(){
        val a = SimpleMath()
        val num1 = 1.0
        val num2 = 0.0
        assertThrows<ArithmeticException> {
            a.division(num1, num2)
        }
    }

    private fun paramsTest(): Stream<Arguments>{
        return Stream.of(
            Arguments.of(1.0, 2.0, 3.0),
            Arguments.of(2.0, 2.0, 4.0),
            Arguments.of(4.0, 4.0, 8.0),
        )
    }
}