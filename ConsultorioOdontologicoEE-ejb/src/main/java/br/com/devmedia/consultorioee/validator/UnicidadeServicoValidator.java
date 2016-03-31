/*
 * Copyright (C) 2016 vsaueia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.devmedia.consultorioee.validator;

import br.com.devmedia.consultorioee.model.Servico;
import br.com.devmedia.consultorioee.service.ServicosService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vsaueia
 */
public class UnicidadeServicoValidator implements ConstraintValidator<UnicidadeServico, Servico> {

    private final ServicosService servicosService = lookupServicosServiceBean();

    @Override
    public void initialize(UnicidadeServico constraintAnnotation) {
        System.out.println("[UnicidadeServicoValidator] " + constraintAnnotation.message());
    }

    @Override
    public boolean isValid(Servico servico, ConstraintValidatorContext context) {
        if (servico == null) {
            return true;
        }

        boolean isValid = true;
        Servico findResult = servicosService.findByDescricaoExata(servico.getDescricao());
        if (findResult != null) {
            return findResult.getId().equals(servico.getId());
        }
        return isValid;
    }

    private ServicosService lookupServicosServiceBean() {
        try {
            Context c = new InitialContext();
            return (ServicosService) c.lookup("java:global/ConsultorioOdontologicoEE/ConsultorioOdontologicoEE-ejb/ServicosService!br.com.devmedia.consultorioee.service.ServicosService");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
}
