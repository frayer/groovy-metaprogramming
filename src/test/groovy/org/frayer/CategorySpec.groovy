package org.frayer

import spock.lang.Specification

class GarageCategory {
    static Car findCarByMake(Garage garage, make) {
        garage.cars.find { it.make == make }
    }
}

class PersonCategory {
    static String getFullName(Person person) {
        "${person.firstName} ${person.lastName}"
    }
}

@Category(Person)
class PersonCategoryAnnotated {
    def getFullName() { "${this.firstName} ${this.lastName}" }
}

class CategorySpec extends Specification {
    Garage garage

    def setup() {
        garage = new Garage()
        garage.cars << new Car(make: 'BMW', model: 'M3', year: '2005')
        garage.cars << new Car(make: 'Audi', model: 'S5', year: '2009')
        garage.cars << new Car(make: 'BMW', model: '335i', year: '2012')
    }

    def "Categories add behavior to instances"() {
        def carFound

        when:
        use(GarageCategory) {
            carFound = garage.findCarByMake('BMW')
        }

        then:
        'BMW' == carFound.make
    }

    def "Categories add localized behavior"() {
        when:
        use(GarageCategory) {
            garage.findCarByMake('BMW')
        }
        garage.findCarByMake('BMW') // This throws a MissingMethodException outside of the "use" block.

        then:
        thrown(groovy.lang.MissingMethodException)
    }

    def "Categories work on Java classes"() {
        def fullName

        given:
        def person = new Person('Buddy', 'Lee')

        when:
        use(PersonCategory) {
            fullName = person.fullName
        }

        then:
        'Buddy Lee' == fullName
    }

    def "Classes annotated with Category work too"() {
        def fullName

        given:
        def person = new Person('Buddy', 'Lee')

        when:
        use(PersonCategoryAnnotated) {
            fullName = person.fullName
        }

        then:
        'Buddy Lee' == fullName
    }
}