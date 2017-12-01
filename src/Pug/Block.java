/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 *
 * @author Thanura
 */
public class Block {

    private String intertxt = "";
    private String attribtxt = "";
    private String tagename = "";
    private String path = "0";
    private int indent = 0;
    private ArrayList<Block> innerblocks = new ArrayList<Block>();
    private HashMap<String, String> attriblist = new HashMap<String, String>();

    public Block(String tagname,int indent) {
        this.tagename = tagname;
        this.indent = indent;
    }

    public void setTagename(String tagename) {
        this.tagename = tagename;
    }

    public String getTagename() {
        return tagename;
    }
    
    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void addBlock(Block b) {
        innerblocks.add(b);
        updateInnertxt();
    }

    public Block getBlock(int bid){
        return innerblocks.get(bid);
    }
    
    
    private int[] stringPathToInt(String s) {
        s = s.substring(0, s.length()-2);
        String[] numbs = s.split("\\.");
        int returnval[] = new int[numbs.length];
        for (int i = 0; i < numbs.length; i++) {
            String j = numbs[i];
            returnval[i] = Integer.parseInt(j);
        }
        return returnval;
    }
    
    public Block getBlockFromPath(String pathtxt){
        int[] path = stringPathToInt(pathtxt);
        Block selectedblock = this;
        for (int i = 1; i < path.length; i++) {
            int j = path[i];
            selectedblock = selectedblock.getBlock(j);
        }
        return selectedblock;
    }
    
    public void addToBlock(Block b,String path){
        getBlockFromPath(path).addBlock(b);
    }
    
    public void addAttribute(String attributename, String value) {
        attriblist.put(attributename, value);
        updateAttribtxtt();
    }

    public void removeAttribute(String attributename) {
        attriblist.remove(attributename);
        updateAttribtxtt();
    }

    public void removeBlock(Block b) {
        innerblocks.remove(b);
        updateInnertxt();
    }

    public void removeBlock(int b) {
        innerblocks.remove(b);
        updateInnertxt();
    }

    public void updateInnertxt() {
        intertxt = "";
        for (Iterator<Block> iterator = innerblocks.iterator(); iterator.hasNext();) {
            Block next = iterator.next();
            intertxt += next.toString();
        }
    }

    public void updateAttribtxtt() {
        attribtxt = "";
        for (Map.Entry<String, String> entry : attriblist.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            attribtxt += " " + key + "=\"" + value + "\"";
        }
    }

    public boolean isAnEmptyTag() {
        return intertxt.equals("")||intertxt.isEmpty();
    }

    public void setIntertxt(String intertxt) {
        this.intertxt = intertxt;
    }

    public String getIntertxt() {
        return intertxt;
    }

    private String getIndent(){
        String s = "";
        for (int i = 0; i < indent; i++) {
            s+="\t";
        }
        return s;
    }
    
    @Override
    public String toString() {
        if(innerblocks.size()>0){
            updateInnertxt();
            updateAttribtxtt();
        }
        if(tagename.equals("|")){
            return getIndent()+intertxt+"\n";
        }
        if (isAnEmptyTag()) {
            return getIndent()+"<" + tagename + attribtxt + "/>\n";
        } else {
            return getIndent()+"<" + tagename + attribtxt + ">"+(innerblocks.size()<1?"":"\n") + intertxt +(innerblocks.size()<1?"":getIndent())+ "</" + tagename + ">\n";
        }
    }

}
