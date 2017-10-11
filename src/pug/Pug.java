/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pug;
import pug.Compiler;
/**
 *
 * @author Thanura
 */
public class Pug {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String s = "html\n"
                + "  head\n"
                + "    title My Site - #{title}\n"
                + "    block scripts\n"
                + "      script(src='/jquery.js')\n"
                + "  body\n"
                + "    block content\n"
                + "    block foot\n"
                + "      #footer\n"
                + "        p some footer content";
        System.out.println(new Compiler().renderFormtext(s));

    }

}
