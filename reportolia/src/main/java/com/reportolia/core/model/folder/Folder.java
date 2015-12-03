package com.reportolia.core.model.folder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import com.reportolia.core.model.base.BaseEntity;

/**
 * 
 * The DataType class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Entity
@Table(name="r3p_folders", uniqueConstraints = { @UniqueConstraint(columnNames = {"parent_folder_id", "name"}) })
public class Folder extends BaseEntity {

	@ManyToOne
    @JoinColumn(name="parent_folder_id")
    private Folder parentFolder;
   
    @NotEmpty
    @Column(name = "name", nullable = false, length = 128)
    private String name;
    
    @NotEmpty
    @Column(name = "expanded_name", nullable = false, length = 500)
    private String expandedName;
  

    @Column(name = "description", nullable = true, length = 500)
    private String description;
    
    @Column(name = "is_system", columnDefinition = "boolean default false")
    private Boolean system;
    
    /**
     * Used for user specific folder
     */
    @Column(name = "user_id")
    private Long userId;
    
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


	public Folder getParentFolder() {
		return this.parentFolder;
	}


	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}


	public String getExpandedName() {
		return this.expandedName;
	}


	public void setExpandedName(String expandedName) {
		this.expandedName = expandedName;
	}


	public Boolean isSystem() {
		return this.system;
	}


	public void setSystem(Boolean system) {
		this.system = system != null ? system : false;
	}


	public Long getUserId() {
		return this.userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setDescription(String description) {
		this.description = description;
	}
    
}
