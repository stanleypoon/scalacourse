package common.io

import java.nio.file.{Files, Paths}
import java.text.MessageFormat

import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import org.scalatest.{FunSuite, Tag}
import java.io._

import common.TestSuite

@RunWith(classOf[JUnitRunner])
class TestFileUtil extends FunSuite with TestSuite {
  val testInputFile = "common_util/testdata/input-hello-world.txt"
  val testTempOutputfile = "common_util/testdata/temp-output.txt"
  val writeTestContents = "Write Test Contents"
  val helloWorld = "Hello World"

  test("read existing input file", Tag("Scala IO")) {
    val helloWorld = FileUtil.readFile(testInputFile)
    assert(helloWorld.equals(helloWorld))
  }

  test ("test write, read and delete", Tag("Scala IO")) {
    FileUtil.write(testTempOutputfile, writeTestContents)
    val contents = FileUtil.readFile(testTempOutputfile)
    assert(contents.equals(writeTestContents))
    FileUtil.deleteFile(testTempOutputfile)
    val fexist = Files.exists(Paths.get(testTempOutputfile))
    assert(!fexist)
  }

  test ("test reading from StdIn", Tag("Scala IO")) {
    val prompt = "Input your line >"

    // these has no effect from sbt command line
    redirectStdIn(helloWorld)

    val input = FileUtil.readLine(prompt)
    assert(input.equals(helloWorld))

    System.setIn(System.in)
    System.setOut(System.out)
  }

  // this test will fail inside IDE with error:
  //   java.io.EOFException: Console has reached end of input
  //
  // this test do pass in sbt test during build time
  test ("test reading formated text", Tag("Scala IO")) {
//  val formatText = "Item{0, number, #} cost{1, number, '$'#,##0.##} a {2}."
    val formatText = "{0} {1, number, currency} {2}"
    val itemNumber = 1111
    val cost = 250
    val unit = "pound"

    val ff = new MessageFormat(formatText)
    // need to type cast here to convert to Java Object[] for format()
    val formttedText = ff.format(Array(itemNumber, cost, unit).map(_.asInstanceOf[AnyRef]))

    // these has no effect from sbt command line
    redirectStdIn(formttedText)

    val inputFields = FileUtil.readFormated(formatText)
    assert(inputFields(0).toString.toInt == itemNumber)
    assert(inputFields(1).toString.toDouble == cost)
    assert(inputFields(2).toString.equals(unit))
  }

  test ("write lines out to file") {
    val sourceLines = Seq("line 1", "line 2", "line 3")
    val tempFileName = Files.createTempFile("utest_", ".txt").getFileName.toAbsolutePath.toString
    try {
      // write and read it back
      FileUtil.writeLines(tempFileName, sourceLines)
      val linesFromRead = FileUtil.readLines(tempFileName)

      // assert number of lines
      assert(sourceLines.size == linesFromRead.size)

      // check contents
      val wronglines = sourceLines.zip(linesFromRead).filter(l => !l._2.equals(l._1))
      assert(wronglines.size == 0)
    } finally {
      FileUtil.deleteFile(tempFileName)
    }
  }

  test ("read from a URL") {
    val siteContents = FileUtil.readURL("http://finance.yahoo.com")
    val testFileName = "yahoo.finance.com.text"
    assert(siteContents.size > 0)
    try {
      FileUtil.saveURL("http://finance.yahoo.com", testFileName)
    } finally {
      FileUtil.deleteFile(testFileName)
    }
  }

  // **** the following redirect works within IDE
  // **** but it does not work from sbt command line

  // redirect STDIN to an input stream with the content in @input
  def redirectStdIn(input:String):Unit = {
    val stdin = new ByteArrayInputStream(input.getBytes)
    System.setIn(stdin)
  }

  // can examine contents of the output in the returned output stream
  //    e.g. assert(ostream.toString.equals(ExpectedText))
  def redirectStdOut(): (OutputStream, PrintStream) = {
    val ostream = new ByteArrayOutputStream()
    val stdout = new PrintStream(ostream)
    System.setOut(stdout)
    (ostream, stdout)
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
    FileUtil.deleteFile(testTempOutputfile)
  }
}
