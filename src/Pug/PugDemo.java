/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pug;
import Pug.PugCompiler;
/**
 *
 * @author Thanura
 */
public class PugDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String s = "html\n"
                + "  head\n"
                + "    title My Site - #{title}\n"
                + "    script(src='/jquery.js',type='text/javascript')\n"
                + "  body\n"
                + "    .col.lol\n"
                + "      | sdfsdf\n"
                + "      br\n"
                + "      | sdfsdfsdasdasd\n"
                + "    #footer\n"
                + "        p some footer content\n";
        //System.out.println(s);
        System.out.println(new PugCompiler().renderFormtext(s));

    }

}
