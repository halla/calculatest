package code.client;

import com.google.gwt.core.client.EntryPoint
import com.google.gwt.event.dom.client.ClickEvent
import com.google.gwt.event.dom.client.ClickHandler
import com.google.gwt.user.client.ui.Button
import com.google.gwt.user.client.Window
import com.google.gwt.user.client.ui.RootPanel
import com.google.gwt.user.client.ui.Label
import com.google.gwt.user.client.ui.FlowPanel
import com.google.gwt.user.client.ui.TextBox
import com.google.gwt.event.dom.client.KeyPressHandler
import com.google.gwt.event.dom.client.KeyPressEvent
import com.google.gwt.event.dom.client.KeyCodes
import com.google.gwt.user.client.ui.SimplePanel
import com.google.gwt.core.client.Scheduler
import scala.util.Random
import com.google.gwt.user.client.ui.Widget
import com.google.gwt.user.client.ui.HorizontalPanel
import com.google.gwt.user.client.ui.VerticalPanel

class Calculatest extends EntryPoint {
  type Multiplication = (Int, Int, Int)
  val screen = new SimplePanel

  var range: Range = _
  var factors: List[Multiplication] = _

  def onModuleLoad() {
    RootPanel.get("controls").add(resultRangeSelector)
    RootPanel.get("screen").add(screen);

    setRange(4 to 100)
    next()
  }


  def setRange(range: Range) {
    this.range = range;
    this.factors = MyMath.randomize(MyMath.factorsByResultRange(range))
  }

  def next() {
    screen.setWidget(buildWidget(factors.head))
    factors = factors.tail
  }

  val resultRangeSelector: Widget = {
    val panel = new HorizontalPanel
    panel.add(buildButton(4 to 100))
    panel.add(buildButton(101 to 200))
    panel.add(buildButton(201 to 300))
    panel.add(buildButton(301 to 400))
    panel.add(buildButton(401 to 500))
    panel.add(buildButton(501 to 600))
    
    def buildButton(range: Range): Widget =
      new Button(range.start + "-" + range.end, (_: ClickEvent) => { setRange(range); next() })

    panel
  }

  def buildWidget(task: Multiplication) = {
    val panel = new VerticalPanel
    task match {
      case (x, y, z) => {
        panel.add(new Label(y.toString))
        panel.add(new Label(x.toString))
        val placeholder = new Label(" ")
        placeholder.setStylePrimaryName("placeholder")
        panel.add(placeholder)
        val tb = new TextBox
        tb.setVisibleLength(5)
        tb.addKeyPressHandler((e: KeyPressEvent) => {
          if (e.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
            val res = tb.getValue().toInt
            if (res == z) next()
          }
        })
        panel.add(tb)
        Scheduler.get.scheduleDeferred(new Scheduler.ScheduledCommand() {
          def execute() {
            tb.setFocus(true);
          }
        })
      }
    }

    panel
  }

  implicit def clickHandler(f: ClickEvent => Unit): ClickHandler = new ClickHandler {
    def onClick(event: ClickEvent) = f(event)
  }

  implicit def keyPressHandler[T](fn: KeyPressEvent => Unit): KeyPressHandler =
    new KeyPressHandler {
      def onKeyPress(event: KeyPressEvent): Unit = fn(event)
    }

}

object MyMath {
  def randomize[T](src: List[T]): List[T] =
    List.fill(src.size)(Random.nextInt).zip(src) sortBy (_._1) map (_._2)

  def factorsByResultRange(range: Range): List[(Int, Int, Int)] =
    (for (
      x <- 2 to range.end / 2;
      y <- 2 to range.end / 2;
      z <- range.start to range.end;
      if (x <= y && x * y == z)
    ) yield (x, y, z)).toList

}




object main {
	import MyMath._
	def main(args: Array[String]): Unit = {
			println(randomize(factorsByResultRange(4 to 100)))
			println(randomize(factorsByResultRange(101 to 200)))
	}
	
}