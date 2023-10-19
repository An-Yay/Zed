package com.ananay.zed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static  com.ananay.zed.TokenType.*;
public class Scanner {
   private final String source;
   private int start=0;
   private int current=0;
   private int line =1;
   private final List<Token> tokens = new ArrayList<>();

   Scanner(String source){
       this.source = source;
   }

   List<Token> scanTokens(){
       while(!isAtEnd()){
           // beginning of the next lexeme
           start=current;
           scanToken();
       }


       tokens.add(new Token(EOF, " ", null, line));
       return tokens;
   }

   private boolean isAtEnd(){
       return current>=source.length();
   }

   private void scanToken(){
       // take the next character and return it
       char c = advance();
       switch (c){
           case'(': addToken(LEFT_PAREN); break;
           case')': addToken(RIGHT_PAREN); break;
           case '{': addToken(LEFT_BRACE); break;
           case '}': addToken(RIGHT_BRACE); break;
           case ',': addToken(COMMA); break;
           case '.': addToken(DOT); break;
           case '-': addToken(MINUS); break;
           case '+': addToken(PLUS); break;
           case ';': addToken(SEMICOLON); break;
           case '*': addToken(STAR); break;
           default:
               Zed.error(line, "Unexpected character.");
               break;
       }
   }

   private char advance(){
       current++;
       return source.charAt(current-1);
   }

   private void addToken(TokenType type){
       // function overloading
       Object literal = null;
       addToken(type, literal);
   }

   private void addToken(TokenType type, Object literal){
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
   }


}
