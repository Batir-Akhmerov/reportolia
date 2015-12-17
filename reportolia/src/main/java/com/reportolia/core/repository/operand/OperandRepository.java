package com.reportolia.core.repository.operand;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.reportolia.core.model.operand.ContentType;
import com.reportolia.core.model.operand.Operand;
import com.reportolia.core.model.operand.OperandOwnerType;
import com.reportolia.core.repository.base.UpdatableRepository;

/**
 * 
 * The OperandColumnPathRepository class
 *
 * @author Batir Akhmerov
 * Created on Dec 1, 2015
 */
public interface OperandRepository extends UpdatableRepository<Operand, Long> {
    
    List<Operand> findByOwnerIdAndOwnerTypeAndContentType(Long ownerId, OperandOwnerType ownerType, ContentType contentType, Sort sort);
     
}
