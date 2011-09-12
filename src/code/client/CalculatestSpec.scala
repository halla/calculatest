package code.client

import org.specs2.mutable._
import org.junit.runner._
import MyMath._

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
  
  "factorize" should {
    "return empty list" in {      
      List(0,1,2,3) forall { n => factorize(n) == Nil} must beTrue
    }
    "return (2,2,4)" in {
      val factors: List[(Int, Int, Int)] = factorize(4)
      factors must_== List((2,2,4))
    }
    "not contain primes as result-values" in {
      val factors: List[(Int, Int, Int)] = (1 to 100).toList flatMap (n => factorize(n))
      (factors forall { a => MyMath.isPrime(a._3) }) must beFalse
    }
    "return valid factorizations" in {
      val factors: List[(Int, Int, Int)] = (1 to 100).toList flatMap factorize
      factors forall ( m => m._1 * m._2 must_== m._3 ) must_== beTrue
    }
  }

    
}
