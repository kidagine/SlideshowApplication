/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slideshow.gui.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import slideshow.gui.model.SlideshowModel;

/**
 *
 * @author Acer
 */
public class SlideshowViewController implements Initializable {
    
    private SlideshowModel model;
    private ScheduledExecutorService executor;
    
    @FXML
    private ImageView imvSlideshow;
    @FXML
    private StackPane stcSlideshow;
    @FXML
    private ToggleButton btnPlay;
    @FXML
    private Button btnPreviousImage;
    @FXML
    private Button btnNextImage;
    @FXML
    private Label lblImageName;
    @FXML
    private GridPane grdSlideshowsTabs;
    @FXML
    private ToggleGroup tglSlideshowsTabs;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        disableButtons();
        setTabsToggle();
        setImageView();
        grdSlideshowsTabs.setMouseTransparent(true);
    }    
    
    private void setTabsToggle()
    {
        tglSlideshowsTabs.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal == null)
            {
                oldVal.setSelected(true);
            }
        });
    }
    
    public void injectModel(SlideshowModel model)
    {
        this.model = model;
    }
    
    private void setImageView()
    {
        DoubleProperty mvw = imvSlideshow.fitWidthProperty();
        DoubleProperty mvh = imvSlideshow.fitHeightProperty();
        mvw.bind(Bindings.selectDouble(stcSlideshow.widthProperty()));
        mvh.bind(Bindings.selectDouble(stcSlideshow.heightProperty()));
    }
    
    public void disableButtons()
    {
        btnPlay.setDisable(true);
        btnNextImage.setDisable(true);
        btnPreviousImage.setDisable(true);
    }
    
    public void enableButtons()
    {
        btnPlay.setDisable(false);
        btnNextImage.setDisable(false);
        btnPreviousImage.setDisable(false);
    }
    
    private FileChooser createImageChooser()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");
        FileChooser.ExtensionFilter generalFilter = new FileChooser.ExtensionFilter("All Image Files", "*.png", "*.jpg", "*.jpeg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG (*.png)", "*.png");
        FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter("JPEG (*.jpeg, *.jpg)","*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(generalFilter);
        fileChooser.getExtensionFilters().add(pngFilter);
        fileChooser.getExtensionFilters().add(jpegFilter);  
        return fileChooser;
    }
    
    private void displayImage()
    {
        imvSlideshow.setImage(model.getCurrentImage());
        setImageNameLabel();
    }

    @FXML
    private void clickPreviousImage(ActionEvent event) 
    {
        previousImage();
    }

    @FXML
    private void clickNextImage(ActionEvent event) 
    {
        nextImage();
    }

    @FXML
    private void clickPlaySlideshow(ActionEvent event) throws InterruptedException 
    {
        if(btnPlay.isSelected())
        {
            executor = Executors.newScheduledThreadPool(2);
            executor.scheduleAtFixedRate(() -> nextSlideshow(), 2, 2, TimeUnit.SECONDS);
            executor.submit(() -> playSlideshow());
        }
        else
        {
            executor.shutdownNow();
        }
    }
    
    private void nextSlideshow()
    {
        model.nextSlideshow();
        Platform.runLater(() -> selectCurrentSlideshowTab());
                
    }
    
    private void playSlideshow()
    {
        try
        {
            while(true)
            {
                TimeUnit.MILLISECONDS.sleep(SlideshowModel.SLIDES_BREAK_TIME);
                Platform.runLater(() -> nextImage());
            }
        }
        catch(InterruptedException ex)
        {
            System.out.println("Slideshow stopped");
        }
    }
    
    private synchronized void nextImage()
    {
        imvSlideshow.setImage(model.nextImage());
        setImageNameLabel();
    }
    
    private void previousImage()
    {
        imvSlideshow.setImage(model.previousImage());
        setImageNameLabel();
    }
    
    private void setImageNameLabel()
    {
        lblImageName.setText(model.getCurrentImageName());
    }

    @FXML
    private void clickLoadSlideshow(ActionEvent event) 
    {
        FileChooser fileChooser = createImageChooser();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if(selectedFiles != null)
        {
            model.addSlideshow(selectedFiles);
            setViewForSlideshow();
        }
    }
    
    private void setViewForSlideshow()
    {
        displayImage();
        selectCurrentSlideshowTab();
        enableButtons();
    }
    
    private void selectCurrentSlideshowTab()
    {
        int slideshowIndex = model.getCurrentSlideshowIndex();
        ToggleButton button = (ToggleButton) grdSlideshowsTabs.getChildren().get(slideshowIndex);
        button.setSelected(true);
        
    }
}
