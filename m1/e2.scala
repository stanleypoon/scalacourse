val yf_text = readURL("https://finance.yahoo.com")
val yf_words = removeStopWords(split(yf_text).map(_.toLowerCase))
val yf_wc = count(yf_words)
print(yf_wc, 0, 10)

val yahoo_text = readURL("https://yahoo.com")
val yahoo_words = removeStopWords(split(yahoo_text).map(_.toLowerCase))
val yahoo_wc = count(yahoo_words)

print(yahoo_wc, 0, 10)
val trump_c = yahoo_words.filter(trump)
val pge_c = yahoo_words.filter(pge)

saveURL("https://yahoo.com", "yahoo.html")
saveURL("https://finance.yahoo.com", "yahoo_finance.html")
