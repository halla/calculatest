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

  var range: Range = _
  var factors: List[Multiplication] = _
  def setRange(range: Range) {
    this.range = range
    this.factors = MyMath.randomize(factorsByResultRange(range))
  }

  def next() {
    screen.setWidget(new CalcWidget(factors.head, this))
    factors = factors.tail
  }

  def handleAnswer(task: Multiplication, answer: Int) =
    if (answer == task._3) next()

  def handleResultRangeSelect(range: Range) = {
    setRange(range)
    next()
  }

  def getWidget = screen

}

object main {
  import MyMath._
  def main(args: Array[String]): Unit = {
    var start = System.currentTimeMillis()
    var now = start

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
  }

}
