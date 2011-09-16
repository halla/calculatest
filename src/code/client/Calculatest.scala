package code.client;

import com.google.gwt.core.client.EntryPoint
import com.google.gwt.user.client.ui.RootPanel
import com.google.gwt.user.client.ui.Label
import com.google.gwt.user.client.ui.FlowPanel
import com.google.gwt.user.client.ui.TextBox
import com.google.gwt.user.client.ui.SimplePanel
import com.google.gwt.core.client.Scheduler

import PimpMyGwt._
import MyMath._

/**
 * Entry point
 */
class Calculatest extends EntryPoint {

  def onModuleLoad() {
    val app = new App
    RootPanel.get("controls").add(app.resultRangeSelector)
    RootPanel.get("screen").add(app.getWidget) 
    app.setRange(4 to 100)
    app.next()
  }

}

class App extends AnswerHandler
  with ResultRangeSelectorHandler {

  val screen = new SimplePanel

  val resultRangeSelector = new ResultRangeSelector(this)

  var range: List[Int] = Nil
  var factors: List[BinaryOp] = Nil
  
  def setRange(range: Range) { 
    this.range = randomize(range.toList)
    this.factors = Nil
    setNextFactors()
  }
  
  def setNextFactors() {
    factors = randomize(factors ++ factorize(range.head).map(pairToBinop)) //otherwise results tend to group
    range = range.tail
    if (factors == Nil) setNextFactors()     
  }

  def next() {
    screen.setWidget(new CalcWidget(factors.head, this))
    if (factors != Nil) factors = factors.tail
    setNextFactors()
  }

  def handleAnswer(task: BinaryOp, answer: Int) =
    if (answer == task.result) next()

  def handleResultRangeSelect(range: Range) = {
    setRange(range)
    next()
  }

  def pairToBinop(pair: Pair[Int, Int]): BinaryOp = new Multiplication(pair._1, pair._2) 
  def getWidget = screen

}


object main {
  import MyMath._
  def main(args: Array[String]): Unit = {
    var start = System.currentTimeMillis()
    var now = start

    val ranges = new Range(2, 1000, 100)
    ranges foreach (a => println(factorsByResultRange(a to (a+100)).size)) 
    println(randomize(factorsByResultRange(4 to 100)))
    now = System.currentTimeMillis()
    println(now - start)
    start = now
    println(randomize(factorsByResultRange(101 to 200)))
    now = System.currentTimeMillis()
    println(now - start)

    start = now
    println(randomize(factorsByResultRange(401 to 500)))
    now = System.currentTimeMillis()
    println(now - start)

    start = now
    println(randomize(factorsByResultRange(901 to 1000)))
    now = System.currentTimeMillis()
    println(now - start)

    start = now
    println(randomize(factorsByResultRange(2001 to 2100)))
    now = System.currentTimeMillis()
    println(now - start)
    
    start = now
    val fs = factors((1 to 100).toList)
    println(fs)    
    now = System.currentTimeMillis()
    println(now - start)

    start = now
    println(randomize(factors((2001 to 2100).toList)))
    now = System.currentTimeMillis()
    println(now - start)
    
    println(randomize( Nil ++ List(1)))
  }

}
