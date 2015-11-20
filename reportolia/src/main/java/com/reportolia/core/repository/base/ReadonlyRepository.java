/**
 * 
 */
package com.reportolia.core.repository.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.Repository;

/**
 * The ReadonlyRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
public interface ReadonlyRepository<T, ID extends Serializable> extends Repository<T, ID> {

	  T findById(ID id);

	  List<T> findAll();
}
