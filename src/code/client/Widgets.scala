package code.client
import com.google.gwt.user.client.ui.Widget
import com.google.gwt.user.client.ui.Grid
import com.google.gwt.user.client.ui.Button
import com.google.gwt.user.client.ui.TextBox
import com.google.gwt.user.client.ui.VerticalPanel
import com.google.gwt.user.client.ui.HorizontalPanel
import com.google.gwt.user.client.ui.Label
import com.google.gwt.event.dom.client.ClickEvent
import com.google.gwt.event.dom.client.KeyPressEvent
import MyMath._
import PimpMyGwt._
import com.google.gwt.core.client.Scheduler
import com.google.gwt.event.dom.client.KeyCodes
import com.google.gwt.user.client.ui.Composite
import com.google.gwt.dom.client.NativeEvent
import com.google.gwt.event.dom.client.DomEvent
import com.google.gwt.dom.client.Document
import com.google.gwt.user.client.ui.SimplePanel


class AppView extends AppDisplay {
  val screen = new SimplePanel
  val widget = screen
}


/**
 * CalcWidget contains the whole thing inside of it.
 */
class CalcWidget(task: Op) 
	extends Composite
	with TaskDisplay
	{
  val base = new HorizontalPanel
  val answerField = buildAnswerField()
  initWidget(base)

  val taskPanel = {
    val panel = new VerticalPanel
    panel.setStylePrimaryName("assignment")
    panel.add(opWidget(task))    
	panel.add(answerField)
	panel
  }

  val opLabel = new Label(task.opString)
  opLabel.setStylePrimaryName("oplabel")
  base add (opLabel)
  base add (taskPanel)  
  
  def buildAnswerField() = {
  	val answerField = new TextBox
	answerField.setVisibleLength(5)
	
	Scheduler.get.scheduleDeferred(new Scheduler.ScheduledCommand() {
		def execute() {
			answerField.setFocus(true)
		}
	})
    answerField
  }

  def opWidget(op: Op) = {
    val panel = new VerticalPanel
    panel.setStylePrimaryName("opPanel")
    op.operands.foreach(o => panel.add(new Label(o.toString)))    	
	val placeholder = new Label(" ")
	placeholder.setStylePrimaryName("placeholder")
	panel.add(placeholder)
	panel
  }
      
}

trait NumpadDisplay {
  var target: Option[NumpadHandler]
}

/**
 * On-screen numpad for handheld input.
 */
class Numpad extends Composite with NumpadDisplay {
  var target: Option[NumpadHandler] = None
  
  val g = new Grid(4, 3)
  g.setStylePrimaryName("numpad")
  val clear = new Button("c", (_: ClickEvent) => {    
    target match {
      case Some(t) => t.handleCmd(Clear())  
      case None =>
    }
  })
  
  val enter = new Button("=", (_: ClickEvent) => {
    target match {
      case Some(t) => t.handleCmd(Enter())
      case None =>
    }    
  })
  def padbutton(n: Int) =
    new Button(n.toString(), (_: ClickEvent) => {
      target match {
        case Some(t) => t.handleCmd(Num(n))
        case None =>
      }
    })

  g.setWidget(0, 0, padbutton(7))
  g.setWidget(0, 1, padbutton(8))
  g.setWidget(0, 2, padbutton(9))
  g.setWidget(1, 0, padbutton(4))
  g.setWidget(1, 1, padbutton(5))
  g.setWidget(1, 2, padbutton(6))
  g.setWidget(2, 0, padbutton(1))
  g.setWidget(2, 1, padbutton(2))
  g.setWidget(2, 2, padbutton(3))
  g.setWidget(3, 0, padbutton(0))
  g.setWidget(3, 1, enter)
  g.setWidget(3, 2, clear)
  initWidget(g)
}


class ResultRangeSelector(handler: ResultRangeSelectorHandler) extends Composite {
  val panel = new HorizontalPanel
  val ranges = new Range(1, 600, 50).toList
  ranges foreach (a => panel.add(selectorButton(a to a+50)))

  def selectorButton(range: Range): Widget =
    new Button(range.start + "-" + range.end, (_: ClickEvent) => { handler.handleResultRangeSelect(range)})

  initWidget(panel)
}

class DimensionSelector(handler: DimensionSelectorHandler) extends Composite {
  type Dimension = (Int, Int)
  val panel = new HorizontalPanel
  val dimensions: List[Dimension] = (for(
      n <- 2 to 5;
      length <- 1 to 5) yield (n,length)).toList
  dimensions foreach (a => panel.add(selectorButton(a)))

  def selectorButton(dimension: Dimension): Widget =
    new Button(dimension._1 + "x" + dimension._2, (_: ClickEvent) => { handler.handleDimensionSelect(dimension)})

  initWidget(panel)
}