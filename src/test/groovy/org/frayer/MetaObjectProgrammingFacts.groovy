package org.frayer

import spock.lang.Specification

class MetaObjectProgrammingFacts extends Specification {
    def "instances of Groovy classes are GroovyObjects"() {
        expect:
        (new Car()) instanceof GroovyObject
    }

    def "instances of Java classes are not GroovyObjects"() {
        expect:
        !((new Person('Buddy', 'Lee')) instanceof GroovyObject)
    }

    def "instances of Groovy classes have a metaClass property"() {
        expect:
        (new Car()).metaClass != null
    }

    def "instances of Java classes have a metaClass property"() {
        expect:
        (new Person("Buddy", "Lee")).metaClass != null
    }
}
