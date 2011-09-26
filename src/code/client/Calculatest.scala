package code.client;

import com.google.gwt.core.client.EntryPoint
import com.google.gwt.user.client.ui.RootPanel
import com.google.gwt.user.client.ui.SimplePanel
import com.google.gwt.user.client.ui.FlowPanel
import com.google.gwt.user.client.ui.Label

/**
 * Entry point
 */
class Calculatest extends EntryPoint {

  def onModuleLoad() {
    val screen = RootPanel.get("screen")  
    (new MyConstantMultiplierApp).app.go(screen)
    (new MyMultiplicationApp).app.go(screen)    
    (new MyAddApp).app.go(screen)    
    (new MyAccumulatorApp).app.go(screen)    
    (new MyEvenDivisionApp).app.go(screen)
  }

}

abstract class MyCalcApp extends AppUiComponent  
	with AppComponent
	with AppConf[Any]
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
	with AdditionAppComponent with AppConf[(Int, Int)] {
  val app = new AdditionApp 
  val confWidget = new DimensionSelector(app)
}

class MyMultiplicationApp extends MyCalcApp
	with MultiplicationAppComponent with AppConf[Range] {
  val app = new MultiplicationApp
  val confWidget = new RangeSelector(app)
}

class MyConstantMultiplierApp extends MyCalcApp 
	with ConstantOpAppComponent 
	with AppConf[Int] {
  val app = new ConstantOpApp {
     def genTasks(n: Int, constant: Int): List[Op] = {	    
	    List.fill(n)(new Multiplication(constant, util.Random.nextInt(100)))
	  }
  }
  val confWidget = new ConstantSelector(app)
}
class MyAccumulatorApp extends MyCalcApp 
	with ConstantOpAppComponent 
	with AppConf[Int] {
  val app = new ConstantOpApp {
    def genTasks(n: Int, constant: Int): List[Op] = {	    
	  List.fill(n)(new Addition(util.Random.nextInt(100), constant))
	}
  }
  val confWidget = new ConstantSelector(app)
}

class MyEvenDivisionApp extends MyCalcApp
	with EvenDivisionAppComponent 
	with AppConf[Range] {
  val app = new EvenDivisionApp
  val confWidget = new RangeSelector(app)
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
