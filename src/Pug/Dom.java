/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pug;

import Utils.Block;
import java.util.ArrayList;

/**
 *
 * @author TheJenos
 */
public class Dom {

    /**
     *
     */
    public static class Domlines {

        private String domtxt;
        private int line;
        private int indent;
        private Block lineblock;

        /**
         *
         * @param domtxt
         * @param line
         * @param indent
         * @param lineblock
         */
        public Domlines(String domtxt, int line, int indent, Block lineblock) {
            this.domtxt = domtxt;
            this.line = line;
            this.indent = indent;
            this.lineblock = lineblock;
        }

        /**
         *
         * @return
         */
        public Block getLineblock() {
            return lineblock;
        }

        /**
         *
         * @param lineblock
         */
        public void setLineblock(Block lineblock) {
            this.lineblock = lineblock;
        }

        /**
         *
         * @return
         */
        public String getDomtxt() {
            return domtxt;
        }

        /**
         *
         * @param domtxt
         */
        public void setDomtxt(String domtxt) {
            this.domtxt = domtxt;
        }

        /**
         *
         * @return
         */
        public int getLine() {
            return line;
        }

        /**
         *
         * @param line
         */
        public void setLine(int line) {
            this.line = line;
        }

        /**
         *
         * @return
         */
        public int getIndent() {
            return indent;
        }

        /**
         *
         * @param indent
         */
        public void setIndent(int indent) {
            this.indent = indent;
        }

    }
    private ArrayList<Domlines> dom = new ArrayList<Domlines>();

    /**
     *
     * @param CurrentLine
     * @param i
     * @return
     */
    public Domlines getFromIndent(int CurrentLine, int i) {
        ArrayList<Domlines> tmp = new ArrayList<Domlines>();
        Domlines retdom = null;
        int CurrentDistance = Integer.MAX_VALUE;
        for (Domlines domlines : dom) {
            if (indetCount(domlines.getDomtxt()) == i) {
                tmp.add(domlines);
            }
        }
        for (Domlines domlines : tmp) {
            if (CurrentDistance > (CurrentLine - domlines.getLine())) {
                CurrentDistance = CurrentLine - domlines.getLine();
                retdom = domlines;
            }
        }
        return retdom;
    }

    /**
     *
     * @param i
     * @return
     */
    public Domlines getFromLine(int i) {
        for (Domlines domlines : dom) {
            if (domlines.getLine() == i) {
                return domlines;
            }
        }
        return null;
    }

    /**
     *
     * @param dm
     */
    public void addToDom(Domlines dm) {
        dom.add(dm);
    }

    /**
     *
     * @param s
     * @return
     */
    public static int indetCount(String s) {
        if (s.startsWith("    ")) {
            int end = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != ' ') {
                    end = i;
                    break;
                }
            }
            return s.substring(0, end).length()/4;
        } else {
            int end = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != '\t') {
                    end = i;
                    break;
                }
            }
            return s.substring(0, end).length();
        }
    }

    /**
     *
     * @return
     */
    public Block getBlock() {
        return dom.get(0).getLineblock();
    }
    
    @Override
    public String toString() {
        return dom.get(0).getLineblock().toString();
    }

}
