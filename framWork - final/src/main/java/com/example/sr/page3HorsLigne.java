package com.example.sr;
import algorithm.*;
import evaluation.Evaluator;
import evaluation.metrics.*;
import javafx.application.Platform;
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
    private MenuButton menumetric;
    @FXML
    private RadioMenuItem ClickThroughRate;
    @FXML
    private RadioMenuItem F1;

    @FXML
    private RadioMenuItem MAP;
    @FXML
    private RadioMenuItem MeanF1;

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
    private CategoryAxis xb;
    @FXML
    private NumberAxis yb;
    @FXML
    private Button Barchart;
    @FXML
    private Button linechart;
    @FXML
    private Button Start;
    @FXML
    private ProgressBar progress;
    Thread runThread;
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
    List<Algorithm> AlgoList=new ArrayList<>();
    String Algorithme;
    //Algorithm
    Algorithm ReC = new RecentlyClicked();
    Algorithm rand = new Random();
    Algorithm MoP = new MostPopular();
    Algorithm RcP = new RecentlyPopular();
    Algorithm ItemCF = new ItemItemCF();
    Algorithm CoC = new CoOccurrence();
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
        barChart.setVisible(true);
        AlgorithmTable.getItems().clear();
        AlgorithmTabl.clear();
        tableMetric.getItems().clear();
        metricTable.clear();
        barChart.getData().clear();
        Evaluator.ResmetricsName.clear();
        Evaluator.ResmetricsResult.clear();
        metricsList.clear();
        Pane p= FXMLLoader.load(getClass().getResource("page2HorsLigne.fxml"));
        rootpane.getChildren().setAll(p);
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
        barChart.getData().clear();
        if (!Barchart.isPressed()) {

            int j = 0;
            for (int i = 0; i < AlgoList.size(); i++) {
                ArrayList met = new ArrayList<>();
                while ((j < Evaluator.ResmetricsResult.size()) && (!met.contains(Evaluator.ResmetricsName.get(j)))) {
                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    met.add(Evaluator.ResmetricsName.get(j));
                    series.setName(Evaluator.ResmetricsName.get(j));
                    series.getData().add(new XYChart.Data<>(((AlgoList.get(i).toString()).split("\\.")[1]).split("@")[0], Float.parseFloat(Evaluator.ResmetricsResult.get(j))));


                    barChart.getData().addAll(series);
                    barChart.getXAxis().setAnimated(false);
                    j++;

                }

                }

            }



    }


    @FXML
    private void st() {
        if ((AlgoList.size() == 0) && (metricsList.size() == 0)) {
            menusystem.setStyle("-fx-border-color:red ; -fx-border-width:2px ");
            new animatefx.animation.Shake(menusystem).play();
            menumetric.setStyle("-fx-border-color:red ; -fx-border-width:2px ");
            new animatefx.animation.Shake(menumetric).play();
        } else {
            if (AlgoList.size() == 0) {
                menusystem.setStyle("-fx-border-color:red; -fx-border-width:2px ");
                new animatefx.animation.Shake(menusystem).play();
            } else {

                if (metricsList.size() == 0) {
                    menumetric.setStyle("-fx-border-color:red; -fx-border-width:2px ");
                    new animatefx.animation.Shake(menumetric).play();
                } else {
                    menusystem.setStyle("-fx-border-color:green; -fx-border-width:2px ");
                    //new animatefx.animation.Shake(menusystem).play();
                    menumetric.setStyle("-fx-border-color:green; -fx-border-width:2px ");
                    //new animatefx.animation.Shake(menumetric).play();
                    Evaluator.ResmetricsName.clear();
                    Evaluator.ResmetricsResult.clear();
                    Sender.offlineStrategy(page2Horsligne.getLien_item(), page2Horsligne.getLien_event(), AlgoList, getLimiteValue(), getRatioValue(), metricsList);

                    for (Algorithm alg : AlgoList) {

                        AlgorithmTabl.add(new table("le protocole choisis :", "Hors Ligne", 0.0));
                        AlgorithmTabl.add(new table("le system de recommandation choisis :", ((alg.toString()).split("\\.")[1]).split("@")[0], 0.0));
                        AlgorithmTabl.add(new table("le Ratio :", ratioValue.toString(), 0.0));
                        AlgorithmTabl.add(new table("le nombre de limites de recommandation  :", limiteValue.toString(), 0.0));

                    }

                    data_Alg = FXCollections.observableArrayList(AlgorithmTabl);
                    Algorithm.setCellValueFactory(new PropertyValueFactory<table, String>("Alg"));
                    resrecommandation.setCellValueFactory(new PropertyValueFactory<table, String>("recommandation"));
                    AlgorithmTable.getItems().clear();
                    //AlgorithmTable.refresh();
                    AlgorithmTable.setItems(data_Alg);
                    AlgorithmTabl.clear();

                    //Metrics
                    for (int i = 0; i < Evaluator.ResmetricsName.size(); i++) {
                        metricTable.add(new table(Evaluator.ResmetricsName.get(i), Evaluator.ResmetricsResult.get(i)));

                    }
                    data_Metric = FXCollections.observableArrayList(metricTable);
                    Metrique.setCellValueFactory(new PropertyValueFactory<table, String>("metrique"));
                    Resultats.setCellValueFactory(new PropertyValueFactory<table, String>("resultats"));
                    tableMetric.setItems(data_Metric);
                    metricsList.clear();

                }
            }
        }
    }

    @FXML
    private void start (ActionEvent event) {
        Platform.runLater(() -> {progress.setVisible(true);
            AlgorithmTable.getItems().clear();
            tableMetric.getItems().clear();
            metricTable.clear();
            barChart.getData().clear();

            Start.setDisable(true);


            runThread = new Thread(() -> {



                st();
                Platform.runLater(() -> {progress.setVisible(false);
                    Start.setDisable(false);

                });
            });
            runThread.start();
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progress.setVisible(false);
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
