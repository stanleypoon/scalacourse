package common.io

import scala.io.{Source, StdIn}
import java.io.File
import java.io.PrintWriter
import java.nio.file.{Files, Paths}

object FileUtil {
  def readLines(fileName:String):Array[String] = {
    Source.fromFile(fileName).getLines().toArray
  }

  def readFile(fileName:String):String = {
    Source.fromFile(fileName).mkString
  }

  def readLine(prompt:String=""):String = {
    StdIn.readLine(prompt)
  }

  def readFormated(format:String):List[Any] = {
    StdIn.readf(format)
  }

  def write(fileName:String, contents:String):Unit = {
    val writer = new PrintWriter(new File(fileName))
    writer.write(contents)
    writer.close()
  }

  def writeLines(fileName:String, lines:Iterable[String]):Unit = {
    val writer = new PrintWriter(new File(fileName))
    lines.foreach(writer.println(_))
    writer.close()
  }

  def deleteFile(fileName:String):Unit = {
    Files.deleteIfExists(Paths.get(fileName))
  }

}