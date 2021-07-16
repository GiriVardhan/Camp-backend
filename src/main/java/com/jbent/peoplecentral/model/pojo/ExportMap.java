package com.jbent.peoplecentral.model.pojo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@SuppressWarnings("unchecked")
@XmlRootElement(name="entities")
public class ExportMap extends HashMap implements Serializable {
  private static final long serialVersionUID = 1L;

  private List<Entity> entity;

  public ExportMap() {
    // empty constructor required for JAXB
  }

  public ExportMap(List entity) {
    this.entity = entity;
    //System.out.println("test");
  }

  @XmlElement(name="entity")
  public List<Entity> getEntity() {
    return entity;
  }

  public void setEntity(List entity) {
    this.entity = entity;
  }

 
}
