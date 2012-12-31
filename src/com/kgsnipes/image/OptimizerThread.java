/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kgsnipes.image;

import com.kgsnipes.image.ui.ImageOptimizerForm;
import com.kgsnipes.image.util.ImageUtil;
import java.awt.Desktop;
import java.io.File;
import java.util.List;

/**
 *
 * @author kaushik
 */
public class OptimizerThread extends Thread {
    
    ImageOptimizerForm form=null;
    List<File> fileList=null;
    int percentage=1;
    public OptimizerThread(ImageOptimizerForm f,List<File> files,int percent )
    {
        form=f;
        fileList=files;
        percentage=percent;
        this.start();
    }
    
    public void run()
    {
        try
        {
            form.getjLabel2().setText("Processing...");
            for(int i=0;i<fileList.size();i++)
            {
                ImageUtil.writeOptimized(fileList.get(i),percentage);
            }
            
            form.getjLabel2().setText("Completed.");
            
            if(Desktop.isDesktopSupported())
            {
                Desktop.getDesktop().open(new File(ImageUtil.outputFolder));
            }
            
                form.getjLabel2().setText("Output Folder :"+ImageUtil.outputFolder);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
}
