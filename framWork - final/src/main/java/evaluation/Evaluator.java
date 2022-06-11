package evaluation;

import data.Event;
import data.Notification;
import evaluation.metrics.Metric;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class Evaluator {

     public static List<String> ResmetricsName = new ArrayList<>();
    public static List<String> ResmetricsResult = new ArrayList<>();
    public static LongOpenHashSet getTestWindowForUser(Event currentTransaction, List<Notification> trainingWindow,
                                                       int currentTransactionIndex, long evaluationWindowSize) {

        // test window of user
        //the ground truth of one user

        LongOpenHashSet listItems = new LongOpenHashSet();

        Event transaction;
        //long requestTimeStamp = currentTransaction.getTime().getTime();
        long testWindowTime = currentTransaction.getTime().getTime()+evaluationWindowSize;
        //System.out.print("testWindowTime : "+testWindowTime +"  --- ");
        //check items in test window (test window starts from last training index)
        for (int i = currentTransactionIndex; i < trainingWindow.size(); i++) {


            if (trainingWindow.get(i) instanceof Event) {

                transaction = (Event) trainingWindow.get(i);

                if (transaction.getTime().getTime() >= testWindowTime) {
                    break;
                }

                if (transaction.getId_user() != currentTransaction.getId_user()) {
                    continue;
                }

                listItems.add(transaction.getId_item());

            }else {
                continue;
            }
        }

        return listItems;

    }

    public static void printResult(List<Metric> metrics) {
        // print evaluation results extracted from metric classes

        DecimalFormat df = new DecimalFormat("0.0000000");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        System.out.println("//=================overall results=========================//");
        System.out.println();

        for (Metric m : metrics) {

            System.out.println(StringUtils.rightPad(m.getName(), 20, " ") + "\t" + df.format(m.getResults()));
            ResmetricsName.add(m.getName().toString());
            ResmetricsResult.add(String.valueOf(m.getResults()));

        }




    }



    }
