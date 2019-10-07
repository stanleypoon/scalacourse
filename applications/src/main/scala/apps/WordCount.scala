package apps

import common.io.FileUtil

object WordCount {
  def getWordCount(fileName:String): List[(String, Int)] = {
    val text = FileUtil.readFile(fileName)
    getWordCountFromString(text)
  }

  def getWordCountFromString(text:String): List[(String, Int)] = {
    val words = text.split("[, \\[\\])(}{&*\\^%$#@\\!|\\~?.,;:\"=+]")
    words.groupBy(identity).mapValues(_.length).toList.sortBy(_._2)(Ordering[Int].reverse)
  }

  def main(args:Array[String]):Unit = {
    println(getWordCountFromString("1,2,3=4,words?;$500 i am good, you are good").mkString("\n"))
  }

}
