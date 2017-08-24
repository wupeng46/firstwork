package com.lbs.commons;

/**
 * <p>Title: LEMIS</p>
 * <p>Description: LEMIS Core platform</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: lbs</p>
 * @author hanvey
 * @version 1.0
 */
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.XMLSerializer;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

public class DOMUtil{
    /**
     * create a new document
     * @return null if there is any exception
     */
    public static Document createNewDocument(){
        Document document = null;
        try{
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch(ParserConfigurationException parserconfigurationexception){
        }
        return document;
    }

    /**
     * load a document from file
     * @param f
     * @return null if there is any exception
     */
    public static Document loadDocumentFromStr(String str){
        Document document = null;
        try{
            InputStream is = new ByteArrayInputStream(str.getBytes());
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(is));
        } catch(Exception exception){
        }
        return document;
    }

    /**
     * load a document from file
     * @param f
     * @return null if there is any exception
     */
    public static Document loadDocumentFromStr(String str, String charsetName){
        Document document = null;
        try{
            InputStream is = new ByteArrayInputStream(str.getBytes(charsetName));
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(is));
        } catch(Exception exception){
        }
        return document;
    }

    /**
     * load a document from file
     * @param f
     * @return null if there is any exception
     */
    public static Document loadDocumentFromFile(File f){
        Document document = null;
        try{
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
        } catch(Exception exception){
        }
        return document;
    }

    /**
     * load a document from input stream
     * @param is
     * @return null if there is any exception
     */
    public static Document loadDocumentFromInputStream(InputStream is){
        Document document = null;
        try{
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        } catch(Exception exception){
        }
        return document;
    }

    /**
     * load a document from uri
     * @param is
     * @return null if there is any exception
     */
    public static Document loadDocumentFromUri(String uri){
        Document document = null;
        try{
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri);
        } catch(Exception exception){
        }
        return document;
    }

    /**
     * parse document
     * @param doc
     * @throws Exception
     */
    public static void parse(Document doc) throws Exception{
        DOMParser parser = new DOMParser();
        parser.setErrorHandler(new ParseErrorHandler());
        parser.setFeature("http://xml.org/sax/features/validation", true);

        StringReader sr = new StringReader(DOMUtil.domToString(doc));
        InputSource is = new InputSource(sr);
        parser.parse(is);
    }

    /**
     * append subNode to node
     * @param node
     * @param subNode
     */
    public static void appendChild(Node node, Node subNode){
        node.appendChild(subNode);
    }

    /**
     * if there has a single node with name of tagName in the whole subtree of node
     * @param node
     * @param tagName the subNode name
     * @return
     */
    public static boolean containsNode(Node node, String tagName){
        return getSingleNode(node, tagName) != null;
    }

    /**
     * get a single node with name of tagName in the whole subtree of node
     * @param node
     * @param tagName the subNode name
     * @return null if node is null or tagName is null or no such subNode exists
     */
    public static Node getSingleNode(Node node, String tagName){
        if(node == null || tagName == null)
            return null;

        Node singleNode = null;
        NodeList nodeList = null;

        if(node instanceof Document)
            nodeList = ((Document)node).getElementsByTagName(tagName);
        else
            nodeList = ((Element)node).getElementsByTagName(tagName);

        if(nodeList != null && nodeList.getLength() > 0)
            singleNode = nodeList.item(0);
        return singleNode;
    }

    /**
     * get a single element with the name of tagName in the whole subtree of node
     * @param node
     * @param tagName the subNode name
     * @return null if node is null or tagName is null or no such subNode exists
     */
    public static Element getSingleElement(Node node, String tagName){
        Element singleElement = null;
        if(containsNode(node, tagName))
            singleElement = (Element)(getSingleNode(node, tagName));
        return singleElement;
    }

    /**
     * get multi nodes with the name of tagName in the whole subtree of node
     * @param node
     * @param tagName the subNode name
     * @return null if node is null or tagName is null
     */
    public static NodeList getMultiNodes(Node node, String tagName){
        if(node == null || tagName == null)
            return null;

        NodeList nodeList = null;

        if(node instanceof Document)
            nodeList = ((Document)node).getElementsByTagName(tagName);
        else
            nodeList = ((Element)node).getElementsByTagName(tagName);

        return nodeList;
    }

    /**
     * get a subNode from node with name of tagName in the position of offset
     * @param node
     * @param tagName the subNode name
     * @param offset the position of this node in multi nodes with same name
     * @return null if node is null or tagName is null or offset<0 or offset>length of multi nodes
     */
    public static Node getNode(Node node, String tagName, int offset){
        if(node == null || tagName == null || offset < 0)
            return null;

        Node result = null;

        NodeList nodeList = getMultiNodes(node, tagName);
        if(offset < nodeList.getLength())
            result = nodeList.item(offset);
        return result;
    }

    /**
     * get a subElement from node with name of tagName in the position of offset
     * @param node
     * @param tagName the subNode name
     * @param offset the position of this element in multi elements with same name
     * @return null if node is null or tagName is null or offset<0 or offset>length of multi elements
     */
    public static Element getElement(Node node, String tagName, int offset){
        Node result = getNode(node, tagName, offset);
        if(result == null)
            return null;
        return(Element)result;
    }

    /**
     * get node value of this node
     * @param node
     * @return null if node is null or node instanceof Document or no node value exists
     */
    public static String getNodeValue(Node node){
        if(node == null || node instanceof Document)
            return null;

        NodeList nodelist = node.getChildNodes();
        if(nodelist.getLength() <= 0)
            return null;

        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0; i < nodelist.getLength(); i++){
            Node eachNode = nodelist.item(i);
            if(eachNode.getNodeType() == 3)
                stringBuffer.append(eachNode.getNodeValue());
        }
        return stringBuffer.toString();
    }

    /**
     * get a single node value with name of tagName in the whole subtree of node
     * @param node
     * @param tagName the subNode name
     * @return null if node is null or tagName is null or no such subNode exists or subNode instanceof Document or no subNode value exists
     */
    public static String getSingleNodeValue(Node node, String tagName){
        Node singleNode = getSingleNode(node, tagName);
        return getNodeValue(singleNode);
    }

    /**
     * get multi node value with name of tagName in the whole subtree of node
     * @param node
     * @param tagName the subNode name
     * @return String[0] if no proper value to return
     */
    public static String[] getMultiNodeValues(Node node, String tagName){
        String[] nodeValues;

        NodeList nodeList = getMultiNodes(node, tagName);
        if(nodeList == null)
            return new String[0];

        nodeValues = new String[nodeList.getLength()];

        for(int i = 0; i < nodeList.getLength(); i++){
            nodeValues[i] = getNodeValue(nodeList.item(i));
        }

        return nodeValues;
    }

    /**
     * get attribute value of element with name of attrName
     * @param element
     * @param attrName attribute name
     * @return null if element is null or attrName is null
     */
    public static String getAttributeValue(Element element, String attrName){
        if(element == null || attrName == null)
            return null;

        return element.getAttribute(attrName);
    }

    /**
     * get all attribute values of this element
     * @param element
     * @return
     */
    public static HashMap getAttributeValues(Element element){
        HashMap attributes = new HashMap();

        NamedNodeMap map = element.getAttributes();
        Attr attr;
        for(int i = 0; i < map.getLength(); i++){
            attr = (Attr)map.item(i);
            attributes.put(attr.getName(), attr.getValue());
        }

        return attributes;
    }

    /**
     * get attribute value with name of attrName of a single node with name of tagName in the whole subtree of node
     * @param node
     * @param tagName the subNode name
     * @param attrName attribute name
     * @return null if node is null or tagName is null or attrName is null
     */
    public static String getSingleAttributeValue(Node node, String tagName, String attrName){
        String attributeValue = null;
        Element element = getSingleElement(node, tagName);
        if(element != null)
            attributeValue = element.getAttribute(attrName);
        return attributeValue;
    }

    /**
     * get multi attribute value with name of attrName of a single node with name of tagName in the whole subtree of node
     * @param node
     * @param tagName the subNode name
     * @param attrName attribute name
     * @return null if node is null or tagName is null or attrName is null
     */
    public static String[] getMultiAttributeValue(Node node, String tagName, String attrName){
        String[] attributeValues;

        NodeList nodeList = getMultiNodes(node, tagName);
        if(nodeList == null)
            return new String[0];

        attributeValues = new String[nodeList.getLength()];

        for(int i = 0; i < nodeList.getLength(); i++){
            attributeValues[i] = getAttributeValue((Element)nodeList.item(i), attrName);
        }

        return attributeValues;
    }

    /**
     * create a element of this document
     * @param document document
     * @param elementName name of new element
     * @param elementValue value of new element
     * @return this element.<br>null if node is null or elementName is null
     */
    public static Element createElement(Document document, String elementName, String elementValue){
        if(document == null || elementName == null)
            return null;

        Element element = document.createElement(elementName);
        setNodeValue(element, elementValue);

        return element;
    }

    /**
     * create and append root element to document
     * @param document document
     * @param elementName name of new element
     * @param elementValue value of new element
     * @return this element.<br>null if node is null or elementName is null
     */
    public static Element createAndAppendRoot(Document document, String elementName, String elementValue){
        if(document == null || elementName == null)
            return null;

        Element element = document.createElement(elementName);
        document.appendChild(element);
        setNodeValue(element, elementValue);

        return element;
    }

    /**
     * create and append a sub element to node
     * @param node parent node
     * @param elementName name of new element
     * @param elementValue value of new element
     * @return this element.<br>null if node is null or elementName is null
     */
    public static Element createAndAppendElement(Node node, String elementName, String elementValue){
        if(node == null || elementName == null)
            return null;

        Document document = null;
        if(node instanceof Document)
            document = (Document)node;
        else
            document = node.getOwnerDocument();
        Element element = document.createElement(elementName);
        node.appendChild(element);
        setNodeValue(element, elementValue);

        return element;
    }

    /**
     * create and append multi sub element to node
     * @param node parent node
     * @param elementName name of new element
     * @param elementValues value of new element
     */
    public static void createAndAppendMultiElement(Node node, String elementName, String[] elementValues){
        if(node == null || elementName == null || elementValues == null || elementValues.length == 0 || node instanceof Document)
            return;

        Document document = node.getOwnerDocument();
        for(int i = 0; i < elementValues.length; i++){
            Element eachElement = document.createElement(elementName);
            node.appendChild(eachElement);
            setNodeValue(eachElement, elementValues[i]);
        }
    }

    /**
     * set the value of this node
     * @param node
     * @param nodeValue
     */
    public static void setNodeValue(Node node, String nodeValue){
        if(node == null || node instanceof Document)
            return;

        Document document = node.getOwnerDocument();
        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++){
            Node eachNode = nodeList.item(i);
            if(eachNode.getNodeType() == 3)
                node.removeChild(eachNode);
        }
        if(nodeValue != null)
            node.appendChild(document.createTextNode(nodeValue));
    }

    /**
     * set the value of a single sub node with name of tagName in the whole subtree of node
     * @param node
     * @param tagName the subNode name
     * @param nodeValue
     */
    public static void setSingleNodeValue(Node node, String tagName, String nodeValue){
        Node singleNode = getSingleNode(node, tagName);
        if(singleNode != null)
            setNodeValue(singleNode, nodeValue);
    }

    /**
     * set the value of attribute of this element
     * @param element
     * @param attributeName name of attribute
     * @param attributeValue value of attribute
     */
    public static void setAttribute(Element element, String attributeName, String attributeValue){
        if(element != null && attributeName != null)
            element.setAttribute(attributeName, attributeValue);
    }

    /**
     * set a set of value of attribute of this element
     * @param element
     * @param attributeName name of attribute
     * @param attributeValue value of attribute
     */
    public static void setAttributes(Element element, HashMap attributes){
        if(element == null || attributes == null)
            return;

        String key;
        for(Iterator e = attributes.keySet().iterator(); e.hasNext(); ){
            key = e.next().toString();
            element.setAttribute(key, attributes.get(key).toString());
        }
    }

    /**
     * set the value of attribute of this element in a single sub node with name of tagName in the whole subtree of node
     * @param node
     * @param tagName the subNode name
     * @param attributeName name of attribute
     * @param attributeValue value of attribute
     */
    public static void setSingleNodeAttribute(Node node, String tagName, String attributeName, String attributeValue){
        Element element = getSingleElement(node, tagName);
        setAttribute(element, attributeName, attributeValue);
    }

    /**
     * get xml string of this node
     * @param node
     * @return null if there is any exception
     */
    public static String domToString(Node node){
        StringWriter stringwriter = new StringWriter();
        XMLSerializer xmlserializer = new XMLSerializer(stringwriter, null);
        try{
            if(node instanceof Document)
                xmlserializer.serialize((Document)node);
            if(node instanceof Element)
                xmlserializer.serialize((Element)node);
        } catch(Exception exception){
            return null;
        }
        return stringwriter.toString();
    }

    static class ParseErrorHandler implements ErrorHandler{

        public void warning(SAXParseException e) throws org.xml.sax.SAXException{
        }

        public void error(SAXParseException e) throws org.xml.sax.SAXException{
            throw e;
        }

        public void fatalError(SAXParseException e) throws org.xml.sax.SAXException{
            throw e;
        }
    }


    /**
     * union attribute and childnode from targetNode to oriNode
     * oriNode and targetNode must be in same document
     * @param oriNode
     * @param targetNode
     * @param isApendChildNode true: append the childNOde that oriNode hasn't in targetNode;
     *                         false: don't append the childNOde that oriNode hasn't in targetNode;
     * @return oriNode
     */
    public static Node unionNodes(Node oriNode, Node targetNode, boolean isApendChildNode){
        HashMap oriAttrs = getAttributeValues((Element)oriNode);
        HashMap targetAttrs = getAttributeValues((Element)targetNode);
        String key;
        for(Iterator e = targetAttrs.keySet().iterator(); e.hasNext(); ){
            key = e.next().toString();
            oriAttrs.put(key, targetAttrs.get(key));
        }
        // set attributest
        setAttributes((Element)oriNode, oriAttrs);
        if(!isApendChildNode)
            return oriNode;

        NodeList nodelist = targetNode.getChildNodes();
        int j = nodelist.getLength();
        String name,type;
        for(int i = 0; i < nodelist.getLength(); i++){
            if(nodelist.item(i).getNodeType() == Element.TEXT_NODE) continue;
            removeChildNodes(oriNode,nodelist.item(i).getNodeName());
        }
        for(int i = 0; i < nodelist.getLength(); i++){
            if(nodelist.item(i).getNodeType() == Element.TEXT_NODE) continue;
           appendChild(oriNode,nodelist.item(i));
        }
        return oriNode;
    }
    /**
     * remove all childnodes named by tagName
     * @param node
     * @param targetNode
     * @return nothing
     */
    public static void removeChildNodes(Node node,String tagName){
        NodeList nodelist = node.getChildNodes();
        for(int i = 0; i < nodelist.getLength(); i++){
            if(nodelist.item(i).getNodeName().equals(tagName))
                node.removeChild(nodelist.item(i));
       }
    }
}
