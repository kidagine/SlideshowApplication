/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slideshow.gui.model;

import java.io.File;
import java.util.List;

/**
 *
 * @author Acer
 */
public class Slideshow {
    
    private File currentImageFile;
    private List<File> imageFiles;
       
    public Slideshow(List<File> imageFiles)
    {
        this.imageFiles = imageFiles;
        currentImageFile = imageFiles.get(0);
    }
    
    public File getCurrentImageFile()
    {
        return currentImageFile;
    }
    
    public String getCurrentImageName()
    {
        return currentImageFile.getName();
    }
    
    public File previousImage()
    {
        int imageIndex = imageFiles.indexOf(currentImageFile);
        if(imageIndex != 0)
        {
            currentImageFile = imageFiles.get(--imageIndex);
            return currentImageFile;
        }
        else
        {
            currentImageFile = imageFiles.get(imageFiles.size()-1);
            return currentImageFile;
        }
    }
    
    public File nextImage()
    {
        int imageIndex = imageFiles.indexOf(currentImageFile);
        if(imageIndex != imageFiles.size()-1)
        {
            currentImageFile = imageFiles.get(++imageIndex);
            return currentImageFile;
        }
        else
        {
            currentImageFile = imageFiles.get(0);
            return currentImageFile;
        }
    }
    
}
