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

/**
 * CalcWidget contains the whole thing inside of it.
 */
class CalcWidget(task: BinaryOp, handler: AnswerHandler) extends Composite with NumpadTarget {
  val base = new HorizontalPanel
  initWidget(base)
  val answerField = new TextBox

  val taskPanel = {
    val panel = new VerticalPanel
    panel.setStylePrimaryName("assignment")
	panel.add(new Label(task.left.toString))
	panel.add(new Label(task.right.toString))
	val placeholder = new Label(" ")
	placeholder.setStylePrimaryName("placeholder")
	panel.add(placeholder)
	answerField.setVisibleLength(5)
	answerField.addKeyPressHandler((e: KeyPressEvent) => {
		if (e.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
			handler.handleAnswer(task, answerField.getValue().toInt)
		}
	})
	panel.add(answerField)
	Scheduler.get.scheduleDeferred(new Scheduler.ScheduledCommand() {
		def execute() {
			answerField.setFocus(true)
		}
	})
	panel
  }
  val numpad = new Numpad(this)
  val opLabel = new Label(task.opString)
  opLabel.setStylePrimaryName("oplabel")
  base add (opLabel)
  base add (taskPanel)
  base add numpad

  def handleCmd(cmd: NumpadCmd) {
    cmd match {
      case Num(n) => answerField.setValue(answerField.getValue + n.toString())
      case Clear() => {
        val txt = answerField.getValue
        answerField.setValue(txt.take(txt.length - 1))
      }
      case Enter() => handler.handleAnswer(task, answerField.getValue().toInt)
    }
  }
}

/**
 * On-screen numpad for handheld input.
 */
class Numpad(target: NumpadTarget) extends Composite {

  val g = new Grid(4, 3)
  g.setStylePrimaryName("numpad")
  val clear = new Button("c", (_: ClickEvent) => {
    target.handleCmd(Clear())

  })
  val enter = new Button("=", (_: ClickEvent) => target.handleCmd(Enter()))
  def padbutton(n: Int) =
    new Button(n.toString(), (_: ClickEvent) => target.handleCmd(Num(n)))

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