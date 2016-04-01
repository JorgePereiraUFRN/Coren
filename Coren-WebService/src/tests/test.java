package tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class test {

	public static void main(String[] args) throws IOException {
		
		String dirXml = "widgets";
		String nomArq = "teste";
		String content = "<?xml version='1.0' encoding='UTF-8'?><det nItem= 1><ddmov><codbar>999999999999</codbar><data>99/99/99</data<horas>99:99:99</horas<ddmov><det>";
		
		File diretorio = new File(dirXml);  
        if (!diretorio.exists()) {  
            diretorio.mkdirs();  
        } else{
        	System.out.println("Dir não existe.");
        }
  
        Writer file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dirXml+nomArq),"ISO-8859-1"));  
        file.write(content);  
        file.close();  

	}

}
