/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pug;

import java.util.HashMap;

/**
 *
 * @author Thanura
 */
public class Compiler {

    public String renderFormtext(String text) {
        text = text.trim();
        Block roothtml = new Block(text.split("\n")[0],0);
        HashMap<String, Boolean> area = new HashMap<>();
        String linebyline[] = text.split("\n");
        String currentpath = "0";
        int oldindet = 0;
        for (int i = 1; i < linebyline.length; i++) {
            String lines = linebyline[i];
            int newindent = indetCount(lines);
            currentpath = partOfPath(currentpath,  newindent-oldindet);
            roothtml.addToBlock(new Block(lines.trim(),newindent), currentpath);
            oldindet = newindent;
        }
        roothtml.updateInnertxt();
        return roothtml.toString();
    }

    private int indetCount(String s) {
        if (s.charAt(1) != ' ' && s.charAt(1) != '\t') {
            return 0;
        }
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ' && s.charAt(i) != '\t') {
                return (s.charAt(i - 1) == '\t' ? i : i / 2);
            }
        }
        return 0;
    }
    
    private String partOfPath(String currentpath,int s){
        if(s < 0){
           String rootpath = currentpath.substring(0, currentpath.length()+(s*2));
           int numb = Integer.parseInt(rootpath.substring(rootpath.length()-1))+1;
           return rootpath.substring(0,rootpath.length()-2)+"."+numb;
        }else if(s > 0){
           return currentpath+".0";
        }else{
           int numb = Integer.parseInt(currentpath.substring(currentpath.length()-1))+1;
           return currentpath.substring(0, currentpath.length()-2)+"."+numb;
        }
    }
}
