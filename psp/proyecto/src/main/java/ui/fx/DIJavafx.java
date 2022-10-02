package ui.fx;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.enterprise.util.AnnotationLiteral;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class DIJavafx extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(700);
        primaryStage.setResizable(false);
        container.getBeanManager().fireEvent(primaryStage, new AnnotationLiteral<StartupScene>() {
        });
    }

}
