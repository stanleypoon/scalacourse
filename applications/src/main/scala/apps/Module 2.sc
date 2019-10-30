import common.io.FileUtil

import sys.process._
import java.io.{File, PrintWriter}
import java.net.{URL, URLDecoder}

import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps
import scala.io.Source


def split(text:String):Array[String] = {
  text.split("[, \\[\\])(}{*\\^\\!|\\~?.,;:\"=+]|\\s")
}

def count(words:Iterable[String]): List[(String, Int)] = {
  words.groupBy(identity).mapValues(_.size).toList.sortBy(_._2)(Ordering[Int].reverse)
}

def print[T](items:Iterable[T], from:Int=0, until:Int=Int.MaxValue):Unit = {
  println(items.slice(from, until).mkString("\n"))
}

def readFile(fileName:String):String = {
  FileUtil.readFile(fileName)
}

split("a,b,{=c. i a")

def write(fileName:String, contents:String):Unit = {
  val writer = new PrintWriter(new File(fileName))
  writer.write(contents)
  writer.close()
}

def readWriteFile(inFileName: String, outFileName:String):Unit = {
  val ifile = Source.fromFile(inFileName)
  val fileContents = ifile.mkString
  write(outFileName, fileContents)
  ifile.close()
}

readWriteFile("/Users/spoon/projects/scalacourse/m1/notes.txt", "/Users/spoon/projects/scalacourse/m1/m3_e1.txt")

// Used while loop
def readCSV(csvFile:String):Array[Array[String]] = {
  val file = Source.fromFile(csvFile)
  val lines = file.getLines()
  val lineArrays = ArrayBuffer[Array[String]]()
  while (lines.hasNext) {
    val line = lines.next()
    lineArrays.append(line.split(","))
  }
  file.close()
  lineArrays.toArray
}

// Same as readCSV except using map(): one of the functional programming primitives
def readCSVF(csvFile:String):Iterator[Array[String]] = {
  val file = Source.fromFile(csvFile)
  val lines = file.getLines()
  lines.map(l => l.split(","))
}

val f: String => Boolean = (s:String) => true
def f(s:String):Boolean = {true}

val lines = readCSV("/Users/spoon/projects/scalacourse/common_util/testdata/cars.csv")
val first:Array[String] = lines.head
println(first.mkString(","))



