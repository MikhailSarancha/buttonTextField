package com.example.buttontextfield;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        Button button1 = new Button("Button1");
        Button button2 = new Button("Button2");
        Button button3 = new Button("Тык");
        button2.setLayoutX(300);
        button3.setLayoutX(200);
        button3.setLayoutY(400);
        TextField textField = new TextField("Тут будет площадь");
        textField.setLayoutY(300);

//        Bounds bounds1 = button1.getBoundsInLocal();
//        Bounds bounds2 = button2.getBoundsInLocal();


        CoordsOnMousePressed coordsOnMousePressed = new CoordsOnMousePressed();

        Pane pane = new Pane(button1, button2, button3, textField);



        Scene scene = new Scene(pane, 400, 500);
        stage.setScene(scene);
        stage.show();
//перемещаем первую кнопку
        button1.setOnMousePressed((mouseEvent) -> {
            coordsOnMousePressed.setX(mouseEvent.getSceneX() - button1.getLayoutX());
            coordsOnMousePressed.setY(mouseEvent.getSceneY() - button1.getLayoutY());
        });
        button1.setOnMouseDragged((mouseEvent) -> {
            button1.setLayoutX(mouseEvent.getSceneX() - coordsOnMousePressed.getX());
            button1.setLayoutY(mouseEvent.getSceneY() - coordsOnMousePressed.getY());
        });
//перемещаем вторую кнопку
        button2.setOnMousePressed((mouseEvent) -> {
            coordsOnMousePressed.setX(mouseEvent.getSceneX() - button2.getLayoutX());
            coordsOnMousePressed.setY(mouseEvent.getSceneY() - button2.getLayoutY());
        });
        button2.setOnMouseDragged((mouseEvent) -> {
            button2.setLayoutX(mouseEvent.getSceneX() - coordsOnMousePressed.getX());
            button2.setLayoutY(mouseEvent.getSceneY() - coordsOnMousePressed.getY());
        });

//Вычисляем площадь
        button3.setOnMouseClicked(mouseEvent -> {
            boolean flag = true;
            Bounds bounds1 = button1.localToScene(button1.getBoundsInLocal());
            Bounds bounds2 = button2.localToScene(button2.getBoundsInLocal());
            System.out.println((bounds1.intersects(bounds2)));
            if (bounds1.intersects(bounds2)){
                flag = true;
                double x1 = bounds1.getMinX();
                double y1 = bounds1.getMinY();
                double w1 = bounds1.getWidth();
                double h1 = bounds1.getHeight();

                double x2 = bounds2.getMinX();
                double y2 = bounds2.getMinY();
                double w2 = bounds2.getWidth();
                double h2 = bounds2.getHeight();

                double overlapX = Math.max(0, Math.min(x1 + w1, x2 + w2) - Math.max(x1, x2));
                double overlapY = Math.max(0, Math.min(y1 + h1, y2 + h2) - Math.max(y1, y2));

                double overlapArea = overlapX * overlapY;
                textField.setText(Double.toString(overlapArea));
                //создадим прямоугольник для подсветки
                Rectangle intersectionRectangle = new Rectangle(
                        Math.max(x1, x2),
                        Math.max(y1, y2),
                        overlapX,
                        overlapY
                );
                intersectionRectangle.setFill(Color.RED);
                intersectionRectangle.setStroke(Color.BLACK);
                intersectionRectangle.setStrokeWidth(1.0);

                // Добавляем прямоугольник на контейнер Pane
                pane.getChildren().add(intersectionRectangle);
            }
            else{
                textField.setText("Не пересекаются");
                int count = pane.getChildren().size()-1;
                while (count!=3){
                    pane.getChildren().remove(count);
                    count-=1;
                }
            }
        });

    }
}