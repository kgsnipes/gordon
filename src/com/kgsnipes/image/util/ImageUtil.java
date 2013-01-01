/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kgsnipes.image.util;

import com.google.javascript.jscomp.CommandLineRunner;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import com.yahoo.platform.yui.compressor.YUICompressor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

/**
 *
 * @author kaushik
 */
public class ImageUtil {
    
    public static String outputFolder="";
    
    
    public ImageUtil()
    {
        
    }
    public static void writeOptimizedImage(String source,String dest,float rate,String formatName) throws FileNotFoundException, IOException
    {
       
        //Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
          Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(formatName);
		ImageWriter writer = (ImageWriter)iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwp.setCompressionQuality(rate);
		File file = new File(dest);
		FileImageOutputStream output = new FileImageOutputStream(file);
		writer.setOutput(output);
		IIOImage image = new IIOImage(ImageIO.read(new File(source)), null, null);
		writer.write(null, image, iwp);
		writer.dispose();
               
    }
    
    
    public static void writeOptimized(File source,int percentage)throws Exception
    {
        String optFile=null;
        
        float rate=0.0f;
        if(percentage>0)
            rate=(float)((float)percentage/(float)100);
       // System.out.println(percentage);
         //System.out.println(rate);
        File f=source;
       //System.out.println(f.getParentFile());
       //System.out.println(f.getParentFile().isDirectory());
       if(f.getParentFile().isDirectory())
       {
           optFile=f.getParent()+File.separator+"optimized";
           System.out.println(optFile);
           File ff=new File(optFile);
           if(!ff.exists())
                ff.mkdir();
           
           if(new File(optFile+File.separator+source.getName()).exists())
           {
               new File(optFile+File.separator+source.getName()).delete();
               //System.out.println("the file already exists");
              
           }
           
           outputFolder=optFile;
           if(source.getName().endsWith(".jpg")|| source.getName().endsWith(".jpeg") )
            writeOptimizedImage(source.getAbsolutePath(),optFile+File.separator+source.getName(),rate,"jpeg");
           else if(source.getName().endsWith(".html") || source.getName().endsWith(".jsp") || source.getName().endsWith(".php") || source.getName().endsWith(".vm") || source.getName().endsWith(".htm"))
               compressHtmlFiles(source.getAbsolutePath(),optFile+File.separator+source.getName());
           else if(source.getName().endsWith(".js"))
               compressFilesWithYUINative(source.getAbsolutePath(),optFile+File.separator+source.getName());
           else if(source.getName().endsWith(".css"))
               compressFilesWithYUINative(source.getAbsolutePath(),optFile+File.separator+source.getName());
       }
        
       
        
        
    }
    
    
    public static void compressHtmlFiles(String sourcePath,String destpath) throws Exception
	{
		//String str=IOUtils.toString(new FileReader(f));
                
                File f=new File(sourcePath);
               
                
		List<String> str=IOUtils.readLines(new FileReader(f));
		StringBuilder sb=new StringBuilder();
		String docType="";
		int count=0;
		for(String s:str)
		{
			if(count==0 && s.indexOf("DOCTYPE")!=-1)
			{
				docType=s;
				count+=1;
				continue;
			}
			sb.append(StringUtils.trim(s));
		}
		
		String temp=sb.toString();
		temp=temp.replaceAll("(?s)<!--.*?-->","");
		IOUtils.write(docType+temp, new FileOutputStream(new File(destpath)));
                 
		
	}
    
    
    public static void compressFilesWithYUI(String sourcePath,String destpath) throws Exception
	{
            
            
                File f=new File(sourcePath);
                
            Reader in=new FileReader(f);
		
		if(f.getName().endsWith(".js"))
                {
                   JavaScriptCompressor compressor = new JavaScriptCompressor(in, new ErrorReporter() {

                                public void warning(String message, String sourceName,
                                        int line, String lineSource, int lineOffset) {
                                    if (line < 0) {
                                        System.err.println("\n[WARNING] " + message);
                                    } else {
                                        System.err.println("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
                                    }
                                }

                                public void error(String message, String sourceName,
                                        int line, String lineSource, int lineOffset) {
                                    if (line < 0) {
                                        System.err.println("\n[ERROR] " + message);
                                    } else {
                                        System.err.println("\n[ERROR] " + line + ':' + lineOffset + ':' + message);
                                    }
                                }

                                public EvaluatorException runtimeError(String message, String sourceName,
                                        int line, String lineSource, int lineOffset) {
                                    error(message, sourceName, line, lineSource, lineOffset);
                                    return new EvaluatorException(message);
                                }

               
                            });

                           
                    compressor.compress(new OutputStreamWriter(new FileOutputStream(new File(destpath)), "UTF-8"), Integer.MAX_VALUE,false,false,true,true);
                    
                    
                    
                }
                else
                {
                    
                    
                    CssCompressor compressor = new CssCompressor(in);

                    compressor.compress(new OutputStreamWriter(new FileOutputStream(new File(destpath)), "UTF-8"), Integer.MAX_VALUE);
                }
                
                 
	}
    
    public static void compressFilesWithYUINative(String sourcePath,String destpath) throws Exception
    {
        String type="css";
        System.out.print(destpath);
        if(sourcePath.endsWith(".js"))
            type="js";

        String args[]={"--charset","UTF-8","--type",type,"-o",destpath,sourcePath};
        YUICompressor.main(args);
        
               
    }
    
    
    public static void compressFilesWithClosureNative(String sourcePath,String destpath) throws Exception
    {
        
       // String args[]={"--js",sourcePath,"--js_output_file",destpath};
        //MyCommandLineRunner.main(args);
        
        String cmd=new ImageUtil().getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        cmd=cmd.substring(0, cmd.lastIndexOf("/"))+"/lib/"+"compiler.jar";
       // System.err.print(cmd);
       //System.out.println("java -jar "+cmd+" --js "+sourcePath+" --js_output_file "+destpath); 
        Runtime.getRuntime().exec("java -jar "+cmd+" --js "+sourcePath+" --js_output_file "+destpath);
        
               
    }
    
    
    
    
}
