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
 *
 */
class CalcWidget(task: Multiplication, handler: AnswerHandler) extends Composite with NumpadTarget {
  val base = new HorizontalPanel
  initWidget(base)
  val panel = new VerticalPanel
  panel.setStylePrimaryName("assignment")
  val tb = new TextBox

  task match {
    case (x, y, z) => {
      panel.add(new Label(y.toString))
      panel.add(new Label(x.toString))
      val placeholder = new Label(" ")
      placeholder.setStylePrimaryName("placeholder")
      panel.add(placeholder)
      tb.setVisibleLength(5)
      tb.addKeyPressHandler((e: KeyPressEvent) => {
        if (e.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
          handler.handleAnswer(task, tb.getValue().toInt)
        }
      })
      panel.add(tb)
      Scheduler.get.scheduleDeferred(new Scheduler.ScheduledCommand() {
        def execute() {
          tb.setFocus(true)
        }
      })
    }
  }

  val numpad = new Numpad(this)
  val opLabel = new Label("*")
  opLabel.setStylePrimaryName("oplabel")
  base add (opLabel)
  base add (panel)
  base add numpad

  def handleCmd(cmd: NumpadCmd) {
    cmd match {
      case Num(n) => tb.setValue(tb.getValue + n.toString())
      case Clear() => {
        val txt = tb.getValue
        tb.setValue(txt.take(txt.length - 1))
      }
      case Enter() => handler.handleAnswer(task, tb.getValue().toInt)
    }
  }
}

/**
 *
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
  panel.add(selectorButton(4 to 100))
  panel.add(selectorButton(101 to 200))
  panel.add(selectorButton(201 to 300))
  panel.add(selectorButton(301 to 400))
  panel.add(selectorButton(401 to 500))
  panel.add(selectorButton(501 to 600))

  def selectorButton(range: Range): Widget =
    new Button(range.start + "-" + range.end, (_: ClickEvent) => {})

  initWidget(panel)
}