/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slideshow.gui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

/**
 *
 * @author Acer
 */
public class SlideshowModel {
    
    private List<Slideshow> slideshows;
    private Slideshow currentSlideshow;
    public static final int SLIDES_BREAK_TIME = 1000;
    
    public SlideshowModel()
    {
        slideshows = new ArrayList();
    }
    
    public void addSlideshow(List<File> imageFiles)
    {
        currentSlideshow = new Slideshow(imageFiles);
        slideshows.add(currentSlideshow);
    }
    
    public int getCurrentSlideshowIndex()
    {
        return slideshows.indexOf(currentSlideshow);
    }
    
    public void nextSlideshow()
    {
        int slideshowIndex = slideshows.indexOf(currentSlideshow);
        if(slideshowIndex != slideshows.size()-1)
        {
            currentSlideshow = slideshows.get(++slideshowIndex);
        }
        else
        {
            currentSlideshow = slideshows.get(0);
        }
    }
    
    public void previousSlideshow()
    {
        int slideshowIndex = slideshows.indexOf(currentSlideshow);
        if(slideshowIndex != 0)
        {
            currentSlideshow = slideshows.get(--slideshowIndex);
        }
        else
        {
            currentSlideshow = slideshows.get(slideshows.size()-1);
        }
    }
    
    private Image convertFileToImage(File file)
    {
        return new Image(file.toURI().toString());
    }
    
    public Image getCurrentImage()
    {
        return convertFileToImage(currentSlideshow.getCurrentImageFile());
    }
    
    public String getCurrentImageName()
    {
        return currentSlideshow.getCurrentImageName();
    }
    
    public Image previousImage()
    {
        return convertFileToImage(currentSlideshow.previousImage());
    }
    
    public Image nextImage()
    {
        return convertFileToImage(currentSlideshow.nextImage());
    }
}
