/**
 * 
 */
package com.reportolia.core.model.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.reportolia.core.utils.CoreUtils;

/**
 * The BaseEntity class
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
@MappedSuperclass
public abstract class BaseEntity {
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable=false)
    private Date createDate;

    // TODO: Test to make sure updateDate is properly handled
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Version
    private Date updateDate;

    @PrePersist
    protected void onCreate() {
    	this.updateDate = this.createDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
    	this.updateDate = new Date();
    }

	/**
	 *
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 *
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 *
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate != null ? createDate : new Date();
	}

	/**
	 *
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 *
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public boolean isNewBean() {
		return CoreUtils.isKeyNull(this.getId());
	}
}	