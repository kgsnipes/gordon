package com.kgsnipes.image.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class MinifyHtml {

	
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
		//String str=IOUtils.toString(new FileReader(f));
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
		IOUtils.write(docType+temp, new FileOutputStream(new File(destpath+f.getName())));
		
	}

}
