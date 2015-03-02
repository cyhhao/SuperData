library(arules)  #����arules�����  
library(stringr)
minedFile <- file("H:/kc2.0/myeclipse/cnsoftbei/path/nameOfFileToBeMined.txt", "r")
fullFileName=readLines(minedFile,n=1)
dataFromFile<- read.transactions(file=fullFileName, format = "basket",sep=",")
#data(Groceries)  #���������ļ�  
#dataFromFile <- read.csv( fullFileName )
rules=apriori(dataFromFile,parameter=list( minlen=2,support=0.01,confidence=0.01))    #���������  
x<-sort(rules,by="support")[1:10]
inspect(x)
write.csv( dataFromFile@itemInfo,"H:/kc2.0/myeclipse/cnsoftbei/path/console.csv", row.names=FALSE, fileEncoding = "UTF-8")