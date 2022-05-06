//import algorithm.*;
//import evaluation.Evaluator;
//import evaluation.metrics.*;
//import org.apache.commons.math3.util.Precision;
//import sender.Sender;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RecsFramEval {
//
//
//
//    public static void main (String [] args){
//
//        String file_item = "C:\\Users\\zineb\\OneDrive\\Bureau\\data_set1\\Items_plista1677_1m_fixed.csv";
//        String file_event = "C:\\Users\\zineb\\OneDrive\\Bureau\\data_set\\Events_plista1677_1m_dedup.csv";
//
//        int recsSizeLimit = 5;
//
//        // to check recommendation requests
//        long evaluationWindowSize = 5*60*1000L;
//
//        //Algorithm algo = new RecentlyClicked();
//        //Algorithm algo = new Random();
//        //Algorithm algo = new MostPopular();
//       Algorithm algo = new RecentlyPopular();
//        //Algorithm algo = new ItemItemCF();
//        //Algorithm algo = new FastSessionCoOccurrence();
//
//        //Metric clickThroughRate = new ClickThroughRate();
//        //clickThroughRate.setK(recsSizeLimit);
//        Metric f1 = new F1();
//        f1.setK(recsSizeLimit);
//        Metric map = new MAP();
//        map.setK(recsSizeLimit);
//        Metric ndcg = new NDCG();
//        ndcg.setK(recsSizeLimit);
//
//
//        List<Metric> metricsList = new ArrayList<>();
//        //metricsList.add(clickThroughRate);
//        metricsList.add(f1);
//        metricsList.add(map);
//        metricsList.add(ndcg);
//
//
//        float ratio =  0.8f;
//        //Sender.offlineStrategy(file_item,file_event,algo,recsSizeLimit,ratio,metricsList);
//
//        Sender.onlineStrategy(file_item,file_event,algo,recsSizeLimit,evaluationWindowSize,metricsList);
//
//        //System.out.println(f1.getResults());
//
//        System.out.println(algo.getClass());
//        Evaluator.printResult(metricsList);
//
//    }
//
//
//}
