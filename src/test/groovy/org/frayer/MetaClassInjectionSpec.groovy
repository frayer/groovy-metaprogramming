package org.frayer

import org.codehaus.groovy.runtime.metaclass.*
import spock.lang.*

class MetaClassInjectionSpec extends Specification {
    def garage
    def cars = []

    def setup() {
        garage = new Garage()
        cars << new Car(make: 'BMW', model: 'M3', year: 2005)
        cars << new Car(make: 'Audi', model: 'S5', year: 2009)
        cars << new Car(make: 'BMW', model: '335i', year: 2012)
        garage.cars = cars
    }

    def cleanup() {
    }

    def "MetaClass can add behavior to classes"() {
        def carFound

        when:
        Garage.metaClass.findCarByMake = { make -> cars.find { car -> car.make == make } }
        def garage = new Garage(cars: cars)
        carFound = garage.findCarByMake('BMW')

        then:
        'BMW' == carFound.make

        cleanup:
        Garage.metaClass = null
    }

    def "MetaClass can add behavior to instances"() {
        def carFound

        when:
        garage.metaClass.findCarByMake = { make -> cars.find { it.make == make } }
        carFound = garage.findCarByMake('BMW')

        then:
        'BMW' == carFound.make
    }

    def "Method names can be interpreted at runtime"() {
        def carFound

        when:
        Garage.metaClass.methodMissing = { String name, args ->
            def searchParameter = (name =~ /findCarBy(.*)/)[0][1].toLowerCase()
            cars.find { car -> car[searchParameter] == args[0] }
        }
        def garage = new Garage(cars: cars)
        carFound = garage.findCarByMake('BMW')

        then:
        'BMW' == carFound.make
        'Audi' == garage.findCarByYear(2009).make

        cleanup:
        Garage.metaClass = null
    }

    def "MetaClass method injection works on Java classes"() {
        def fullName

        when:
        Person.metaClass.fullName = { -> "$firstName $lastName" }
        def person = new Person('Buddy', 'Lee')

        then:
        'Buddy Lee' == person.fullName()

        cleanup:
        Person.metaClass.getFullName = { -> throw new MissingMethodException() }
    }

    def "MetaClassRegistry keeps information on POJO MetaClasses"() {
        def mcr = new MetaClassRegistryImpl()

        when:
        Person.metaClass.getFullName = { -> "$firstName $lastName" }

        then:
        mcr.getMetaClass(Person.class).pickMethod('nonExistentMethod', null) == null
        mcr.getMetaClass(Person.class).pickMethod('getFullName', null) != null

        cleanup:
        Person.metaClass.getFullName = { -> throw new MissingMethodException() }
    }
}