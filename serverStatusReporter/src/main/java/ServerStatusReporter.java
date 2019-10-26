import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Status;
import server.ServerPollCallable;

import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ServerStatusReporter {

    public static final Logger logger = LoggerFactory.getLogger(ServerStatusReporter.class);

    public static void main(String[] args) {

        List<Status> statusList = getStatuses();

        //basic validation
        if(statusList.size()<1000){
            logger.error("Problem with getting status for all servers. Calculating the average for " + statusList.size() + "requests");
        }

        //get sum of total requests per (application, version)
        Map<String, LongSummaryStatistics> requestCountMap = statusList.stream().collect(Collectors.groupingBy(f -> f.getApplication() + "," + f.getVersion(),
                Collectors.summarizingLong(Status::getRequests_count)));

        //get sum of success requests per (application, version)
        Map<String, LongSummaryStatistics> successCountMap = statusList.stream().collect(Collectors.groupingBy(f -> f.getApplication() + "," + f.getVersion(),
                Collectors.summarizingLong(Status::getSuccess_count)));

        //get success rate
        requestCountMap.entrySet().iterator().forEachRemaining( entry -> {
            String successRate = String.format("%.2f", 100*(double)(successCountMap.get(entry.getKey()).getSum())/(double)(entry.getValue().getSum()));
            System.out.println(entry.getKey() + "," + successRate);
        });

    }

    /**
     * Polls all the servers and gets all the statuses
     * @return list of statuses for all servers
     */
    public static List<Status> getStatuses(){

        final String SERVER_URL = "http://storage.googleapis.com/revsreinterview/hosts/host";
        List<Status> statusList = new ArrayList();

        ExecutorService executor = Executors.newFixedThreadPool(100);
        List<Future<String>> futureList = new ArrayList();
        for(int i=0; i<1000; i++){
            ServerPollCallable serverPollCallable = new ServerPollCallable(SERVER_URL + i + "/status");
            futureList.add(executor.submit(serverPollCallable));
        }
        executor.shutdown();

        for(Future<String> future: futureList){
            ObjectMapper mapper =  new ObjectMapper();
            String status = "";
            try {
                status = future.get();
                statusList.add(mapper.readValue(status, Status.class));
            } catch (JsonProcessingException e) {
                logger.error("Json exception reading {}", status);
            } catch (Exception e) {
                logger.error("Exception reading status from future");
            }
        }
        return statusList;
    }


}
