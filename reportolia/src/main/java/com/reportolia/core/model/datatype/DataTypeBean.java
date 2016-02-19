package com.reportolia.core.model.datatype;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.reportolia.core.model.base.BaseEntity;

/**
 * 
 * The DataType class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Entity
@Table(name="r3p_data_types", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
public class DataTypeBean extends BaseEntity {

   
    @Column(name = "name", nullable = false, length = 128)
    private String name;
  

    @Column(name = "description", nullable = true, length = 500)
    private String description;
    
    //@OneToMany(targetEntity=DataTypeFormat.class, mappedBy="dataType", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    //private List<DataTypeFormat> dataTypeFormats;


    /**
     * System Table Name
     * @return
     */
    public String getName() {
        return this.name;
    }
    
    
    /**
     * Table Description
     * @return
     */
    public String getDescription() {
        return this.description;
    }
    
}
