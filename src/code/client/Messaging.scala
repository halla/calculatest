package code.client

import MyMath._

trait AnswerHandler {
  def handleAnswer(question: Op, answer: Int)
}

trait RangeSelectorHandler {
  def handleResultRangeSelect(range: Range)
}

trait DimensionSelectorHandler {
  def handleDimensionSelect(dimension: (Int, Int))
}

trait IntSelectorHandler {
  def handleIntSelect(int: Int)
}

sealed trait NumpadCmd
case class Num(n: Int) extends NumpadCmd
case class Clear() extends NumpadCmd
case class Enter() extends NumpadCmd

trait NumpadHandler {
	def handleCmd(cmd: NumpadCmd)
}