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
        package br.com.devmedia.consultorioee.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author vsaueia
 */
public final class FormatadoresUtil {

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private static final DecimalFormat decimalFormatter = new DecimalFormat("#0.00");

    public static String formatarData(Date data) {
        return dateFormatter.format(data);
    }

    public static String formatarDecimal(BigDecimal numero) {
        return decimalFormatter.format(numero);
    }
}