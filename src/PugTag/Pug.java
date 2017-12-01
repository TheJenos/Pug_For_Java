/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PugTag;

import Pug.PugCompiler;
import Render.Alert;
import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Thanura
 */
public class Pug extends SimpleTagSupport {

    StringWriter sw = new StringWriter();
    
    public void doTag() throws JspException, IOException {
        getJspBody().invoke(sw);
        JspWriter out = getJspContext().getOut();
        try {
            out.println(new PugCompiler().renderFormtext(sw.getBuffer().toString()));
        } catch (Exception e) {
            out.println(new Alert(e.toString()).danger());
        }

    }
}
