/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.controller;

import br.com.devmedia.consultorioee.util.JsfUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author vsaueia
 */
class BasicController {
    
    protected Set<ConstraintViolation<Serializable>> getViolations(Serializable entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Serializable>> violations = validator.validate(entity);
        return violations;
    }
    
    protected boolean existsViolations(Serializable entity) {
        Set<ConstraintViolation<Serializable>> violations = getViolations(entity);
        if(violations.isEmpty()) {
            return false;
        }
        for (ConstraintViolation<Serializable> violation : violations) {
            JsfUtils.addError(violation.getMessage());
        }
        return true;
    }
}
