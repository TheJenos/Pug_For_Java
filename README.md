# Pug Template Engine For Java
Still I'am on Stage 1,So If You Found Any Bug Just Inform Me
* First Import Java Files.
```java
import pug.Compiler;
```
* Then add Your Pug String To renderFormtext() Method.
```java
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
```
* renderFormtext() retrun the HTML text.

## Stage 1
  - Just Contain Object To Correct Order With Indents  
