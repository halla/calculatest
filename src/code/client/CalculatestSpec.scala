package code.client

import org.specs2.mutable._
import org.junit.runner._


@RunWith(classOf[org.specs2.runner.JUnitRunner])
class CalculatestSpec extends SpecificationWithJUnit {

  "factorsByResultRange" should {
    "return empty list" in {
      val factors = MyMath.factorsByResultRange(0 to 3)
      factors must_== Nil
    }  
    "return a non-empty list" in {
      val factors: List[(Int, Int, Int)] = MyMath.factorsByResultRange(4 to 4)
      factors must_!= Nil
    }
    "return (2,2,4)" in {
      val factors: List[(Int, Int, Int)] = MyMath.factorsByResultRange(4 to 4)
      factors must_== List((2,2,4))
    }
    "not contain primes as result-values" in {
      val factors: List[(Int, Int, Int)] = MyMath.factorsByResultRange(0 to 100)
      (factors forall { a => MyMath.isPrime(a._3) }) must beFalse
    }
  }
  

    
}
