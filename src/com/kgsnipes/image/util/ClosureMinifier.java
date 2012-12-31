/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kgsnipes.image.util;

import java.io.File;
import com.google.javascript.jscomp.Compiler;

/**
 *
 * @author kaushikganguly
 */
public class ClosureMinifier {
    
    
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
		Compiler compiler=new Compiler(System.out);
                //compiler.com
               // compiler.compile(null, null, null) 
		//IOUtils.write(docType+temp, new FileOutputStream(new File(destpath+f.getName())));
		
	}
	
    
    
}
