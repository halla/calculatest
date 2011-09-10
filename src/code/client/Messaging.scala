package code.client

import MyMath._

trait AnswerHandler {
  def handleAnswer(question: Multiplication, answer: Int)
}

trait ResultRangeSelectorHandler {
  def handleResultRangeSelect(range: Range)
}

sealed trait NumpadCmd
case class Num(n: Int) extends NumpadCmd
case class Clear() extends NumpadCmd
case class Enter() extends NumpadCmd

trait NumpadTarget {
	def handleCmd(cmd: NumpadCmd)
}