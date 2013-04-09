package org.frayer

import spock.lang.*

class Fibonacci implements GroovyInterceptable {
    def caching = false
    def cachedResults = [:]
    def callCount = 0

    def invokeMethod(String name, args) {
        if (caching && name == 'calc') {
            def fibNum = args[0]
            if (cachedResults[fibNum]) {
                return cachedResults[fibNum]
            } else {
                cachedResults[fibNum] = Fibonacci.metaClass.getMetaMethod(name, args).invoke(this, args)
                return cachedResults[fibNum]
            }
        }
        else {
            Fibonacci.metaClass.getMetaMethod(name, args).invoke(this, args)
        }
    }

    def calc(num) {
        callCount++
        if (num < 2) num
        else calc(num - 1) + calc(num - 2)
    }
}

class MemoizeSpec extends Specification {
    def "Fibonacci works as expected"() {
        given:
        def fib = new Fibonacci()

        expect:
        result == fib.calc(num)

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
        def fib = new Fibonacci()

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
        fib.calc(4)

        then:
        fib.callCount == 9
    }

    def "Fibonacci calculations are cached"() {
        given:
        def fib = new Fibonacci(caching: true)

        when:
        fib.calc(4)

        then:
        fib.callCount == 5
    }
}
