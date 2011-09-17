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
    val addApp = new AdditionApp
    addApp.next()
    RootPanel.get("screen").add(addApp.dimensionSelector)
    RootPanel.get("screen").add(addApp.screen)
  }

}

class App extends AnswerHandler
  with ResultRangeSelectorHandler {

  val screen = new SimplePanel

  val resultRangeSelector = new ResultRangeSelector(this)

  var range: List[Int] = Nil
  var tasks: List[BinaryOp] = Nil
  
  def setRange(range: Range) { 
    this.range = randomize(range.toList)
    this.tasks = Nil
    setNextFactors()
  }
  
  def setNextFactors() {
    tasks = randomize(tasks ++ factorize(range.head).map(pairToBinop)) //otherwise results tend to group
    range = range.tail
    if (tasks == Nil) setNextFactors()     
  }

  def next() {
    screen.setWidget(new CalcWidget(tasks.head, this))
    if (tasks != Nil) tasks = tasks.tail
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

class AdditionApp extends AnswerHandler 
	with DimensionSelectorHandler {
  val screen = new SimplePanel
  var tasks: List[BinaryOp] = genTasks(50, (2,2))
  val dimensionSelector = new DimensionSelector(this)  
  
  def next() {
    screen.setWidget(new CalcWidget(tasks.head, this))
    if (tasks != Nil) tasks = tasks.tail
    prepareNextTask()
  }
  
  def genTasks(n: Int, dimension: (Int, Int)) = 
    List.fill(n)(new Addition(randomNumberByLength(dimension._2), randomNumberByLength(dimension._2)))
    
  def setDimension(dimension: (Int, Int)) {
    tasks = genTasks(50, dimension)
  }
  def prepareNextTask() {
    //tasks = new Addition(randomNumberByLength(2), randomNumberByLength(2)) :: tasks  
  }

  def handleAnswer(task: BinaryOp, answer: Int) =
    if (answer == task.result) next()
  
  def handleDimensionSelect(dimension: (Int, Int)) {
    	setDimension(dimension)
    	next()
  }
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
