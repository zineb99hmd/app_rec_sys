package sender;

import algorithm.*;
import data.Event;
import data.Item;
import data.Notification;
import data.Request;
import data.loading.ReadFileJsn;
import data.loading.ReadPlista;
import data.splliting.DataSplittingStrategy;
import evaluation.EstimateReadingTime;
import evaluation.Evaluator;
import evaluation.metrics.Metric;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sender {
    public static List<String> RecList= new ArrayList<>();

    public static void offlineStrategy(String fileItem, String fileEvent, Algorithm algo, int recsLimit, float trainingRatio, List<Metric> metricsList) {

        List<Notification> trainingWindow = ReadPlista.loadPlistaData(fileItem, fileEvent);

        List<Item> items = DataSplittingStrategy.offlineHoldoutGetItemSet(trainingWindow,trainingRatio);

        List<Event> trainingSet = DataSplittingStrategy.offlineHoldoutGetTrainingSet(trainingWindow,trainingRatio);

        List<Event> testSet = DataSplittingStrategy.offlineHoldoutGetTrainingSet(trainingWindow,trainingRatio);

        for (Item i:items){
            algo.handleItemUpdate(i);
        }

        for (Event e:trainingSet){
            algo.handleEventNotification(e);
        }

        /// evaluation
        Map<Long,LongOpenHashSet> transactionsForAllUsersToEvaluate= new HashMap<Long,LongOpenHashSet>();
        LongOpenHashSet userTransactions;

        Request request;

        // users to evaluate
        for (Event e:testSet){

            userTransactions = transactionsForAllUsersToEvaluate.get(e.getId_user());

            if (userTransactions == null){
                userTransactions   =  new LongOpenHashSet();
            }
            userTransactions.add(e.getId_item());
            transactionsForAllUsersToEvaluate.put(e.getId_user(),userTransactions);
        }
        List<Long> listUsers = new ArrayList<>();
        listUsers.addAll(transactionsForAllUsersToEvaluate.keySet());
        request = new Request();
        for ( Long idUser:listUsers){
            request.setId_user(idUser);
            System.out.println("recsLimit"+recsLimit);
            request.setLimit(recsLimit);
            algo.getRecommendations(request);
            System.out.println("recommandations : "+algo.getRecommendations(request));

            userTransactions = transactionsForAllUsersToEvaluate.get(idUser);
            for (Metric metric:metricsList){

                metric.evaluate((long) 0.0,algo.getRecommendations(request),userTransactions);
            }



        }
        for (int i=0;i<algo.getRecommendations(request).size();i++){
            RecList.add(String.valueOf((algo.getRecommendations(request)).get(i)));

        }

        Evaluator.printResult(metricsList);

        //userTransactions
    }

    public static void onlineStrategy(String fileItem, String fileEvent, Algorithm algo,int recsLimit, long evaluationWindowSize,List<Metric> metricsList) {
        {
            List<Notification> trainingWindow = ReadPlista.loadPlistaData(fileItem, fileEvent);

            Notification itemOrEvent;
            Event currentTransaction;
            Item item;
            Request request=null ;
            LongOpenHashSet userTransactions;

            DataSplittingStrategy.onLineTemporalSplit(trainingWindow);

            for (int i = 0; i < trainingWindow.size(); i++) {

                progressPercentage(i + 1, trainingWindow.size());

                itemOrEvent = trainingWindow.get(i);
                if (itemOrEvent instanceof Item) {
                    //itemUpdate
                    item = (Item) itemOrEvent;
                    algo.handleItemUpdate(item);
                    //System.out.print(" itemID : "+item.getId());
                    //System.out.println(" : "+item.getTime());
                } else {

                    //event notification or request
                    currentTransaction = (Event) itemOrEvent;

                    algo.handleEventNotification(currentTransaction);

                    //System.out.print(" userID : "+currentTransaction.getId_user());
                    //System.out.println(" : "+currentTransaction.getTime());

                    userTransactions = Evaluator.getTestWindowForUser(currentTransaction, trainingWindow, i, evaluationWindowSize);


                    // check if there is a recommendation request
                    // send request only if there is valid items in the next evaluation window
                    //if there is no ground truth, there is nothing to evaluate(no requests)
                    if (!userTransactions.isEmpty()) {

                        request = new Request(currentTransaction);
                        request.setLimit(recsLimit);
                        //algo.getRecommendations(request);


                        //evaluation of the current request
                        for (Metric metric:metricsList){
                            metric.evaluate(request.getTime().getTime(),algo.getRecommendations(request),userTransactions);

                        }


                    }
                }

            }
            for (int i=0;i<algo.getRecommendations(request).size();i++){
                RecList.add(String.valueOf((algo.getRecommendations(request)).get(i)));

            }
//
//            System.out.println(algo.getRecommendations(request));
           Evaluator.printResult(metricsList);
        }



    }
    public static void onlineStrategyWithIa(String fileItem, String fileEvent, Algorithm algo,int recsLimit,List<Metric> metricsList) {
        {
            String fileExtractfullText="C:\\Users\\zineb\\OneDrive\\Bureau\\data_set1\\items.json";
            List<Notification> trainingWindow = ReadPlista.loadPlistaData(fileItem, fileEvent);
            HashMap<Long, String> fullText= ReadFileJsn.readJson(fileExtractfullText);
            long evaluationWindowSize = 0;
            Notification itemOrEvent;
            Event currentTransaction;
            Item item;
            Request request = null;
            LongOpenHashSet userTransactions;
            DataSplittingStrategy.onLineTemporalSplit(trainingWindow);

            for (int i = 0; i < trainingWindow.size(); i++) {
                progressPercentage(i + 1, trainingWindow.size());
                itemOrEvent = trainingWindow.get(i);
                if (itemOrEvent instanceof Item) {
                    //itemUpdate
                    item = (Item) itemOrEvent;
                    algo.handleItemUpdate(item);
                    //System.out.print(" itemID : "+item.getId());
                    //System.out.println(" : "+item.getTime());
                } else {
                    // event notification or request
                    currentTransaction = (Event) itemOrEvent;
                    //getTextEvent(currentTransaction,trainingWindow);
                    algo.handleEventNotification(currentTransaction);
                    //System.out.print(" Id_item : "+currentTransaction.getId_item());
                    evaluationWindowSize= EstimateReadingTime.estimateTestWindowSize(fullText.get(currentTransaction.getId_item()));
                    userTransactions = Evaluator.getTestWindowForUser(currentTransaction, trainingWindow, i, evaluationWindowSize);
                    // check if there is a recommendation request
                    // send request only if there is valid items in the next evaluation window
                    //if there is no ground truth, there is nothing to evaluate(no requests)
                    if (!userTransactions.isEmpty()) {
                        request = new Request(currentTransaction);
                        request.setLimit(recsLimit);
                        //algo.getRecommendations(request);
                        //System.out.println(algo.getRecommendations(request));
                        //evaluation of the current request
                        for (Metric metric:metricsList){
                            metric.evaluate(request.getTime().getTime(),algo.getRecommendations(request),userTransactions);
                        }

                    }
                }

            }
            for (int i=0;i<algo.getRecommendations(request).size();i++){
                RecList.add(String.valueOf((algo.getRecommendations(request)).get(i)));

            }
//
//            System.out.println(algo.getRecommendations(request));
            Evaluator.printResult(metricsList);

        }


    }


    public static void progressPercentage(int remain, int total) {
        if (remain > total) {
            throw new IllegalArgumentException();
        }
        int maxBareSize = 10; // 10unit for 100%
        int remainProcent = ((100 * remain) / total) / maxBareSize;
        char defaultChar = '-';
        String icon = "*";
        String bare = new String(new char[maxBareSize]).replace('\0', defaultChar) + "]";
        StringBuilder bareDone = new StringBuilder();
        bareDone.append("[");
        for (int i = 0; i < remainProcent; i++) {
            bareDone.append(icon);
        }
        String bareRemain = bare.substring(remainProcent, bare.length());
        System.out.print("\r" + bareDone + bareRemain + " " + remainProcent * 10 + "%");
        if (remain == total) {
            System.out.print("\n");
        }
    }


}
