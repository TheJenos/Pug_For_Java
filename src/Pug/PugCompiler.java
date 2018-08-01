/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pug;

import Utils.Block;
import static Pug.Dom.indetCount;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Thanura
 */
public class PugCompiler {

    ArrayList var = new ArrayList();
    
    /**
     *
     * @param text
     * @return
     */
    public String getTextFromLine(String text) {
        text = text.trim();
        Matcher mss = Pattern.compile("([\\#.\\w]+)\\(([^)]+)\\)").matcher(text);
        if (mss.find()) {
            return text.replace(mss.group(), "").trim();
        } else {
            return text.replace(text.split(" ")[0], "").trim();
        }
    }

    /**
     *
     * @param text
     * @return
     */
    public String getTagFromLine(String text) {
        text = text.trim();
        Matcher mss = Pattern.compile("([\\#.\\w]+)\\(([^)]+)\\)").matcher(text);
        if (mss.find()) {
            return mss.group();
        } else {
            return text.split(" ")[0];
        }
    }

    /**
     *
     * @param text
     * @return
     */
    public Block renderDom(String text) {
        text = text.trim();
        String lines[] = text.split("\n");
        Dom dom = new Dom();
        dom.addToDom(new Dom.Domlines(lines[0], 0, 0, new Block(getTagFromLine(lines[0]), 0)));
        for (int i = 1; i < lines.length; i++) {
            String CurrentLine = lines[i];
            if(CurrentLine.trim().length()<1) continue;
            int CurrentIndentCount = indetCount(CurrentLine);
            try {
                Dom.Domlines superdom = dom.getFromIndent(i, CurrentIndentCount - 1);
                Block b = new Block(getTagFromLine(CurrentLine), CurrentIndentCount);
                b.setIntertxt(getTextFromLine(CurrentLine));
                setIDsAndClasses(b, getTagFromLine(CurrentLine));
                if (superdom == null) {
                    throw new Exception("Root Block Is Missing");
                }
                superdom.getLineblock().addBlock(b);
                dom.addToDom(new Dom.Domlines(CurrentLine, i, CurrentIndentCount, b));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage() + " --> Line[" + i + "],Indent[" + CurrentIndentCount + "] => " + CurrentLine);
            }
        }
        return dom.getBlock();
    }

    private void setIDsAndClasses(Block b, String s) throws Exception {
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
                if (key.length > 1) {
                    b.addAttribute(key[0], key[1].replaceAll("\"", "").replaceAll("'", ""));
                } else {
                    b.addAttribute(key[0], "true");
                }
            }
            b.setTagename(b.getTagename().replaceAll(s.substring(s.indexOf("(") + 1, s.indexOf(")")), ""));
            b.setTagename(b.getTagename().replaceAll("\\(", ""));
            b.setTagename(b.getTagename().replaceAll("\\)", ""));
        }
    }

}
