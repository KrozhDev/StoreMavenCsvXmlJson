import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.metadata.IIOMetadataNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class XmlSettings {

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(new File("shop.xml"));

    Node root = doc.getDocumentElement();

    NodeList nodeList = root.getChildNodes();
    private IIOMetadataNode node;

    String isLoadFile;
    String loadingFile;
    String loadingFileFormat;



    public XmlSettings() throws ParserConfigurationException, IOException, SAXException {
        System.out.println("Корневой элемент " + root.getNodeName());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
//            System.out.println("Текущий элемент " + node.getNodeName() + "Тип элемента " + node.getNodeType());

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println("Текущий элемент " + node.getNodeName() + " Тип элемента " + node.getNodeType());
                System.out.println("Дети элемента " + node.getChildNodes());
                System.out.println("Контент элемента" + node.getTextContent());
                String[] content = node.getTextContent().split("\n");
                isLoadFile = content[1].trim();
                loadingFile = content[2].trim();
                loadingFileFormat = content[3].trim();




                System.out.println("isLoadFile " + isLoadFile);
                System.out.println("fileForInput " + loadingFile);
                System.out.println("fileFormat " + loadingFileFormat);
            }
        }
    }

    public boolean isLoadBasketData() {

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println("Текущий элемент " + node.getNodeName() + "Тип элемента " + node.getNodeType());
        }
        return true;


    }

}
