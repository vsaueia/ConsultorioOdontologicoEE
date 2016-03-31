package br.com.devmedia.consultorioee.converter;

import br.com.devmedia.consultorioee.model.Servico;
import br.com.devmedia.consultorioee.service.ServicosService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vsaueia
 */
@FacesConverter("servicoConverter")
public class ServicoConverter implements Converter {

    private final ServicosService servicoService = lookupServicosServiceBean();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        return lookupServicosServiceBean().findByDescricaoExata(value.toString());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        return ((Servico) value).getDescricao();
    }

    private ServicosService lookupServicosServiceBean() {
        try {
            Context c = new InitialContext();
            return (ServicosService) c.lookup("java:global/br.com.devmedia.consultorioee_ConsultorioOdontologicoEE-ear_ear_1.0-SNAPSHOT/br.com.devmedia.consultorioee_ConsultorioOdontologicoEE-ejb_ejb_1.0-SNAPSHOT/ServicosService!br.com.devmedia.consultorioee.service.ServicosService");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
