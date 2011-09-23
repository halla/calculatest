package code.client

import com.google.gwt.user.client.ui.HasWidgets
import com.google.gwt.user.client.ui.SimplePanel
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

