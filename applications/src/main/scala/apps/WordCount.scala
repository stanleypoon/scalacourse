package apps

import scala.language.postfixOps  // silent compiler warning
import common.io.FileUtil._
import common.text.TextUtil._

object WordCount extends App {

  type SINGLE_FILTER_FUNCTION[T] = (T) => Boolean
  type KV_FILTER_FUNCTION[K,V] = ((K,V)) => Boolean

  def getSingleFilterFunctions[T](values:Iterable[T]):List[SINGLE_FILTER_FUNCTION[T]] = {
    values.map(v => (o: T) => v.equals(o)).toList
  }

  def getKVFilterFunctions[K, V](values:Iterable[K]):List[KV_FILTER_FUNCTION[K,V]] = {
    values.map(e => (p:(K, V)) => p._1.equals(e)).toList
  }

  def analyseSite(url:String, keys:List[String]):Unit = {
    // decode HTML entities like: &amp; &lt; &gt; etc
    val text = decode(readURL(url))
    // remove common words that carry little information
    // also lower case words: can be issue with proper nouns like Trump / trump
    val words = removeStopWords(split(text).map(_.toLowerCase))
    // generate word count
    val wc = count(words)
    // output top 10 popular words
    print(wc, 0, 10)
    // get counts for each of the keywords
    val keyWordCounts = getKVFilterFunctions[String, Int](keys)
    .map(f => wc.filter(f))
      .groupBy(identity)
      .mapValues(_.size)

    println(keyWordCounts.mkString(","))
  }

  def yahooFinance(keyWords:List[String]) = {
    analyseSite("https://finance.yahoo.com", keyWords)
  }

  def yahooMain(keyWords:List[String]) = {
    analyseSite("https://yahoo.com", keyWords)
  }

  val keyWords = List("trump", "pg&e", "fisher", "oil")
  yahooFinance(keyWords)
  yahooMain(keyWords)
}