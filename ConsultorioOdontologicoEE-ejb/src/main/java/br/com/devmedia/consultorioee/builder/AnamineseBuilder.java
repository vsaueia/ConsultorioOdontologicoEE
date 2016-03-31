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
package br.com.devmedia.consultorioee.builder;

import br.com.devmedia.consultorioee.model.Anaminese;
import br.com.devmedia.consultorioee.model.Cliente;

/**
 *
 * @author vsaueia
 */
public class AnamineseBuilder {
    
    private final Anaminese anaminese;
    
    public AnamineseBuilder() {
        anaminese = new Anaminese();
    }
    
    public Anaminese create() {
        return anaminese;
    }
    
    public AnamineseBuilder comAlergia(boolean alergia) {
        anaminese.setAlergia(alergia);
        return this;
    }
    
    public AnamineseBuilder comDescricaoAlergia(String descricaoAlergia) {
        anaminese.setDescricaoAlergia(descricaoAlergia);
        return this;
    }
    
    public AnamineseBuilder comCliente(Cliente cliente) {
        anaminese.setCliente(cliente);
        return this;
    }

}
