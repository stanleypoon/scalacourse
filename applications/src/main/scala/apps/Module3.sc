import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.Random

1.to(10)

// 1 is object of Int
// we can access functions/members of it
// infix notation

val one:Int = 1

one.to(10)

1 to 10

/**
  * Attempt to simulate Central limit theorem
  */

// 1,2,3,4,5,6,7,8,9,10  (Population)
// 1 2 3, 4 5 6, 8 9 10  (Samples)

val populationFixed = Array[Double](1,2,3,4,5,6,7,8,9,10)
val population = (1 to 1000).map(_.toDouble).toArray

// Step 1 Function to get the mean from
// an array of doubles
//
// There are many ways to write this function:

// helper function to make the code easier to understand
// used in meanFold and meanReduceClear
def add(a:Double, b:Double):Double = {
  a + b
}

def meanBuiltIn(n:Iterable[Double]):Double = {
  n.sum/n.size
}

def meanFold(n:Iterable[Double]):Double = {
  n.foldLeft(0D)(add)/n.size
}

def meanReduceClear(n:Iterable[Double]):Double = {
  n.reduce(add)/n.size
}

def meanReduce(n:Iterable[Double]):Double = {
  n.reduce(_+_)/n.size
}

// test them out
meanBuiltIn(populationFixed)
meanFold(populationFixed)
meanReduce(populationFixed)
meanReduceClear(populationFixed)

/**
  * This version assumes the population with distinct values
  *
  * @param sampleSize
  * @param population
  * @return a sample of size sampleSize as an Array[Double]
  */
def getSampleWithoutReplacement(sampleSize:Int, population:Array[Double]):Array[Double] = {
  val sample = ListBuffer[Double]()
  val populationSize = population.length
  while (sample.size < sampleSize) {
    val value = population(Random.nextInt(populationSize))
    if (!sample.contains(value)) sample.append(value)
  }
  sample.toArray
}

// Test out sampling
getSampleWithoutReplacement(3, population)
getSampleWithoutReplacement(100, populationFixed)

def CLTS(population:Array[Double], samples:Int, sampleSize:Int):Double = {
  // get samples of a certain size
  // calculate the mean each sample
  // calculate mean of the sample means

  meanReduce((1 to samples).map(_ => meanReduce(getSampleWithoutReplacement(sampleSize, population))))
}

meanReduce(population)
CLTS(population, 1000, 100)

