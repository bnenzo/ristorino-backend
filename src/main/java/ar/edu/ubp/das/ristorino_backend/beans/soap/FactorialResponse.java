package ar.edu.ubp.das.ristorino_backend.beans.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;

import java.math.BigInteger;
import java.util.List;

@XmlRootElement(name = "FactorialResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class FactorialResponse {

  @XmlValue
  private BigInteger result;

  public BigInteger getResult() {
    return result;
  }

  public void setResult(BigInteger result) {
    this.result = result;
  }
}