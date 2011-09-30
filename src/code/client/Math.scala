package code.client
import scala.util.Random

//imposcala.util.Random

//import com.google.gwt.user.client.Random

/**
 *
 */
object MyMath {
  
  
  def randomize[T](src: List[T]): List[T] =
    List.fill(src.size)(Random.nextInt).zip(src) sortBy (_._1) map (_._2)

  def factors(list: List[Int]): List[(Int, Int)] = 
    list flatMap (n => factorize(n))  
  
  def factorize(n: Int): List[(Int, Int)] = {
	(for (
      x <- 2 to Math.sqrt(n).intValue();
      y <- 2 to n / 2;
      if (x <= y && x * y == n)
    ) yield (x, y)).toList
  }
  
  def factorsByResultRange(range: Range): List[(Int, Int, Int)] =
    (for (
      x <- 2 to Math.sqrt(range.end).intValue();
      y <- 2 to range.end / 2;
      z <- range.start to range.end;
      if (x <= y && x * y == z)
    ) yield (x, y, z)).toList

  def isPrime(n: Int): Boolean =
    if (n < 4) true else (2 to n) forall { n % _ != 0 }
  
  def randomNumberByLength(length: Int): Int = {
    (Random.nextInt(9) + 1) * Math.pow(10,(length -1)).toInt +
    	List.fill(length-1)(Random.nextInt(10)).foldLeft(0)(_*10 + _)
  }
  
}

trait Op {
  def result: Int  
  val operands: List[Int]
  def opString: String
}

trait ListOp extends Op {
}

trait BinaryOp extends Op {  
  val left: Int
  val right: Int
  val operands = List(left, right)
}

class Multiplication(val left: Int, val right: Int) extends BinaryOp {
  def result = left * right 
  def opString = "*"
}

class Division(val left: Int, val right: Int) extends BinaryOp {
  def result = left / right 
  def opString = "/"  
}

class Addition(val left: Int, val right: Int) extends BinaryOp {
  def result = left + right
  def opString = "+"
}

class AdditionList(val operands: List[Int]) extends ListOp {
  def opString = "+"
  def result = operands.foldLeft(0)(_+_)
}

class Power(val left: Int, val right: Int) extends BinaryOp {
  def opString = "^"
  def result = Math.pow(left, right).toInt
}