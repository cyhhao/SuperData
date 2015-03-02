library(Rwordseg)
library(stringr)
library(rJava)
minedFile <- file("H:/kc2.0/myeclipse/cnsoftbei/path/nameOfFileToBeMined.txt", "r")
fullFileName=readLines(minedFile,n=1)
close(minedFile)
con <- file( fullFileName, "r")
line=readLines(con,n=1)
d <- c()
while( length(line) != 0 ) {
	write.table(line,file = "H:/kc2.0/myeclipse/cnsoftbei/path/rst.txt", append = TRUE,row.names=F,quote = F, fileEncoding = "UTF-8")
     line=readLines(con,n=1)
}
close(con)