import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.matching.Regex

// Module 5 Exercise **********************
// Please try to anticipate the output from the following lines
// It is to clarify the concept of function declarations and definitions
// in the form of value, function.
// Using the do Nothing function as an example.

def doNothing(a:Any):Unit = ()

val whatIsThis = (1 to 3).map(doNothing)
println(whatIsThis.mkString(","))

val willThisBeTheSame = (1 to 3).map(_ => ())
println(willThisBeTheSame.mkString(","))

(1 to 3).foreach(doNothing)
(1 to 3).foreach(_ => ())

val doNothingFunctionAsValue = (a:Any) => ()

val whatIsThisThen = (1 to 3).map(doNothingFunctionAsValue)
println(whatIsThisThen.mkString(","))

// End of Exercise **********************

case class MyNewClass(i:Int)

def matchCustomClass(c:MyNewClass):Boolean = {
  c match {
    case MyNewClass(10) => true
  }
}

def matchRegEx(pattern:Regex, toMatch:String):Boolean = {
  toMatch match {
    case pattern() => true
    case _ => false
  }
}
val re1 = ".*".r
val phoneNumber = "[(][\\d]{3}[)][\\d]{3}[-][\\d]{4}".r
//val URLPattern = "(http://www\\.|https://www\\.|http://|https://)?[a-z0-9]+([-.]{1}[a-z0-9]+)*\\.\\s?[a-z]{2,5}(:[0-9]{1,5})?(/.*)?".r

//matchRegEx(URLPattern, "http://www.abc.com.cn:9000/index")
matchRegEx(phoneNumber, "(408)345-7890")

def mySplit(s:String):Array[String] = {
  s.split(",")
}

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
def readCSVF(csvFile:String):Array[Array[String]] = {
  val file = Source.fromFile(csvFile)
  val lines:Array[String] = file.getLines().toArray
  lines.map(s => mySplit(s))
}

val lines = readCSV("/Users/spoon/projects/scalacourse/common_util/testdata/cars.csv")
//lines.foreach(l => println(l.mkString(",")))
val first = lines.head
println(first.mkString(","))

