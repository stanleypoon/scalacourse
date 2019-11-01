package common.io

import scala.io.{Source, StdIn}
import java.io.{ByteArrayInputStream, File, PrintWriter}
import java.net.URL
import java.nio.file.{Files, Paths}

import sys.process._
import scala.language.postfixOps

object FileUtil extends App {
  def readLines(fileName:String):Array[String] = {
    Source.fromFile(fileName).getLines().toArray
  }

  def readURL(url:String):String = {
    Source.fromURL(url).mkString
  }

  def saveURL(url:String, outputFile:String):Unit = {
    new URL(url) #> new File(outputFile) !!
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

  def deadCode():Unit = {
    println("I would never be run.")
  }
}