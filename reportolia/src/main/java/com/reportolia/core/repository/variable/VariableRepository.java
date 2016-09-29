package com.reportolia.core.repository.variable;

import java.util.List;

import com.reportolia.core.model.variable.Variable;
import com.reportolia.core.model.variable.VariableOwnerType;
import com.reportolia.core.repository.base.UpdatableRepository;

/**
 * 
 * The VariableRepository class
 *
 * @author Batir Akhmerov
 * Created on Sep 2, 2016
 */
public interface VariableRepository extends UpdatableRepository<Variable, Long> {
 
	public List<Variable> findByOwnerTypeAndOwnerId(VariableOwnerType ownerType, Long ownerId);
    
}
