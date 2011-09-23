package code.client;

import com.google.gwt.core.client.EntryPoint
import com.google.gwt.user.client.ui.RootPanel
import com.google.gwt.user.client.ui.SimplePanel
import com.google.gwt.user.client.ui.FlowPanel

/**
 * Entry point
 */
class Calculatest extends EntryPoint {

  def onModuleLoad() {
   val multApp = new MyMultiplicationApp   
   multApp.app.go(RootPanel.get("screen"))
   // val addApp = new AdditionApp(new AppView)   
    val addApp = new MyAddApp
    addApp.app.go(RootPanel.get("screen"))
  }

}

class MyAddApp extends AppUiComponent
	with AdditionAppComponent
	with TaskComponent
	with TaskUiComponent
{
  val app = new AdditionApp
  val op = new Addition(2,2)
  
  var taskUi: TextAnswerWidget = new CalcWidget(op)

  var task = new TaskPresenter(op) 
  
  val ui = new AppUi {   
    val screen = new FlowPanel
    val widget = screen 
  }  
  
}

class MyMultiplicationApp extends AppUiComponent
	with MultiplicationAppComponent
	with TaskComponent
	with TaskUiComponent
{
  val app = new MultiplicationApp
  val op = new Multiplication(2,2)
  
  var taskUi: TextAnswerWidget = new CalcWidget(op)

  var task = new TaskPresenter(op) 
  
  val ui = new AppUi {   
    val screen = new FlowPanel
    val widget = screen 
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
