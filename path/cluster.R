con <- file("H:/kc2.0/myeclipse/cnsoftbei/path/nameOfFileToBeMined.txt", "r")
line=readLines(con,n=1)
data <- read.csv( line, header = TRUE )
kc <- kmeans(data,3)
