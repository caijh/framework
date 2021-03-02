package com.github.caijh.framework.util;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import com.github.caijh.framework.util.exception.ObjectToXmlException;
import org.springframework.lang.Nullable;

public class XmlUtils {

    private XmlUtils() {}

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
        return toXml(object, null);
    }

}
