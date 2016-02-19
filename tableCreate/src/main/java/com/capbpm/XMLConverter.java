package com.capbpm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class XMLConverter {

	private Marshaller marshaller;// = new Jaxb2Marshaller();
	private Unmarshaller unmarshaller;// = new Jaxb2Marshaller();

	public XMLConverter(){
	    Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
	    jaxb2Marshaller.setSupportDtd(true);
	    marshaller = jaxb2Marshaller;
	    unmarshaller = jaxb2Marshaller;
	}
	
	public Marshaller getMarshaller() {
		System.out.println("getMarshaller marshaller="+marshaller);
		return marshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public Unmarshaller getUnmarshaller() {
		System.out.println("getMarshaller unmarshaller="+marshaller);
		return unmarshaller;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	public void convertFromObjectToXML(Object object, String filepath)
		throws IOException {

		FileOutputStream os = null;
		try {
			os = new FileOutputStream(filepath);
			getMarshaller().marshal(object, new StreamResult(os));
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	public Object convertFromXMLToObject(String xmlfile) throws IOException {

		FileInputStream is = null;
		try {
			is = new FileInputStream(xmlfile);
			return getUnmarshaller().unmarshal(new StreamSource(is));
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
	
	public Object convertFromXMLAsStringToObject(String s) throws IOException {
	     return getUnmarshaller().unmarshal(new StreamSource(new StringReader(s)));
	}

}
