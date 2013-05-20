/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlserialization;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import xmlserialization.beans.Product;
import xmlserialization.beans.Vendor;

/**
 *
 * @author Dimityr
 */
public class XMLSerialization {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Product boksoviRykavici = new Product("rykavici", "golemi", 422, 3.2);
        Product boksovaKrusha = new Product("krusha", "tejka", 23, 333.3);

        List<Product> products = new ArrayList<>();
        products.add(boksoviRykavici);
        products.add(boksovaKrusha);
        Vendor v1 = new Vendor("Kolio Shampiona", products, "mega golemiq");

//        XMLSerialization.persist(v1);

        XMLSerialization.serialize(v1);

    }

    public static void persist(Object object) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("XMLSerializationPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public static void serialize(Object object) {
        final JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(object.getClass());

            StringWriter writer = new StringWriter();

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(object, writer);
            System.out.println(writer.toString());
        } catch (JAXBException ex) {
            Logger.getLogger(XMLSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
