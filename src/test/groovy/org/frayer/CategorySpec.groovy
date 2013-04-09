package org.frayer

import spock.lang.*

class CategorySpec extends Specification {
    Garage garage

    def setup() {
        garage = new Garage()
        garage.cars << new Car(make: 'BMW', model: 'M3', year: '2005')
        garage.cars << new Car(make: 'Audi', model: 'S5', year: '2009')
        garage.cars << new Car(make: 'BMW', model: '335i', year: '2012')
    }

    @Ignore
    def "Categories add behavior to instances"() {
        when:
        def carFound = garage.findCarByMake('BMW')

        then:
        'BMW' == carFound.make
    }

    @Ignore
    def "Categories add localized behavior"() {
    }

    @Ignore
    def "Categories work on Java classes"() {
        given:
        def person = new Person('Buddy', 'Lee')

        when:
        def fullName = person.fullName

        then:
        'Buddy Lee' == fullName
    }

    @Ignore
    def "Classes annotated with Category work too"() {
        given:
        def person = new Person('Buddy', 'Lee')

        when:
        def fullName = person.fullName

        then:
        'Buddy Lee' == fullName
    }
}