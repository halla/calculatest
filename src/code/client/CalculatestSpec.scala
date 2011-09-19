package code.client

import org.specs2.mutable._
import org.junit.runner._
import MyMath._
import org.easymock.EasyMock._
import com.google.gwt.user.client.ui.TextBox
import com.google.gwt.user.client.ui.HasValue

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
      val factors: List[(Int, Int)] = factorize(4)
      factors must_== List((2,2))
    }
    "not contain primes as result-values" in {
      val factors: List[(Int, Int)] = (1 to 100).toList flatMap (n => factorize(n))
      (factors forall { a => MyMath.isPrime(a._1 * a._2) }) must beFalse
    }
    /*"return valid factorizations" in {
      val factors: List[(Int, Int)] = (1 to 100).toList flatMap factorize
      factors forall ( m => m._1 * m._2 must_== m._3 ) must_== beTrue
    }*/ //TODO 
  }
  
  "randomNumByLength" should {
    "return numbers with matching string length" in {
      val lengths = 1 to 10
      val nums = for (n <- lengths) yield (n, randomNumberByLength(n).toString.length)
      val expected = lengths.toList.zip(lengths)
      expected must_== nums.toList
    }
  }

  "numpad click" should {    
    "result in numpadcmd" in {
      val target = createMock(classOf[HasValue[String]])
      val appView = createMock(classOf[AppView])
      val app = new AdditionApp(appView)
      //val numpad = createStrictMock(classOf[NumpadBridge]) //new NumpadBridge(target)
      0 must_== 1
    }
  }
    
}
