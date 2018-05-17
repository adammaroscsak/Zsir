/* 
 * Copyright (C) 2018 Adam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ScoreTable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Class for manipulating the stored score table.
 *
 * @author adam
 */
public class ScoreTableData {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ScoreTableDialog.class);

    /**
     * The source xml file of the score table.
     */
    private static File XMLFile;
    /**
     * The directory of the xml file.
     */
    private static final Path mainDir;
    /**
     * Indicates whether the xml file is valid.
     */
    private static boolean valid = true;

    static {
        String homeDir = System.getProperty("user.home");
        mainDir = Paths.get(homeDir, ".Zsir");
        initEnvironment();
    }
        
    /**
     * Initializes the xml file.
     */
    private static void initFile() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("scoretable");
            rootElement.appendChild(doc.createElement("persons"));
            doc.appendChild(rootElement);
            write(doc);
        } catch (ParserConfigurationException ex) {
            logger.error("An error occured during the xml file initialization", ex);
        }

    }

    /**
     * Adds the specified new Person element to the xml file.
     *
     * @param newPerson the person to be added to the xml file
     */
    public static void addPerson(Person newPerson) {
        try {
            JAXBContext context = JAXBContext.newInstance(Persons.class);        
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            docBuilder.setEntityResolver(new EntityResolverImpl());
          
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            Persons persons = (Persons) unmarshaller.unmarshal(XMLFile);
            persons.addPerson(newPerson);

            m.marshal(persons, doc);

            write(doc);
        } catch (ParserConfigurationException | JAXBException ex) {
            logger.error("An error occured during the adding a person to the xml file.", ex);
            
        }
    }

    /**
     * Writes out the specified document into the xml file.
     *
     * @param doc the specified document
     */
    private static void write(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            Source source = new DOMSource(doc);
            StreamResult result = new StreamResult(XMLFile);
            transformer.setOutputProperty(
                    OutputKeys.DOCTYPE_SYSTEM, "scoretable.dtd");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            logger.error("An error occured during the writing the xml file.", ex.getMessage());
        }
    }

    /**
     * Creates the xml file and inits it.
     *
     * @param file the file to be created
     */
    private static void createXml(Path file) {
        try {
            Path path = Files.createFile(file);
            XMLFile = path.toFile();
            initFile();
        } catch (IOException ex) {
            logger.error("An error occured during the creating the xml file.", ex.getMessage());
        }
    }

    /**
     * Validates the xml file.
     */
    private static void validate() {
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void error(SAXParseException exception) {
                    setValid(false);
                }

                @Override
                public void fatalError(SAXParseException exception) {
                    setValid(false);
                }

                @Override
                public void warning(SAXParseException exception) {
                    setValid(false);
                }
            });
            Document doc = builder.parse(XMLFile);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            logger.error("An error occured during the validating the xml file.", ex);
        }
    }

    /**
     * Indicates whether the xml file is valid.
     *
     * @return true if the xml file is valid
     */
    private static boolean isValid() {
        return valid;
    }

    /**
     * Sets the value of the valid property.
     *
     * @param valid the specified value
     */
    private static void setValid(boolean valid) {
        ScoreTableData.valid = valid;
    }

    /**
     * Gets the xml file.
     *
     * @return the xml file
     */
    public static File getFile() {
        return XMLFile;
    }

    /**
     * Initializes the environment of the score table.
     */
    private static void initEnvironment() {
        Path dtdPath = Paths.get(mainDir.toString(), "scoretable.dtd");
        Path xmlPath = Paths.get(mainDir.toString(), "scoretable.xml");

        if (!Files.exists(mainDir)) {
            try {
                Files.createDirectory(mainDir);
            } catch (IOException ex) {
                logger.error("An error occured during the creating the main directory of the game.", ex.getLocalizedMessage());
            }
        }

        if (!Files.exists(dtdPath)) {
            try {
                Files.copy(ScoreTableData.class.getResourceAsStream("/files/scoretable.dtd"), dtdPath);
            } catch (IOException ex) {
                logger.error(
                        "An error occured during the copying the dtd file into the main directory of the game.",
                        ex.getLocalizedMessage());
            }
        }

        if (!Files.exists(xmlPath)) {
            createXml(xmlPath);
        } else {
            XMLFile = xmlPath.toFile();
            validate();
            if (!isValid()) {
                initFile();
            }
        }
    }

    private static class EntityResolverImpl implements EntityResolver {

        public EntityResolverImpl() {
        }

        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            return new InputSource(Paths.get(mainDir.toString(), "scoretable.dtd").toString());
        }
    }

}

