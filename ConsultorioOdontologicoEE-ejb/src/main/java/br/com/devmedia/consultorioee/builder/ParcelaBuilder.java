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
import br.com.devmedia.consultorioee.model.Parcela;

import java.math.BigDecimal;

/**
 *
 * @author vsaueia
 */
public class ParcelaBuilder {
    private final Parcela parcela;
    
    public ParcelaBuilder() {
        parcela = new Parcela();
    }
    
    public Parcela create() {
        return parcela;
    }
    
    public ParcelaBuilder comNumero(int numero) {
        parcela.setNumero(numero);
        return this;
    }
    
    public ParcelaBuilder comValor(BigDecimal valor) {
        parcela.setValor(valor);
        return this;
    }
    
    public ParcelaBuilder comOrcamento(Orcamento orcamento) {
        parcela.setOrcamento(orcamento);
        return this;
    }
    
    public ParcelaBuilder emAberto(boolean status) {
        parcela.setPago(!status);
        return this;
    }
}
