package com.as;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.*;
import com.al.*;
import com.as.*;
import com.generation.*;
public class Ecriture {
	 FileWriter fileWriter = null;
     FileReader fileReader = null;
	public static boolean write(String pathname, String content){
        BufferedWriter fileWriter = null;
        try {
            fileWriter = new BufferedWriter(new FileWriter(new File(pathname)));
            fileWriter.write(content);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        finally {
            if(fileWriter != null)
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}