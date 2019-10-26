## Dependencies
1. Java 11
2. Maven (to build the jar)

## Build 
mvn clean package

## Run 
java -jar serverStatusReporter-1.0.jar

## Assumptions
Total number of requests per application per version do not exceed a max long value ((2^63)-1). Assuming the number of requests on the status page is reported for the last X amount of time and this tool is run on a periodic schedule and success rates are stored in a persistent storage.
Reporting the success rate with a precision of 2.
