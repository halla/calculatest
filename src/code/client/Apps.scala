package code.client

import MyMath._
import com.google.gwt.user.client.ui.HasWidgets
import java.util.logging.Level


/**
 * Train multiplications by result range.
 */
trait MultiplicationAppComponent  
  extends AppUiComponent 
  with AppComponent 
  with TaskComponent
  with TaskUiComponent
  with AppConf[Range] 
  {
  
  class MultiplicationApp extends Appl 
   	with RangeSelectorHandler {
    var tasks: List[Op] = List(new Multiplication(2,2))
    var range: List[Int] = (1 to 50).toList
    
    setRange(4 to 100)
  	
    override def prepareNextTask() {      
	  tasks = randomize(tasks ++ factorize(range.head).map(pairToBinop)) //randomize, otherwise results tend to group	  
	  range = range.tail	  
	  if (tasks == Nil) prepareNextTask()     
	}
    
		  
	def setRange(range: Range) { 
	  this.range = randomize(range.toList)
	  tasks = Nil
	  prepareNextTask()
	}
	  
	def handleRangeSelect(range: Range) = {
	  setRange(range)
	  next()
	}
	  
	def pairToBinop(pair: Pair[Int, Int]): BinaryOp = new Multiplication(pair._1, pair._2)   
  }    


}

/**
 * Train addition by operand dimensions (count X stringlength)
 */

trait AdditionAppComponent extends AppComponent
	with AppUiComponent		
	with TaskComponent
	with TaskUiComponent
	with AppConf[(Int, Int)]
	{
  
  class AdditionApp extends Appl
    with DimensionSelectorHandler {	  
	  var tasks: List[Op] = genTasks(50, (2,2))
	    	  	 
	  def genTasks(n: Int, dimension: (Int, Int)): List[Op] = {
	    def genOperands = List.fill(dimension._1)(randomNumberByLength(dimension._2))
	    List.fill(n)(new AdditionList(genOperands))
	  }
	  
	  def setDimension(dimension: (Int, Int)) {
	    tasks = genTasks(100, dimension)
	  }
	  
	  def handleDimensionSelect(dimension: (Int, Int)) {
	    setDimension(dimension)
	    next()
	  }
  }
}

/**
 * Multiplication by a constant.
 * 
 * TODO lots of duplication w constant addition.
 */
trait ConstantMultiplierAppComponent extends AppComponent
	with AppUiComponent		
	with TaskComponent
	with TaskUiComponent
	with AppConf[Int]
	{
  
  class ConstantMultiplierApp extends Appl
    with IntSelectorHandler {	  
	  var tasks: List[Op] = genTasks(100, 2)
  	  	 
	  def genTasks(n: Int, constant: Int): List[Op] = {	    
	    List.fill(n)(new Multiplication(constant, util.Random.nextInt(100)))
	  }
	  
	  def setConstant(constant: Int) {
	    tasks = genTasks(100, constant)
	  }
	  
	  def handleIntSelect(int: Int) {
		setConstant(int)
	    next()
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
	with AppConf[Int]
	{
  
  class AccumulatorApp extends Appl
    with IntSelectorHandler {	  
	  var tasks: List[Op] = genTasks(100, 2)
  	  	 
	  def genTasks(n: Int, constant: Int): List[Op] = {	    
	    List.fill(n)(new Addition(util.Random.nextInt(100), constant))
	  }
	  
	  def setConstant(constant: Int) {
	    tasks = genTasks(100, constant)
	  }
	  
	  def handleIntSelect(int: Int) {
		setConstant(int)
	    next()
	  }
  }
}


/**
 * Train addition adding a constant to an integer.
 */
trait EvenDivisionAppComponent extends AppComponent
	with AppUiComponent		
	with TaskComponent
	with TaskUiComponent
	with AppConf[Range]
	{
  
  class EvenDivisionApp extends Appl
    with RangeSelectorHandler {	  
	  var tasks: List[Op] = List(new Division(4,2))
	  var range: List[Int] = (1 to 50).toList
	  
	  handleRangeSelect(1 to 50)	  
	  
	  override def prepareNextTask() {
	    val dividend = range.head
	    val divisors = factorize(dividend)
	    val newTasks = (divisors zip List.fill(divisors.length * 2)(dividend))
	    val newOps: List[Op] = newTasks flatMap { a => List(new Division(a._2 ,a._1._1), new Division(a._2,a._1._2)) }  
	    tasks = randomize(tasks ++ newOps) //randomize, otherwise results tend to group
	    range = range.tail
	    if (tasks == Nil) prepareNextTask()     
	  }
	  
	  private def pairToBinop(pair: Pair[Int, Int]): BinaryOp = new Multiplication(pair._1, pair._2)
	  
	  def setRange(range: Range) { 
	    this.range = randomize(range.toList)
	    tasks = Nil
	    prepareNextTask()
	  }
	  
	  def handleRangeSelect(range: Range) {
		setRange(range)
	    next()
	  }
  }
}