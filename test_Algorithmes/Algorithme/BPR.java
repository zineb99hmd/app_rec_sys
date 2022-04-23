package Algorithme;

import Data_Split.Data_Memory;
import Util.Util;
import data.Notification_Event;
import data.Notification_Item;
import data.Notification_Requete;
import it.unimi.dsi.fastutil.longs.LongArrayList;

import java.util.*;

public class BPR extends Algorithm {
    private Set<Long> items = new HashSet<>();
    private Set<Long> users = new HashSet<>();
    private List<Transaction> transactions = new ArrayList<>();

    // / Sample uniformly from users, for the strategy of the original paper set
    // false
    public boolean UniformUserSampling = true;

    protected final static Random random = new Random();

    // Regularization parameter for the bias term
    public double biasReg = 0;

    // number of columns in the latent matrices
    public int numFeatures = 100;

    // number of iterations over the data
    public int initialSteps = 100;

    // number of users
    private int numUsers;

    // number of items
    private int numItems;

    // datamanagement-object
    public Data_Memory data;

    // Learning rate alpha
    public double learnRate = 0.05;

    // Regularization parameter for user factors
    public double regU = 0.0025;

    // Regularization parameter for positive item factors
    public double regI = 0.0025;

    // Regularization parameter for negative item factors</summary>
    public double regJ = 0.00025;

    // If set (default), update factors for negative sampled items during
    // learning
    public boolean updateJ = true;

    // The two factors to modify the sampling of i and j.
    // default = uniform sampling = 0
    // if both this and popI/J are set, this will take over
    public double gaussDenominatorI = 0;
    public double gaussDenominatorJ = 0;

    // The two switches to enable pop sampling for i and j.
    // default = uniform sampling = false;
    // if both this and gaussDenominatorI/J are set, the gaussian sampling will
    // take over
    public boolean popI = false;
    public boolean popJ = false;
    public float predictRatingBPR(long cookie, Long item) {
        // Note: Predictions are only helpful for ranking and not for prediction
        // convert IDs in mapped values
        int itemidx = data.;
        Integer useridx = data.userIndices.get(cookie);

        if (useridx != null) {
            return (float) (data.item_bias[itemidx] + data.rowScalarProduct(useridx, itemidx));
        } else {
            // This might happen during training test splits for super-sparse
            // (test) data
            // System.out.println("-- No entry for user: " + user);
            return Float.NaN;
        }
    }
    private void init() {

        initDataManagement();

        // ascertain number of users and items
        numItems = items.size();
        numUsers = users.size();

        // System.out.println("Users, items: " + numUsers + " " + numItems + "
        // ratings " + dataModel.getRatings().size());

        // if one of the gaussian sampling parameters is set, tell the
        // DataManagement class to prepare the necessary additional data
        // structures
        if (gaussDenominatorI != 0 || gaussDenominatorJ != 0 || popI || popJ) {
            data.useAdvancedSampling = true;
        }

        // Initialization of datamanagement-object
        data.init(transactions, users, items, numUsers, numItems, numFeatures);

        // System.out.println("Init done BPR");
        // trainig of the data
        train();
    }

    public int[] sampleItempair(int[] triple) {
        int u = triple[0];

        List<Integer> user_items = data.userMatrix.get(u);

        // use the gaussian distribution to aquire the i item of the (u,i,j)
        // triple from the less popular ones (aka the unpopular good alternative
        // to the popular item j)
        if (gaussDenominatorI > 0) {
            triple[1] = gaussRandItem(data.userPopularityMatrixAscending.get(u), gaussDenominatorI);
        } else if (popI) {
            triple[1] = aggregationRandItem(data.aggregatedUserPopularityMatrixAscending.get(u),
                    data.aggregatedUserPopularitySum.get(u));
        }
        // else default uniformly random drawing
        else {
            triple[1] = user_items.get((random.nextInt(user_items.size())));
        }
        do {
            // use the gaussian distribution to aquire the j item of the (u,i,j)
            // triple from the more popular ones (aka the popular, but unliked
            // item)
            if (gaussDenominatorJ > 0) {
                triple[2] = gaussRandItem(data.popularityListDescending, gaussDenominatorJ);
            } else if (popJ) {
                triple[2] = aggregationRandItem(data.aggregatedPopularityMapDescending, data.numPosentries);
            }
            // else default uniformly random drawing
            else {
                triple[2] = random.nextInt(numItems);
            }
        } while (user_items.contains(triple[2]));

        return triple;
    }

    protected int gaussRandItem(List<Integer> list, double denominator) {
        // try 10 times to draw gaussian from the list
        for (int i = 0; i < 10; i++) {
            double gRand = random.nextGaussian();
            double gRandExpanded = gRand * list.size() / denominator;
            int listIndex = Math.abs((int) Math.round(gRandExpanded));

            if (listIndex < list.size()) {
                return list.get(listIndex);
            }
        }
        // if that fails because the index was always out of list bounds, then
        // just draw random
        return list.get(random.nextInt(list.size()));
    }

    protected int aggregationRandItem(Map<Integer, Integer> map, int aggregatedSize) {
        int pRandom = random.nextInt(aggregatedSize) + 1;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= pRandom) {
                return entry.getKey();
            }
        }
        System.err.println("popularityRandItem() did not yield a correct value!");
        return -1;
    }

    @Override
    public void handleEventNotification(List<Notification_Event> id_event) {
        for (ClickData t : transactions ){
            this.items.add(t.click.item.id);
            this.users.add(t.click.userId);
            this.transactions.add(t.click);
        }

        init();

    }

    @Override
    public void handleItemUpdate(List<Notification_Item> item) {

    }

    @Override
    public LongArrayList getRecommendations(List<Notification_Requete> id_event) {
        Map<Long, Float> predictions = new HashMap<>();
        float pred = Float.NaN;

        // Go through all the items
        for (Long item : items) {

            // make a prediction and remember it in case the recommender
            // could make one
            pred = predictRatingBPR(clickData.click.userId, item);
            if (!Float.isNaN(pred)) {
                predictions.put(item, pred);
            }
        }

        return new LongArrayList(Util.sortByValue(predictions, false).keySet());
    }

    public void setUniformUserSampling(boolean uniformUserSampling) {
        UniformUserSampling = uniformUserSampling;
    }

    public void setBiasReg(double biasReg) {
        this.biasReg = biasReg;
    }

    public void setNumFeatures(int numFeatures) {
        this.numFeatures = numFeatures;
    }

    public void setInitialSteps(int initialSteps) {
        this.initialSteps = initialSteps;
    }


    public void setLearnRate(double learnRate) {
        this.learnRate = learnRate;
    }


    public void setRegU(double regU) {
        this.regU = regU;
    }


    public void setRegI(double regI) {
        this.regI = regI;
    }


    public void setRegJ(double regJ) {
        this.regJ = regJ;
    }


    public void setUpdateJ(boolean updateJ) {
        this.updateJ = updateJ;
    }
    public void setGaussDenominatorI(double gaussDenominatorI) {
        this.gaussDenominatorI = gaussDenominatorI;
    }

    public void setGaussDenominatorJ(double gaussDenominatorJ) {
        this.gaussDenominatorJ = gaussDenominatorJ;
    }


    public void setPopI(boolean popI) {
        this.popI = popI;
    }


    public void setPopJ(boolean popJ) {
        this.popJ = popJ;
    }
}
