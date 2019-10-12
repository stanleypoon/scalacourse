import common.io.FileUtil
import sys.process._
import java.io.File
import java.net.{URL, URLDecoder}
import org.apache.commons.text.StringEscapeUtils.{escapeHtml4, unescapeHtml4}

def removeStopWords[T](words:Iterable[T]):Iterable[T] = {
    val STOP_WORDS = Seq("", "we", "our", "i", "not", "the", "for", "a", "and", "is", "in", "of", "to", "for", "–", "&", "$", "an", "are", "on", "as", "that", "from", "more", "most", "also", "may", "be", "by", "with", "at", "it", "or", "was", "been", "will", "can", "has", "have", "then", "their","this", "all", "not", "com", "how", "when", "what", "but", "than", "they", "into", "your", "you", "he", "she", "its", "her", "his", "where", "about", "such", "every")
    words.filter(l => !STOP_WORDS.contains(l))
  }

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

def readURL(url:String):String = {
  unescapeHtml4(FileUtil.readURL(url))
}

def saveURL(url:String, outputFile:String):Unit = {
  new URL(url) #> new File(outputFile) !!
}

def filterBy(pattern:String): (String) => Boolean = {
  (e:String) => e.equals(pattern) 
}

val trump = filterBy("trump")
val pge = filterBy("pg&e")
