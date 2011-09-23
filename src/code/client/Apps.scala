package code.client

import MyMath._
import com.google.gwt.user.client.ui.HasWidgets

/**
 * Train multiplications by result range.
 */
trait MultiplicationAppComponent extends RangeSelectorHandler  
  with AppUiComponent 
  with AppComponent 
  with TaskComponent
  with TaskUiComponent
  {
  
  class MultiplicationApp extends Appl {  
    var tasks: List[Op] = Nil
	  def go(container: HasWidgets) {
	    container.add(resultRangeSelector)
	    container.add(ui.widget)
	    setRange(4 to 100)
	    next()
	  }
	  override def prepareNextTask() {
	    tasks = randomize(tasks ++ factorize(range.head).map(pairToBinop)) //randomize, otherwise results tend to group
	    range = range.tail
	    if (tasks == Nil) prepareNextTask()     
	  }
   
  }
  val resultRangeSelector = new RangeSelector(this)

  var range: List[Int] = Nil
  
  def setRange(range: Range) { 
    this.range = randomize(range.toList)
    app.tasks = Nil
    app.prepareNextTask()
  }

  def handleResultRangeSelect(range: Range) = {
    setRange(range)
    app.next()
  }

  def pairToBinop(pair: Pair[Int, Int]): BinaryOp = new Multiplication(pair._1, pair._2)   

}

/**
 * Train addition by operand dimensions (count X stringlength)
 */

trait AdditionAppComponent extends AppComponent
	with AppUiComponent		
	with TaskComponent
	with TaskUiComponent	
	{
  
  class AdditionApp extends Appl
    with DimensionSelectorHandler {	  
	  var tasks: List[Op] = genTasks(50, (2,2))
	  val dimensionSelector = new DimensionSelector(this)  
	  
	  def go(container: HasWidgets) = {
	    container.add(dimensionSelector)
	    container.add(ui.widget)
	    app.next()
	  }
	  
	  def genTasks(n: Int, dimension: (Int, Int)): List[Op] = {
	    def genOperands = List.fill(dimension._1)(randomNumberByLength(dimension._2))
	    List.fill(n)(new AdditionList(genOperands))
	  }
	  
	  def setDimension(dimension: (Int, Int)) {
	    tasks = genTasks(100, dimension)
	  }
	  
	  def handleDimensionSelect(dimension: (Int, Int)) {
	    	setDimension(dimension)
	    	app.next()
	  }
  }
}

/**
 * Train addition adding a constant to an integer.
 */
trait AccumulatorAppComponent extends AppComponent
	with AppUiComponent		
	with TaskComponent
	with TaskUiComponent	
	{
  
  class AccumulatorApp extends Appl
    with IntSelectorHandler {	  
	  var tasks: List[Op] = genTasks(50, 2)
	  val constantSelector = new ConstantSelector(this)  
	  
	  def go(container: HasWidgets) = {
	    container.add(constantSelector)
	    container.add(ui.widget)
	    app.next()
	  }
	  
	  def genTasks(n: Int, constant: Int): List[Op] = {	    
	    List.fill(n)(new Addition(util.Random.nextInt(100), constant))
	  }
	  
	  def setConstant(constant: Int) {
	    tasks = genTasks(100, constant)
	  }
	  
	  def handleIntSelect(int: Int) {
		  setConstant(int)
	      app.next()
	  }
  }
}
