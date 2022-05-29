package com.example.sr;
import algorithm.*;
import evaluation.Evaluator;
import evaluation.metrics.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sender.Sender;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class page3EnLigne implements Initializable {
    @FXML
    Pane rootpane;
    @FXML
    Pane lyomtakhrej;

    @FXML
    private Spinner<Integer> limites;
    @FXML
    private Spinner<Integer> temps;
    @FXML
    Integer limiteValue;

    @FXML
    Long timeValue;
    @FXML
    private RadioMenuItem Random;
    @FXML
    private RadioMenuItem MostPopular;
    @FXML
    private RadioMenuItem RecentlyClicked;
    @FXML
    private RadioMenuItem RecentlyPopular;
    @FXML
    private RadioMenuItem CoOccurence;

    @FXML
    private RadioMenuItem ClickThroughRate ;
    @FXML
    private RadioMenuItem F1;

    @FXML
    private RadioMenuItem MAP;
    @FXML
    private RadioMenuItem MeanF1;
    @FXML
    RadioButton explicite;
    @FXML
    RadioButton implicite;
    @FXML
    private RadioMenuItem MRR;
    @FXML
    private RadioMenuItem NDCG;
    @FXML
    private TableView<table> tableMetric;
    @FXML
    private TableColumn<table,String> Metrique;
    @FXML
    private TableColumn<table,String> Resultats;
    @FXML
    private TableColumn<table, String> Algorithme;

    @FXML
    private TableView<table> AlgorithmTable;

    @FXML
    private TableColumn<table, String> resrecommandation;

    @FXML
    private BarChart<String,Number> barChart;
    @FXML
    private LineChart<String,Number> lineChart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private Button linechart;
    @FXML
    private Button Barchart;
    @FXML
    private Button St;

    @FXML
    private CategoryAxis metric_cat;
    private SimpleStringProperty metrique;
    private SimpleStringProperty resultats;
    private SimpleStringProperty Alg;
    private SimpleStringProperty recommandation;


    public ObservableList<table> data_Metric;
    public ObservableList<table> data_Alg;

    List<table> metricTable=new ArrayList<>();
    List<table> AlgorithmTabl=new ArrayList<>();


     List<evaluation.metrics.Metric> metricsList=new ArrayList<>();
    List<Algorithm> AlgoList=new ArrayList<>();

    private Scene scene;
    //Algorithm
    String Algorithm;
    Algorithm ReC = new RecentlyClicked();
    Algorithm rand = new Random();
    Algorithm MoP = new MostPopular();
    Algorithm RcP = new RecentlyPopular();

    Algorithm CoC = new FastSessionCoOccurrence();
    //MEtrics
    Metric  click=new ClickThroughRate();
    Metric f1=new F1();
    Metric map=new MAP();
    Metric ndcg=new NDCG();
    Metric mrr=new MRR();
    Metric precision=new PrecisionOrRecall();
    Metric meanf1=new MeanF1();

    @FXML
    private void Quit(MouseEvent MOUSE_CLICKED) {
        System.exit(0);
    }

    @FXML
    void minimize(MouseEvent MOUSE_CLICKED) {

        Stage stage = (Stage) rootpane.getScene().getWindow();
        stage = (Stage) ((Button) MOUSE_CLICKED.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }
    @FXML
    void back(MouseEvent MOUSE_CLICKED) throws IOException {

        Pane p= FXMLLoader.load(getClass().getResource("page2EnLigne.fxml"));
        rootpane.getChildren().setAll(p);
    }

    @FXML
    public void getlist( ActionEvent event) {
        if (ClickThroughRate.isSelected() && (!metricsList.contains(click))) {

            metricsList.add(click);

        }else{
            if (!ClickThroughRate.isSelected() && (metricsList.contains(click))) {
                metricsList.remove(click);
            }
        }
        if (F1.isSelected()&& (!metricsList.contains(f1))) {
            metricsList.add(f1);

        }else {
            if (!F1.isSelected() && (metricsList.contains(f1)))
                metricsList.remove(f1);


        }
        if (MAP.isSelected()&& (!metricsList.contains(map))) {
            metricsList.add(map);

        }else{if (!MAP.isSelected()&& (metricsList.contains(map)))
            metricsList.remove(map);

        }
        if (MRR.isSelected()&& (!metricsList.contains(mrr))) {
            metricsList.add(mrr);

        }else{
            if(!MRR.isSelected()&& (metricsList.contains(mrr)))
                metricsList.remove(mrr);

            }
        if (MeanF1.isSelected()&& (!metricsList.contains(meanf1))) {
            metricsList.add(meanf1);

        }else{
            if (!MeanF1.isSelected()&& (metricsList.contains(meanf1)))
                metricsList.remove(meanf1);
        }
        if (NDCG.isSelected()&& (!metricsList.contains(ndcg))) {
            metricsList.add(ndcg);

        }else{
            if (!NDCG.isSelected()&& (metricsList.contains(ndcg)))
                metricsList.remove(ndcg);

            }
    }
    @FXML
    public void getlistAlgo( ActionEvent event) {
        if (Random.isSelected() && (!AlgoList.contains(rand))) {

            AlgoList.add(rand);

        }else{
            if (!Random.isSelected() && (AlgoList.contains(rand))) {
                AlgoList.remove(rand);
            }
        }
        if (MostPopular.isSelected()&& (!AlgoList.contains(MoP))) {
            AlgoList.add(MoP);

        }else {
            if (!MostPopular.isSelected() && (AlgoList.contains(MoP)))
                AlgoList.remove(MoP);


        }
        if (RecentlyClicked.isSelected()&& (!AlgoList.contains(ReC))) {
            AlgoList.add(ReC);

        }else{if (!RecentlyClicked.isSelected()&& (AlgoList.contains(ReC)))
            AlgoList.remove(ReC);

        }
        if (RecentlyPopular.isSelected()&& (!AlgoList.contains(RcP))) {
            AlgoList.add(RcP);

        }else{
            if(!RecentlyPopular.isSelected()&& (AlgoList.contains(RcP)))
                metricsList.remove(RcP);

        }
        if (CoOccurence.isSelected()&& (!AlgoList.contains(CoC))) {
            AlgoList.add(CoC);

        }else{
            if (!CoOccurence.isSelected()&& (AlgoList.contains(CoC)))
                AlgoList.remove(CoC);
        }




    }
    @FXML
    private void Gr(ActionEvent event) {
        if (!Barchart.isPressed()) {
            barChart.setVisible(true);

            int j=0;
            for (int i = 0; i < AlgoList.size(); i++) {
                ArrayList met=new ArrayList<>();
                ArrayList algname=new ArrayList<>();
                while((j<Evaluator.ResmetricsResult.size())&&(!met.contains(Evaluator.ResmetricsName.get(j)))){
                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    met.add(Evaluator.ResmetricsName.get(j));
                    series.setName(Evaluator.ResmetricsName.get(j));
                    series.getData().add(new XYChart.Data<>(((AlgoList.get(i).toString()).split("\\.")[1]).split("@")[0],Float.parseFloat(Evaluator.ResmetricsResult.get(j))));
                    barChart.getData().addAll(series);

                    barChart.getXAxis().setAnimated(false);
                    j++;
                }}}

    }
    @FXML
    private void line_Gr(ActionEvent event) {
        if (!linechart.isPressed()) {
            lineChart.setVisible(true);

            int j=0;
            for (int i = 0; i < AlgoList.size(); i++) {
                ArrayList met=new ArrayList<>();
                ArrayList algname=new ArrayList<>();
                while((j<Evaluator.ResmetricsResult.size())&&(!met.contains(Evaluator.ResmetricsName.get(j)))){
                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    met.add(Evaluator.ResmetricsName.get(j));
                    series.setName(Evaluator.ResmetricsName.get(j));
                    series.getData().add(new XYChart.Data<>(((AlgoList.get(i).toString()).split("\\.")[1]).split("@")[0],Float.parseFloat(Evaluator.ResmetricsResult.get(j))));
                    lineChart.getData().add(series);
                    j++;
                }}}

    }
    @FXML
    private void bar(ActionEvent event) {
        if (!Barchart.isPressed()) {
            barChart.setVisible(true);


        }
    }



        @FXML
        private void start (ActionEvent event) {

                if (explicite.isSelected()) {
                    Sender.onlineStrategy(page2EnLigne.getLien_item(), page2EnLigne.getLien_event(), AlgoList, getLimiteValue(), getTimeValue(), metricsList);
                    //Algorithms
                    for (Algorithm alg : AlgoList) {
                        AlgorithmTabl.add(new table("le protocole choisis :", "EN Ligne", 0.0));
                        AlgorithmTabl.add(new table("le system de recommandation choisis :", ((alg.toString()).split("\\.")[1]).split("@")[0], 0.0));
                        AlgorithmTabl.add(new table("la méthode pour choisisr le temps d'évaluation  :", "explicite", 0.0));
                        AlgorithmTabl.add(new table("le nombre de limites de recommandation  :", limiteValue.toString(), 0.0));
                    }

                    data_Alg = FXCollections.observableArrayList(AlgorithmTabl);
                    Algorithme.setCellValueFactory(new PropertyValueFactory<table, String>("Alg"));
                    resrecommandation.setCellValueFactory(new PropertyValueFactory<table, String>("recommandation"));
                    AlgorithmTable.setItems(data_Alg);
                    //Metrics
                    for (int i = 0; i < Evaluator.ResmetricsName.size(); i++) {
                        metricTable.add(new table(Evaluator.ResmetricsName.get(i), Evaluator.ResmetricsResult.get(i)));
                    }
                    data_Metric = FXCollections.observableArrayList(metricTable);
                    Metrique.setCellValueFactory(new PropertyValueFactory<table, String>("metrique"));
                    Resultats.setCellValueFactory(new PropertyValueFactory<table, String>("resultats"));
                    tableMetric.setItems(data_Metric);
                    //appel fonction explicite

                } else {
                    if (implicite.isSelected()) {


                        //appel fonction with IA
                        Sender.onlineStrategyWithIa(page2EnLigne.getLien_item(), page2EnLigne.getLien_event(), AlgoList, getLimiteValue(), metricsList);

                        //Algorithms
                        for (Algorithm alg : AlgoList) {
                            AlgorithmTabl.add(new table("le protocole choisis :", "EN Ligne", 0.0));
                            AlgorithmTabl.add(new table("le system de recommandation choisis :", ((alg.toString()).split("\\.")[1]).split("@")[0], 0.0));
                            AlgorithmTabl.add(new table("la méthode pour choisisr le temps d'évaluation  :", "implicite", 0.0));
                            AlgorithmTabl.add(new table("le nombre de limites de recommandation  :", limiteValue.toString(), 0.0));
                        }
                        data_Alg = FXCollections.observableArrayList(AlgorithmTabl);
                        Algorithme.setCellValueFactory(new PropertyValueFactory<table, String>("Alg"));
                        resrecommandation.setCellValueFactory(new PropertyValueFactory<table, String>("recommandation"));
                        AlgorithmTable.setItems(data_Alg);
                        //Metrics
                        for (int i = 0; i < Evaluator.ResmetricsName.size(); i++) {
                            metricTable.add(new table(Evaluator.ResmetricsName.get(i), Evaluator.ResmetricsResult.get(i)));
                        }
                        data_Metric = FXCollections.observableArrayList(metricTable);
                        Metrique.setCellValueFactory(new PropertyValueFactory<table, String>("metrique"));
                        Resultats.setCellValueFactory(new PropertyValueFactory<table, String>("resultats"));
                        tableMetric.setItems(data_Metric);
                    }

                }

            }



    @FXML

    private void explicitefunc(ActionEvent event) {

        if (!explicite.isPressed()) {
            temps.setVisible(true);

        }
    }
    @FXML
    private void implicitefunc(ActionEvent event) {

        if (!explicite.isPressed()) {
            temps.setVisible(false);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        temps.setVisible(false);

        SpinnerValueFactory<Integer> valueFactoryLimites = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        valueFactoryLimites.setValue(1);
        limites.setValueFactory(valueFactoryLimites);
        limiteValue = (int) limites.getValue();
        limites.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                limites.setValueFactory(valueFactoryLimites);
                limiteValue = (int) limites.getValue();
            }
        });
        SpinnerValueFactory<Integer> valueFactoryTime = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        valueFactoryTime.setValue(1);

        temps.setValueFactory(valueFactoryTime);
        timeValue = Long.valueOf(temps.getValue());
        temps.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                temps.setValueFactory(valueFactoryTime);
                timeValue = Long.valueOf(temps.getValue());
            }
        });
    }
    public Long getTimeValue() {
        return timeValue;
    }

    public Integer getLimiteValue() {
        return limiteValue;
    }
}
















