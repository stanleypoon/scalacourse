package common.text

import common.TestSuite
import common.io.FileUtil
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestTextUtil extends FunSuite with TestSuite {

  test("get word count from a local file") {
    val text = TextUtil.readFile("common_util/testdata/word-count.txt")
    val words = TextUtil.removeStopWords(TextUtil.split(text).map(_.toLowerCase))
    val wc = TextUtil.count(words)

    val freedom_c = wc.filter(l => l._1.equals("freedom")).head._2
    val dream_c = wc.filter(l => l._1.equals("dream")).head._2
    val nation_c = wc.filter(l => l._1.equals("nation")).head._2

    assert(freedom_c == 20)
    assert(dream_c == 11)
    assert(nation_c == 10)
  }

  test("get word count from a live web page") {
    val yf_text = TextUtil.decode(FileUtil.readURL("https://finance.yahoo.com"))
    val yf_words = TextUtil.removeStopWords(TextUtil.split(yf_text).map(_.toLowerCase))
    val yf_wc = TextUtil.count(yf_words)

    TextUtil.print(yf_wc, 0, 5)

    val html_c = yf_wc.filter(l => l._1.equals("html")).head._2
    assert(html_c > 0)

    val trumps = yf_words.filter(TextUtil.trump)
    println(trumps)
  }

  protected override def beforeAll() {
    super.beforeAll()
    cleanUp  // in case something left over from last test
  }

  protected override def afterAll() {
    try {
      cleanUp
    } finally {
      super.afterAll()
    }
  }

  private def cleanUp:Unit = {
  }

}
