package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class test {

	public static void main(String[] args) throws IOException {

		String dirXml = "widgets";
		String nomArq = "teste.txt";
		String content = "<?xml version='1.0' encoding='UTF-8'?><det nItem= 1><ddmov><codbar>999999999999</codbar><data>99/99/99</data<horas>99:99:99</horas<ddmov><det>";

		FileWriter arq = new FileWriter("" + dirXml + "/" + nomArq);
		PrintWriter gravarArq = new PrintWriter(arq);

		gravarArq.printf(content);
		arq.close();

		System.out.println("finished");

	}

}
