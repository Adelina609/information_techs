import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import user.Helper;

public class XTest {

    private static final String classElement = "com.sun.org.apache.xerces.internal.dom.DeferredElementImpl";
    private static final String classAttribute = "com.sun.org.apache.xerces.internal.dom.DeferredAttrImpl";
    private static final String XML_URI   = "https://www.w3schools.com/xml/books.xml";

    private static File file = new File("C:\\Users\\user\\Downloads\\Telegram Desktop\\xpath\\src\\books.xml");
    private static FileInputStream fis;

    private static String myXpath = "";

    static {
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
//    private static final String EXPRESSIONTEST3 = "//book[@category='web']/title";
//    private static final String EXPRESSIONTEST34 = "/bookstore/book[title[@lang='en']]/author";
//    private static final String EXPRESSIONTEST1 = "/bookstore/book/@category";
//    private static final String EXPRESSIONTEST = "/bookstore/book[@category='web']";


    public static void main(String[] args){
        System.out.println("Enter your xpath: ");
        Scanner sc = new Scanner(System.in);
        myXpath = sc.nextLine();
        Helper helper = new Helper(myXpath);

        if(helper.isBracketsValid() &&
                        helper.isSlashValid() &&
                                helper.isQuotesValid()) {
            try {
                XTest xPathTest = new XTest();
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        else if(!helper.isBracketsValid()) {
            System.out.println("Unmatched brackets");
        }
        else if(!helper.isQuotesValid()){
            System.out.println("Unmatched quotes");
        }
        else if(!helper.isSlashValid()){
            System.out.println("XPath shouldn't end with slash");
        }
        sc.close();
    }


    public XTest() throws Exception{

        // Initialize some stuff
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new Exception("Can't crate DocumentBuilder");
        }

        // Load XML document from WWW
        Document doc;
        try {
            doc = builder.parse(fis);
        } catch (IOException ex) {
            throw new Exception("Can't get XML by URL " + XML_URI);
        } catch (SAXException ex) {
            throw new Exception("Can't read downloaded XML.");
        }

        // Initialize xPath expression
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr;
        try {
            expr = xpath.compile(myXpath);
        } catch (XPathExpressionException ex) {
            throw new Exception("Can't parse xPath expression " + myXpath);
        }

        // Use xPath on our document
        NodeList nodeList;
        try {
            nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex) {
            throw new Exception("Can't evaluate expression");
        }

        if(nodeList.getLength() == 0){
            System.out.println("No match");
        } else {

            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getClass().getName().equals(classElement)) {
                    System.out.println(nodeList.item(i).getTextContent());
                }
                else if (nodeList.item(i).getClass().getName().equals(classAttribute)) {
                    System.out.println(nodeList.item(i));
                }
            }
        }
    }
}
