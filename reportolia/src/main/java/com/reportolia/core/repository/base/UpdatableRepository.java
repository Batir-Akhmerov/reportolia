/**
 * 
 */
package com.reportolia.core.repository.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The UpdatableRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
public interface UpdatableRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	  T findById(ID id);

	  List<T> findAll();
}
