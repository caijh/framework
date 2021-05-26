package com.github.caijh.framework.util;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import com.github.caijh.framework.util.exception.ObjectToXmlException;
import com.github.caijh.framework.util.exception.XmlToObjectException;
import org.springframework.lang.Nullable;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class XmlUtils {

    private XmlUtils() {

    }

    /**
     * parse object to xml string.
     *
     * @param object the object to xml string
     * @param name   root xml name
     * @param <T>    class type
     * @return xml string of object
     */
    @SuppressWarnings("unchecked")
    public static <T> String toXml(T object, @Nullable String name) {
        JAXBContext jaxbContext;
        try {
            Class<T> clazz = ((Class<T>) object.getClass());
            jaxbContext = JAXBContext.newInstance(clazz);

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter writer = new StringWriter();
            boolean xmlRootElementPresent = clazz.isAnnotationPresent(XmlRootElement.class);
            if (xmlRootElementPresent) {
                marshaller.marshal(object, writer);
            } else {
                JAXBElement<T> jaxbElement = new JAXBElement<>(
                        new QName("", name != null ? name : clazz.getSimpleName().toLowerCase()), clazz, object);
                marshaller.marshal(jaxbElement, writer);
            }

            return writer.toString();
        } catch (JAXBException e) {
            throw new ObjectToXmlException(e);
        }
    }

    public static <T> String toXml(T object) {
        return XmlUtils.toXml(object, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromXml(String xml, Class<T> clazz) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            boolean xmlRootElementPresent = clazz.isAnnotationPresent(XmlRootElement.class);
            if (xmlRootElementPresent) {
                return (T) unmarshaller.unmarshal(new StringReader(xml));
            }
            StringReader reader = new StringReader(xml);
            SAXParserFactory sax = SAXParserFactory.newInstance();
            sax.setNamespaceAware(false);//设置忽略名称空間
            XMLReader xmlReader = sax.newSAXParser().getXMLReader();
            Source source = new SAXSource(xmlReader, new InputSource(reader));
            return unmarshaller.unmarshal(source, clazz).getValue();
        } catch (Exception e) {
            throw new XmlToObjectException(e);
        }
    }

}
