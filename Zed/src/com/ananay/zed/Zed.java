package com.ananay.zed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Zed {
    static boolean hadError=false;
    public static void main(String [] args) throws IOException{

        if(args.length>1){
            System.out.println("Usage: jZed[Script]");
            System.exit(64);
        }
        else if(args.length==1){
            // running Zed by giving a path in the command line
            runFile(args[0]);
        }
        else{
            // running Zed interactively, prompt by prompt
            runPrompt();
        }
    }
}


private static void runFile(String path) throws IOException{
    // reading all the contents of the files as binary array
    byte[] bytes = Files.readAllBytes(Paths.get(path));
    // converting the read bytes to string
    String fileContent=new String (bytes, Charset.defaultCharset());
    run(fileContent);
    if(hadError)System.exit(65);

}

private static void runPrompt() throws IOException{

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    while(true){
        System.out.println(">>>");
        String line = reader.readLine();
        if(line==null){
            break;
        }
        run(line);
        hadError=false;
    }
}

private static void run(String source){
    Scanner scanner = new Scanner(source);
    List<Token> tokens= scanner.scanTokens;
    for(Token token:tokens){
        System.out.println(token);
    }
}

static void error(int line, String message){
    report(line, " ", message);
}

private static void report(int line, String where, String message){
    System.err.println("[line " + line + "] Error" + where + ": " + message));
    hadError=true;
}