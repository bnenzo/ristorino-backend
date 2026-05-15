package ar.edu.ubp.das.ristorino_backend.beans;

import jakarta.xml.bind.annotation.XmlValue;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SoapStringResponse")
@XmlAccessorType(XmlAccessType.FIELD)

public class SoapStringResponseBean {
  @XmlValue
  private String response;

  public String getResponse() {
    return response;
  }

  public void setResponse(String value) {
    this.response = value;
  }
}
