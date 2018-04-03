package br.com.syntech.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class FxChartUtil {

	// Create XYChart Country Data
	public static ObservableList<XYChart.Series<Number, Number>> getCountrySeries() {

		XYChart.Series<Number, Number> seriesUSA = new XYChart.Series<Number, Number>();
		seriesUSA.setName("Recebidos");

		seriesUSA.getData().add(new XYChart.Data<Number, Number>(1, 30000));
		seriesUSA.getData().add(new XYChart.Data<Number, Number>(2, 28500));
		seriesUSA.getData().add(new XYChart.Data<Number, Number>(3, 25500));
		seriesUSA.getData().add(new XYChart.Data<Number, Number>(4, 31000));
		seriesUSA.getData().add(new XYChart.Data<Number, Number>(5, 34000));
		seriesUSA.getData().add(new XYChart.Data<Number, Number>(6, 10000));
		seriesUSA.getData().add(new XYChart.Data<Number, Number>(7, 10000));
		seriesUSA.getData().add(new XYChart.Data<Number, Number>(8, 40900));
		seriesUSA.getData().add(new XYChart.Data<Number, Number>(9, 10000));
		seriesUSA.getData().add(new XYChart.Data<Number, Number>(10, 10000));
		seriesUSA.getData().add(new XYChart.Data<Number, Number>(11, 10000));
		seriesUSA.getData().add(new XYChart.Data<Number, Number>(12, 28500));

		ObservableList<XYChart.Series<Number, Number>> data = FXCollections.<XYChart.Series<Number, Number>>observableArrayList();
		data.add(seriesUSA);
		return data;
	}

	// Create XYChart Year Data
	public static ObservableList<XYChart.Series<String, Number>> getYearSeries() {
		XYChart.Series<String, Number> series1950 = new XYChart.Series<String, Number>();
		series1950.setName("1950");
		series1950.getData().add(new XYChart.Data<String, Number>("China", 555));
		series1950.getData().add(new XYChart.Data<String, Number>("India", 358));
		series1950.getData().add(new XYChart.Data<String, Number>("Brazil", 54));
		series1950.getData().add(new XYChart.Data<String, Number>("UK", 50));
		series1950.getData().add(new XYChart.Data<String, Number>("USA", 158));

		XYChart.Series<String, Number> series2000 = new XYChart.Series<String, Number>();
		series2000.setName("2000");
		series2000.getData().add(new XYChart.Data<String, Number>("China", 1275));
		series2000.getData().add(new XYChart.Data<String, Number>("India", 1017));
		series2000.getData().add(new XYChart.Data<String, Number>("Brazil", 172));
		series2000.getData().add(new XYChart.Data<String, Number>("UK", 59));
		series2000.getData().add(new XYChart.Data<String, Number>("USA", 285));

		XYChart.Series<String, Number> series2050 = new XYChart.Series<String, Number>();
		series2050.setName("2050");
		series2050.getData().add(new XYChart.Data<String, Number>("China", 1395));
		series2050.getData().add(new XYChart.Data<String, Number>("India", 1531));
		series2050.getData().add(new XYChart.Data<String, Number>("Brazil", 233));
		series2050.getData().add(new XYChart.Data<String, Number>("UK", 66));
		series2050.getData().add(new XYChart.Data<String, Number>("USA", 409));

		ObservableList<XYChart.Series<String, Number>> data = FXCollections.<XYChart.Series<String, Number>>observableArrayList();
		data.add(series1950);
		data.add(series2000);
		data.add(series2050);
		return data;
	}

}