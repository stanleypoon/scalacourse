package common.io

import java.io.File
import java.nio.file.{Files, Path, Paths}

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, FunSuite, Suite, Tag}

import scala.reflect.io.Path

trait TestSuite extends BeforeAndAfterAll {self: Suite =>

  protected override def beforeAll() {
    super.beforeAll()
  }

  protected override def afterAll() {
    super.afterAll()
  }
}

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
