package code.client

import com.google.gwt.user.client.ui.HasWidgets
import com.google.gwt.user.client.ui.SimplePanel
import MyMath._
import com.google.gwt.user.client.ui.HasValue
import com.google.gwt.user.client.ui.TextBox
import com.google.gwt.event.dom.client.KeyPressEvent
import com.google.gwt.dom.client.NativeEvent
import com.google.gwt.dom.client.Document
import com.google.gwt.event.dom.client.DomEvent
import com.google.gwt.event.dom.client.KeyCodes
import com.google.gwt.user.client.ui.HorizontalPanel
import PimpMyGwt._
import com.google.gwt.user.client.ui.Widget
import com.google.gwt.user.client.ui.Panel
import com.google.gwt.user.client.ui.Label


trait HasTasks {
  var currentTask: Op
  var tasks: List[Op]	
  def prepareNextTask() {}
}

trait AppUiComponent {
  val ui: AppUi 
  
  trait AppUi {
	val screen: HasWidgets
	val widget: Widget
  }
}



trait AppComponent {
  self: AppUiComponent with TaskUiComponent with TaskComponent =>
  val app: Appl
  
  trait Appl extends HasTasks {  	  
	  var currentTask: Op = _
	  
	  def go(container: HasWidgets)  
	  
	  def next() {
	    currentTask = tasks.head	    
	    ui.screen.clear	   
	    taskUi = new CalcWidget(currentTask)
	    task = new TaskPresenter(currentTask)
	    ui.screen.add(taskUi.widget)
	    ui.screen.add(new Label("moi"))
	    if (tasks != Nil) tasks = tasks.tail
	    prepareNextTask()
	  }  
	}
}

trait TaskUiComponent {
  var taskUi: TextAnswerWidget
  
}

trait TaskComponent {
  self: TaskUiComponent with AppComponent =>
    
  var task: TaskPresenter
	
  class TaskPresenter(currentTask: Op) 
		extends AnswerHandler	
		with NumpadHandler {
	  
	  val numpad = new Numpad
	  taskUi.base.add(numpad)
	  bind
	  
	  def bind = {    
	    taskUi.answerField.addKeyPressHandler((e: KeyPressEvent) => {
			if (e.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
			  handleAnswer(currentTask, taskUi.answerField.getValue().toInt)
			}
		})
		numpad.target = Some(this)
	  }
	  
	  def handleAnswer(task: Op, answer: Int) = 
	    if (answer == task.result) app.next()
	    
	  def handleCmd(cmd: NumpadCmd) {
		cmd match {
			case Num(n) => taskUi.answerField.setValue(taskUi.answerField.getValue + n.toString())
			case Clear() => {
				val txt = taskUi.answerField.getValue
						taskUi.answerField.setValue(txt.take(txt.length - 1))
			}
			case Enter() => {
			  handleAnswer(currentTask, taskUi.answerField.getValue().toInt)
			}
		}
	  }
	}
}

/**
 * Train multiplications by result range.
 *//*
class MultiplicationApp(val view: AppDisplay) extends App	
	with ResultRangeSelectorHandler {

  val resultRangeSelector = new ResultRangeSelector(this)

  var range: List[Int] = Nil
  var tasks: List[Op] = Nil
  
  
  def go(container: HasWidgets) {
    container.add(resultRangeSelector)
    container.add(view.widget)
    setRange(4 to 100)
    next()
  }
  
  def setRange(range: Range) { 
    this.range = randomize(range.toList)
    this.tasks = Nil
    prepareNextTask()
  }
  
  override def prepareNextTask() {
    tasks = randomize(tasks ++ factorize(range.head).map(pairToBinop)) //randomize, otherwise results tend to group
    range = range.tail
    if (tasks == Nil) prepareNextTask()     
  }

  def handleResultRangeSelect(range: Range) = {
    setRange(range)
    next()
  }

  def pairToBinop(pair: Pair[Int, Int]): BinaryOp = new Multiplication(pair._1, pair._2)   

}*/

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
	    tasks = genTasks(50, dimension)
	  }
	  
	  def handleDimensionSelect(dimension: (Int, Int)) {
	    	setDimension(dimension)
	    	app.next()
	  }
  }
}


