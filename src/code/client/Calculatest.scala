package code.client;

import com.google.gwt.core.client.EntryPoint
import com.google.gwt.event.dom.client.ClickEvent
import com.google.gwt.event.dom.client.ClickHandler
import com.google.gwt.user.client.ui.Button
import com.google.gwt.user.client.Window
import com.google.gwt.user.client.ui.RootPanel
import com.google.gwt.user.client.ui.Label
import com.google.gwt.user.client.ui.FlowPanel
import com.google.gwt.user.client.ui.TextBox
import com.google.gwt.event.dom.client.KeyPressHandler
import com.google.gwt.event.dom.client.KeyPressEvent
import com.google.gwt.event.dom.client.KeyCodes
import com.google.gwt.user.client.ui.SimplePanel
import com.google.gwt.core.client.Scheduler
import scala.util.Random
import com.google.gwt.user.client.ui.Widget
import com.google.gwt.user.client.ui.HorizontalPanel
import com.google.gwt.user.client.ui.VerticalPanel
import com.google.gwt.user.client.ui.Grid
import com.google.gwt.user.client.ui.HasValue
import PimpMyGwt._
import MyMath._

/**
 * Entry point
 */
class Calculatest extends EntryPoint
  with AnswerHandler
  with ResultRangeSelectorHandler {

  val screen = new SimplePanel

  var range: Range = _
  var factors: List[Multiplication] = _

  def onModuleLoad() {
    RootPanel.get("controls").add(resultRangeSelector)
    RootPanel.get("screen").add(screen);
    setRange(4 to 100)
    next()
  }

  def setRange(range: Range) {
    this.range = range;
    this.factors = MyMath.randomize(factorsByResultRange(range))
  }

  def next() {
    screen.setWidget(new CalcWidget(factors.head, this))
    factors = factors.tail
  }

  val resultRangeSelector = new ResultRangeSelector(this)

  def handleAnswer(task: Multiplication, answer: Int) =
    if (answer == task._3) next()

  def handleResultRangeSelect(range: Range) = setRange(range); next()
}


trait AnswerHandler {
  def handleAnswer(question: Multiplication, answer: Int)
}

trait ResultRangeSelectorHandler {
  def handleResultRangeSelect(range: Range)
}

sealed trait NumpadCmd
case class Num(n: Int) extends NumpadCmd
case class Clear() extends NumpadCmd
case class Enter() extends NumpadCmd

trait NumpadTarget {
	def handleCmd(cmd: NumpadCmd)
}



/**
 *
 */
object MyMath {
  type Multiplication = (Int, Int, Int)
  def randomize[T](src: List[T]): List[T] =
    List.fill(src.size)(Random.nextInt).zip(src) sortBy (_._1) map (_._2)

  def factorsByResultRange(range: Range): List[(Int, Int, Int)] =
    (for (
      x <- 2 to Math.sqrt(range.end).intValue();
      y <- 2 to range.end / 2;
      z <- range.start to range.end;
      if (x <= y && x * y == z)
    ) yield (x, y, z)).toList

  def isPrime(n: Int): Boolean =
    if (n < 4) true else (2 to n) forall { n % _ != 0 }
}


object main {
  import MyMath._
  def main(args: Array[String]): Unit = {
    var start = System.currentTimeMillis()
    var now = start

    println(randomize(factorsByResultRange(4 to 100)))
    now = System.currentTimeMillis()
    println(now - start);
    start = now
    println(randomize(factorsByResultRange(101 to 200)))
    now = System.currentTimeMillis()
    println(now - start);

    start = now
    println(randomize(factorsByResultRange(401 to 500)))
    now = System.currentTimeMillis()
    println(now - start);

    start = now
    println(randomize(factorsByResultRange(901 to 1000)))
    now = System.currentTimeMillis()
    println(now - start);

    start = now
    println(randomize(factorsByResultRange(2001 to 2100)))
    now = System.currentTimeMillis()
    println(now - start);

  }

}