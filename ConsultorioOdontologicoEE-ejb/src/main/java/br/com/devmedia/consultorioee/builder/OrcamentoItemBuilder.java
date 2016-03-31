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

import br.com.devmedia.consultorioee.model.Orcamento;
import br.com.devmedia.consultorioee.model.OrcamentoItem;
import br.com.devmedia.consultorioee.model.Servico;

import java.math.BigDecimal;

/**
 *
 * @author vsaueia
 */
public class OrcamentoItemBuilder {
    private final OrcamentoItem orcamentoItem;
    
    public OrcamentoItemBuilder(Orcamento orcamento) {
        this.orcamentoItem = new OrcamentoItem();
        orcamentoItem.setOrcamento(orcamento);
    }
    
    public OrcamentoItem create() {
        return orcamentoItem;
    }
    
    public OrcamentoItemBuilder comTotal(BigDecimal total) {
        orcamentoItem.setTotal(total);
        return this;
    }
    
    public OrcamentoItemBuilder comOrcamento(Orcamento orcamento) {
        orcamentoItem.setOrcamento(orcamento);
        return this;
    }
    
    public OrcamentoItemBuilder comServico(Servico servico) {
        orcamentoItem.setServico(servico);
        return this;
    }
}
