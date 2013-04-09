package org.frayer

import org.codehaus.groovy.runtime.metaclass.*
import spock.lang.*

class MetaClassInjectionSpec extends Specification {
    def mcr
    def garage
    def cars = []

    def setup() {
        mcr = new MetaClassRegistryImpl()
        garage = new Garage()
        cars << new Car(make: 'BMW', model: 'M3', year: 2005)
        cars << new Car(make: 'Audi', model: 'S5', year: 2009)
        cars << new Car(make: 'BMW', model: '335i', year: 2012)
        garage.cars = cars
    }

    @Ignore
    def "MetaClass can add behavior to classes"() {
        when:
        def carFound = garage.findCarByMake('BMW')

        then:
        'BMW' == carFound.make
    }

    @Ignore
    def "MetaClass can add behavior to instances"() {
        when:
        def carFound = garage.findCarByMake('BMW')

        then:
        'BMW' == carFound.make
    }

    @Ignore
    def "Method names can be interpreted at runtime"() {
        when:
        def garage = new Garage(cars: cars)

        then:
        'BMW' == garage.findCarByMake('BMW').make
        2009 == garage.findCarByModel('S5').year
        'Audi' == garage.findCarByYear(2009).make

        cleanup:
        Garage.metaClass = null
    }

    @Ignore
    def "MetaClass method injection works on Java classes"() {
        when:
        def person = new Person('Buddy', 'Lee')

        then:
        'Buddy Lee' == person.fullName()
    }

    @Ignore
    def "MetaClassRegistry keeps information on POGO MetaClasses"() {
        when:
        Garage.metaClass.findCarByMake = { null }

        then:
        mcr.getMetaClass(Garage).pickMethod('nonExistentMethod', null) == null
        mcr.getMetaClass(Garage).pickMethod('findCarByMake', null) != null
    }

    @Ignore
    def "MetaClassRegistry keeps information on POJO MetaClasses"() {
        when:
        Person.metaClass.getFullName = { null }

        then:
        mcr.getMetaClass(Person).pickMethod('nonExistentMethod', null) == null
        mcr.getMetaClass(Person).pickMethod('getFullName', null) != null
    }
}