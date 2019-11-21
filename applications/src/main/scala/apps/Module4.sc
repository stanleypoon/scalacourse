import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.Random

/*
    Defining classes
    Case class
    Pattern Matching
 */

class myNewClass(i:Int)

case class FullName(first:String, given:String, last:String)

case class Employee(id:String, lastName:String, firstName:String, role:String,
               level:Int, dept:String, directReports:List[Employee],
               reportTo:List[Employee])
{
  def getFullName:FullName = {
    FullName(firstName, "", lastName)
  }

  def salaryOld(level:Int):Double = {
    if (level < 2) 1000D
    else if (level < 3) 2000D
    else if (level < 4) 3000D
    else -1
  }

  def salary(level:Int):Double = {
    level match {
      case 1 => 1000D
      case 2 => 2000D
      case 3 => 3000D
      case _ => -1D
    }
  }

  def matchEmployee(employee:AnyRef):Boolean = {
    val r1 = directReports.head
    employee match {
      case Employee(this.id, this.lastName, _, _, _, _,_,_) => true
      case e:Employee => true
      case _ => false
    }
  }
}

//val e4 = Employee("e10004", "a", "b1", "c", 1, "d", List.empty, List.empty)
//val e3 = Employee("e10003", "a", "b1", "c", 1, "d", List.empty, List.empty)
//val e1 = Employee("e10001", "a", "b", "c", 1, "d", List(e3, e4), List.empty)
//val e2 = Employee("e10002", "a", "b1", "c", 1, "d", List(e3), List.empty)
//
//e1
//e2
//e1.matchEmployee(e2)

//
//
//
//
//def function(a:String): () = {
//  def ht():Unit = {
//  }
//
//  ht
//}
//
//
//val fType:_ => () = function


/**
  * This version does not assume the population
  * have distinct values
  *
  * @param sampleSize
  * @param population
  * @return a sample of size sampleSize as an Array[Double]
  */
def getSampleWithoutReplacementG(sampleSize:Int, population:Array[Double]):Array[Double]= {
  val len = population.length
  if (sampleSize > 0 && sampleSize < len) {
    val indices = ListBuffer[Int]()
    val sample = ListBuffer[Double]()
    do {
      val ri = Random.nextInt(len)
      if (!indices.contains(ri)) {
        indices.append(ri)
        sample.append(population(ri))
      }
    } while (indices.length < sampleSize)
    sample.toArray
  } else {
    println("Sample size out of range")
    Array.empty
  }
}

class SampleIndex(val populationSize:Int) {
  val indices: mutable.Set[Int] = mutable.Set.empty

  def withReplacement:Int = Random.nextInt(populationSize)

  def withOutReplacement:Int = {
    var ri = -1
    do {
      ri = Random.nextInt(populationSize)
    } while (!indices.add(ri))
    ri
  }
}

def getSample(sampleSize:Int, population:Array[Double],
              replacement:Boolean = false):Array[Double] = {
  val indexFactory = new SampleIndex(population.length)
  val sampleMethod = if (replacement) indexFactory.withReplacement _
                        else indexFactory.withOutReplacement _
  (1 to sampleSize).map(_ => population(sampleMethod())).toArray
}

val populationFixed = Array[Double](1,2,3,4,5,6,7,8,9,10)
val population = (1 to 1000).map(_.toDouble).toArray

getSample(3, populationFixed)
getSample(9, populationFixed, true)