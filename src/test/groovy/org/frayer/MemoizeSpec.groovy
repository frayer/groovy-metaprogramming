package org.frayer

import spock.lang.*

class Fibonacci {
    def callCount = 0

    def fib(num) {
        callCount++
        if (num < 2) num
        else fib(num - 1) + fib(num - 2)
    }
}

class MemoizeSpec extends Specification {
    def "Fibonacci works as expected"() {
        given:
        def fibonacci = new Fibonacci()

        expect:
        result == fibonacci.fib(num)

        where:
        result | num
        0      | 0
        1      | 1
        1      | 2
        2      | 3
        3      | 4
        5      | 5
    }

    def "Fibonacci calculations are not cached"() {
        given:
        def fibonacci = new Fibonacci()

        when:
        /*
            fib(4) -- fib(3) ---- fib(2) -- fib(1) -- 1
                   \         \           \_ fib(0) -- 0
                    \         \
                     \         \__ fib(1) -- 1
                      \
                       \__ fib(2) -- fib(1) -- 1
                                  \_ fib(0) -- 0
        */
        fibonacci.fib(4)

        then:
        fibonacci.callCount == 9
    }

    @Ignore
    def "Fibonacci calculations are cached"() {
        given:
        def fibonacci = new Fibonacci()

        when:
        fibonacci.fib(4)

        then:
        fibonacci.callCount == 5
    }
}
