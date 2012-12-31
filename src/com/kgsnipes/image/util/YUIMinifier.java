/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kgsnipes.image.util;


import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

/**
 *
 * @author kaushikganguly
 */
public class YUIMinifier {
    
    
     public static void minifyFiles(String srcpath,String destpath)throws Exception
	{
		File src=new File(srcpath);
		if(src.isDirectory())
		{
			for(File ff:src.listFiles())
			{
				if(!ff.isDirectory())
					compressFiles(ff,destpath);
				//System.out.println(ff.getName());
			}
		}
	}
	
	public static void compressFiles(File f,String destpath) throws Exception
	{
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

                            // Close the input stream first, and then open the output stream,
                            // in case the output file should override the input file.
                            in.close(); in = null;
                    
                    
                    
                    
                }
                else
                {
                    
                    
                    CssCompressor compressor = new CssCompressor(in);

                        // Close the input stream first, and then open the output stream,
                        // in case the output file should override the input file.
                        in.close(); in = null;

                       /* if (outputFilename == null) {
                            out = new OutputStreamWriter(System.out, charset);
                        } else {
                            out = 
                        }*/

                        compressor.compress(new OutputStreamWriter(new FileOutputStream(new File(destpath+f.getName())), "UTF-8"), Integer.MAX_VALUE);
                }
	}
        
        
        
}
