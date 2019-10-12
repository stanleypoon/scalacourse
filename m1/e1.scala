import scala.language.postfixOps
val text = readFile("common_util/testdata/word-count.txt")
val words = removeStopWords(split(text).map(_.toLowerCase))
val wc = count(words)
println()
println("What is this document?")
println()
print(wc, 0, 20)
