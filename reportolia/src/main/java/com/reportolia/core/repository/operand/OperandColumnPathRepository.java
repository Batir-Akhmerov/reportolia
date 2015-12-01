package com.reportolia.core.repository.operand;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.reportolia.core.model.operand.Operand;
import com.reportolia.core.model.operand.OperandColumnPath;
import com.reportolia.core.repository.base.UpdatableRepository;

/**
 * 
 * The OperandColumnPathRepository class
 *
 * @author Batir Akhmerov
 * Created on Dec 1, 2015
 */
public interface OperandColumnPathRepository extends UpdatableRepository<OperandColumnPath, Long> {
    
    List<OperandColumnPath> findByOperand(Operand operand, Sort sort);
     
}
