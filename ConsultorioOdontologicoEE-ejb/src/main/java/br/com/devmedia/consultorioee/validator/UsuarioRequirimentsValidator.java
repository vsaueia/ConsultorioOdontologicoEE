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

import br.com.devmedia.consultorioee.model.Usuario;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author vsaueia
 */
public class UsuarioRequirimentsValidator implements ConstraintValidator<UsuarioRequirements, Usuario>{

    @Override
    public void initialize(UsuarioRequirements constraintAnnotation) {
        System.out.println("[UsuarioValidator] "+constraintAnnotation.message());
    }

    @Override
    public boolean isValid(Usuario usuario, ConstraintValidatorContext context) {
        if(usuario == null) {
            return true;
        }
        return (usuario.isAdministrador() && !usuario.isDentista())
                || (!usuario.isAdministrador() && usuario.isDentista());
    }
    
    
}
