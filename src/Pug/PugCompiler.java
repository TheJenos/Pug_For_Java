/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pug;

import java.util.HashMap;

/**
 *
 * @author Thanura
 */
public class PugCompiler {

    public String renderFormtext(String text) {
        text = text.trim();
        Block roothtml = new Block(text.split("\n")[0], 0);
        HashMap<String, Boolean> area = new HashMap<>();
        String linebyline[] = text.split("\n");
        String currentpath = "0";
        int oldindet = 0;
        for (int i = 1; i < linebyline.length; i++) {
            String lines = linebyline[i];
            int newindent = indetCount(lines);
            String code = lines.trim();
            String tagname = code.split(" ")[0];
            tagname = tagname.endsWith(".")? tagname.substring(0,tagname.length()-1):tagname;
            currentpath = partOfPath(currentpath, newindent - oldindet);
            Block b = new Block(tagname, newindent);
            setIDsAndClasses(b, tagname);
            if (lines.endsWith(".")) {
                b.setIntertxt(b.getIntertxt() + "\n");
                int lastindet = newindent;
                while (newindent <= indetCount(linebyline[++i])) {
                    b.setIntertxt(b.getIntertxt() + getIndent(indetCount(linebyline[i])) + linebyline[i].trim() + "\n");
                    newindent = indetCount(linebyline[i]);
                }
                b.setIntertxt(b.getIntertxt() + getIndent(lastindet));
            } else if (code.split(" ").length > 1) {
                b.setIntertxt(code.substring(code.split(" ")[0].length() + 1));
            }
            roothtml.addToBlock(b, currentpath);
            oldindet = newindent;
        }
        roothtml.updateInnertxt();
        return roothtml.toString();
    }

    private void setIDsAndClasses(Block b, String s) {
        String name = s.split("\\(")[0];
        String hash[] = name.split("#");
        String dot[] = name.split("\\.");
        if (hash[0].equals("") || dot[0].equals("")) {
            b.setTagename("div");
        } else if (hash.length > 1) {
            b.setTagename(hash[0]);
        } else if (dot.length > 1) {
            b.setTagename(dot[0]);
        }
        if (hash.length > 1) {
            String classes = "";
            String hasdot[] = hash[1].split("\\.");
            for (int i = 1; i < hasdot.length; i++) {
                String string = hasdot[i];
                classes += string + " ";
            }
            b.addAttribute("id", hasdot[0]);
            if (hasdot.length > 1) {
                b.addAttribute("class", classes.substring(0, classes.length() - 1));
            }
        } else if (dot.length > 1) {
            String classes = "";
            for (int i = 1; i < dot.length; i++) {
                String string = dot[i];
                classes += string + " ";
            }
            b.addAttribute("class", classes.substring(0, classes.length() - 1));
        }
        if (s.split("\\(").length > 1) {
            String attribs[] = s.substring(s.indexOf("(") + 1, s.indexOf(")")).split(",");
            for (int i = 0; i < attribs.length; i++) {
                String key[] = attribs[i].split("=");
                if (key.length>1) {
                    b.addAttribute(key[0], key[1].replaceAll("\"", "").replaceAll("'", ""));
                }else{
                    b.addAttribute(key[0], "true");
                }
            }
            b.setTagename(b.getTagename().replaceAll(s.substring(s.indexOf("(")+1 , s.indexOf(")")), ""));
            b.setTagename(b.getTagename().replaceAll("\\(", ""));
            b.setTagename(b.getTagename().replaceAll("\\)", ""));
        }
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

    private String partOfPath(String currentpath, int s) {
        if (s < 0) {
            String rootpath = currentpath.substring(0, currentpath.length() + (s * 2));
            int numb = Integer.parseInt(rootpath.substring(rootpath.length() - 1)) + 1;
            return rootpath.substring(0, rootpath.length() - 2) + "." + numb;
        } else if (s > 0) {
            return currentpath + ".0";
        } else {
            int numb = Integer.parseInt(currentpath.substring(currentpath.length() - 1)) + 1;
            return currentpath.substring(0, currentpath.length() - 2) + "." + numb;
        }
    }

    private String getIndent(int indent) {
        String s = "";
        for (int i = 0; i < indent; i++) {
            s += "\t";
        }
        return s;
    }
}
