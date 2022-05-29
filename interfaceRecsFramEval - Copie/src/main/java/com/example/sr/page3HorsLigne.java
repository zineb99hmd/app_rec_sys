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

public class page3HorsLigne implements Initializable {
    @FXML
    Pane rootpane;

    @FXML
    private Spinner<Integer> limites;

    @FXML
    Integer limiteValue;
    @FXML
    private Integer ratioValue;
    @FXML
    Integer timeValue;
    @FXML
    private Slider ratio;
    @FXML
    private MenuButton menusystem;
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
    private RadioMenuItem ItemItemCF;
    @FXML
    private MenuButton menumetric;
    @FXML
    private RadioMenuItem ClickThroughRate;
    @FXML
    private RadioMenuItem F1;
    @FXML
    private RadioMenuItem HypothesisTestableMetric;
    @FXML
    private RadioMenuItem MAP;
    @FXML
    private RadioMenuItem MeanF1;
    @FXML
    private RadioMenuItem Metric;
    @FXML
    private RadioMenuItem MRR;
    @FXML
    private RadioMenuItem NDCG;
    @FXML
    private RadioMenuItem PrecisionOrRecall;
    @FXML
    private TableView<table> tableMetric;
    @FXML
    TableColumn<table,String> Metrique;
    @FXML
    TableColumn<table,String> Resultats;
    @FXML
    private TableColumn<table, String> Algorithm;

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
    private Button Barchart;
    @FXML
    private Button linechart;
    @FXML
    TableColumn<table, String> precorrec;
    private SimpleStringProperty metrique;
    private SimpleStringProperty resultats;
    private SimpleStringProperty Alg;
    private SimpleStringProperty recommandation;


    public ObservableList<table> data_Metric;
    public ObservableList<table> data_Alg;

    List<table> metricTable=new ArrayList<>();
    List<table> AlgorithmTabl=new ArrayList<>();
    List<evaluation.metrics.Metric> metricsList = new ArrayList<>();

    String Algorithme;
    //Algorithm
    Algorithm ReC = new RecentlyClicked();
    Algorithm rand = new Random();
    Algorithm MoP = new MostPopular();
    Algorithm RcP = new RecentlyPopular();
    Algorithm ItemCF = new ItemItemCF();
    Algorithm CoC = new FastSessionCoOccurrence();
    //Metrics
    Metric click = new ClickThroughRate();
    Metric f1 = new F1();
    Metric map = new MAP();
    Metric ndcg = new NDCG();
    Metric mrr = new MRR();
    Metric precision = new PrecisionOrRecall();
    Metric meanf1 = new MeanF1();


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

        Pane p= FXMLLoader.load(getClass().getResource("page2HorsLigne.fxml"));
        rootpane.getChildren().setAll(p);
    }


    @FXML
    public void random(ActionEvent event) {
        if (Random.isSelected()) {
            menusystem.setText(Random.getText());
            Algorithme = Random.getText();
        }
    }

    @FXML
    public void mostPopular(ActionEvent event) {
        if (MostPopular.isSelected()) {
            menusystem.setText(MostPopular.getText());

            Algorithme = MostPopular.getText();
        }
    }

    @FXML
    public void recentlyClicked(ActionEvent event) {
        if (RecentlyClicked.isSelected()) {
            menusystem.setText(RecentlyClicked.getText());

            Algorithme = RecentlyClicked.getText();
        }
    }

    @FXML
    public void recentlyPopular(ActionEvent event) {
        if (RecentlyPopular.isSelected()) {
            menusystem.setText(RecentlyPopular.getText());

            Algorithme = RecentlyPopular.getText();
        }
    }

    @FXML
    public void coccurence(ActionEvent event) {
        if (CoOccurence.isSelected()) {
            menusystem.setText(CoOccurence.getText());

            Algorithme = CoOccurence.getText();
        }
    }

    @FXML
    public void itemItemCF(ActionEvent event) {
        if (ItemItemCF.isSelected()) {
            menusystem.setText(ItemItemCF.getText());

            Algorithme = ItemItemCF.getText();
        }
    }

    @FXML
    public void getlist(ActionEvent event) {
        if (ClickThroughRate.isSelected() && (!metricsList.contains(click))) {

            metricsList.add(click);

        } else {
            if (!ClickThroughRate.isSelected() && (metricsList.contains(click))) {
                metricsList.remove(click);
            }
        }
        if (F1.isSelected() && (!metricsList.contains(f1))) {
            metricsList.add(f1);

        } else {
            if (!F1.isSelected() && (metricsList.contains(f1)))
                metricsList.remove(f1);


        }
        if (MAP.isSelected() && (!metricsList.contains(map))) {
            metricsList.add(map);

        } else {
            if (!MAP.isSelected() && (metricsList.contains(map)))
                metricsList.remove(map);

        }
        if (MRR.isSelected() && (!metricsList.contains(mrr))) {
            metricsList.add(mrr);

        } else {
            if (!MRR.isSelected() && (metricsList.contains(mrr)))
                metricsList.remove(mrr);

        }
        if (MeanF1.isSelected() && (!metricsList.contains(meanf1))) {
            metricsList.add(meanf1);

        } else {
            if (!MeanF1.isSelected() && (metricsList.contains(meanf1)))
                metricsList.remove(meanf1);
        }
        if (NDCG.isSelected() && (!metricsList.contains(ndcg))) {
            metricsList.add(ndcg);

        } else {
            if (!NDCG.isSelected() && (metricsList.contains(ndcg)))
                metricsList.remove(ndcg);

        }

        if (PrecisionOrRecall.isSelected() && (!metricsList.contains(precision))) {
            metricsList.add(precision);

        } else {
            if (!PrecisionOrRecall.isSelected() && (metricsList.contains(precision)))
                metricsList.remove(precision);
        }

        System.out.println("metrics list : " + metricsList);
    }
    @FXML
    private void Gr(ActionEvent event) {
        if (!Barchart.isPressed()) {
            barChart.setVisible(true);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(Algorithme);
            String number;
            for (int i = 0; i < Evaluator.ResmetricsName.size(); i++) {

                series.getData().add(new XYChart.Data<>(Evaluator.ResmetricsName.get(i), Float.parseFloat(Evaluator.ResmetricsResult.get(i))));


            }
            barChart.getData().add(series);


        }}
    @FXML
    private void line_Gr(ActionEvent event) {
        if (!linechart.isPressed()) {
            lineChart.setVisible(true);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(Algorithme);
            String number;
            for (int i = 0; i < Evaluator.ResmetricsName.size(); i++) {

                series.getData().add(new XYChart.Data<>(Evaluator.ResmetricsName.get(i), Float.parseFloat(Evaluator.ResmetricsResult.get(i))));


            }
            lineChart.getData().add(series);


        }}

    @FXML
    private void start(ActionEvent event) {
        if (menusystem.getText().equals("Random")) {

            Sender.offlineStrategy(page2Horsligne.getLien_item(), page2Horsligne.getLien_event(), rand, getLimiteValue(), getRatioValue(), metricsList);


        } else {
            if (menusystem.getText().equals("MostPopular")) {

                Sender.offlineStrategy(page2Horsligne.getLien_item(), page2Horsligne.getLien_event(), MoP, getLimiteValue(), getRatioValue(), metricsList);


            } else {
                if (menusystem.getText().equals("Recently clicked")) {

                    Sender.offlineStrategy(page2Horsligne.getLien_item(), page2Horsligne.getLien_event(), ReC, getLimiteValue(), getRatioValue(), metricsList);



                } else {
                    if (menusystem.getText().equals("Recently Popular")) {

                        Sender.offlineStrategy(page2Horsligne.getLien_item(), page2Horsligne.getLien_event(), RcP, getLimiteValue(), getRatioValue(), metricsList);


                    } else {
                        if (menusystem.getText().equals("Co-Occurence")) {

                            Sender.offlineStrategy(page2Horsligne.getLien_item(), page2Horsligne.getLien_event(), CoC, getLimiteValue(), getRatioValue(), metricsList);


                        } else {
                            if (menusystem.getText().equals("Item-Item-CF")) {

                                Sender.offlineStrategy(page2Horsligne.getLien_item(), page2Horsligne.getLien_event(), ItemCF, getLimiteValue(), getRatioValue(), metricsList);


                            }
                        }
                    }
                }
            }
        }
        //Algorithms
        AlgorithmTabl.add(new table("le protocole choisis :","Hors Ligne", 0.0));
        AlgorithmTabl.add(new table("le system de recommandation choisis :",Algorithme, 0.0));
        AlgorithmTabl.add(new table("le Ratio :",ratioValue.toString(), 0.0));
        AlgorithmTabl.add(new table("le nombre de limites de recommandation  :",limiteValue.toString(), 0.0));
//        for(int i=0;i<Sender.RecList.size();i++){
//
//
//
//            AlgorithmTabl.add(new table(Algorithme,Sender.RecList.get(i),0.0));
//
//        }
        data_Alg = FXCollections.observableArrayList(AlgorithmTabl);
        Algorithm.setCellValueFactory(new PropertyValueFactory<table,String>("Alg"));
        resrecommandation.setCellValueFactory(new PropertyValueFactory<table,String>("recommandation"));
        AlgorithmTable.setItems(data_Alg);

        //Metrics
        for(int i=0;i<Evaluator.ResmetricsName.size();i++){
            metricTable.add(new table(Evaluator.ResmetricsName.get(i),Evaluator.ResmetricsResult.get(i)));
        }


        data_Metric = FXCollections.observableArrayList(metricTable);
        Metrique.setCellValueFactory(new PropertyValueFactory<table,String>("metrique"));
        Resultats.setCellValueFactory(new PropertyValueFactory<table,String>("resultats"));
        tableMetric.setItems(data_Metric);



    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SpinnerValueFactory<Integer> valueFactoryLimites = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        valueFactoryLimites.setValue(1);

        limites.setValueFactory(valueFactoryLimites);
        limiteValue=(int)limites.getValue();
        limites.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                limites.setValueFactory(valueFactoryLimites);
                limiteValue=(int)limites.getValue();
            }
        });

        ratio.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                ratioValue=(int)ratio.getValue();
            }
        });
    }

    public TableView<table> getTableMetric() {
        return tableMetric;
    }







    public Integer getRatioValue() {
        return ratioValue;
    }

    public Integer getLimiteValue() {
        return limiteValue;
    }
}
















