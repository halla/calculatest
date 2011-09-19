package code.client;

import com.google.gwt.core.client.EntryPoint
import com.google.gwt.user.client.ui.RootPanel

/**
 * Entry point
 */
class Calculatest extends EntryPoint {

  def onModuleLoad() {
    val multApp = new MultiplicationApp    
    multApp.go(RootPanel.get("screen"))
    val addApp = new AdditionApp    
    addApp.go(RootPanel.get("screen"))
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
