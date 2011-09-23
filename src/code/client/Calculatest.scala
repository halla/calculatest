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
    val screen = RootPanel.get("screen")
    
    (new MyMultiplicationApp).app.go(screen)
    (new MyAddApp).app.go(screen)
    (new MyAccumulatorApp).app.go(screen)    
    (new MyEvenDivisionApp).app.go(screen)
  }

}

abstract class MyCalcApp extends AppUiComponent  
	with AppComponent
	with TaskComponent
	with TaskUiComponent {
	
	val op = new Multiplication(2,2)
	
	var taskUi: TextAnswerWidget = new CalcWidget(op)
	
	var task = new TaskPresenter(op) 
	
	val ui = new AppUi {   
		val screen = new FlowPanel
				val widget = screen 
	}
	
}

class MyAddApp extends MyCalcApp
	with AdditionAppComponent {
  val app = new AdditionApp  
}

class MyMultiplicationApp extends MyCalcApp
	with MultiplicationAppComponent {
  val app = new MultiplicationApp
}

class MyAccumulatorApp extends MyCalcApp with AccumulatorAppComponent {
  val app = new AccumulatorApp 
  
}

class MyEvenDivisionApp extends MyCalcApp
	with EvenDivisionAppComponent {
  val app = new EvenDivisionApp
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
