/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slideshow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import slideshow.gui.controller.SlideshowViewController;
import slideshow.gui.model.SlideshowModel;

/**
 *
 * @author Acer
 */
public class Slideshow extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/slideshow/gui/view/SlideshowView.fxml"));
        Parent root = fxmlLoader.load();
        
        SlideshowViewController controller = fxmlLoader.getController();
        controller.injectModel(new SlideshowModel());
        
        Scene scene = new Scene(root); 
        stage.setTitle("Pika");
        stage.setScene(scene);
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        Image icon = new Image(getClass().getResourceAsStream("/slideshow/gui/images/pikachu.png"));
        stage.getIcons().add(icon);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
