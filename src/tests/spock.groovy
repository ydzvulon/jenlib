@Grab('org.spockframework:spock-core:1.2')

class TestX extends spock.lang.Specification {
  def 'test method'() {
  }
}

class HelloSpock extends spock.lang.Specification {
    def "length of Spock's and his friends' names"() {
        expect:
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }
}