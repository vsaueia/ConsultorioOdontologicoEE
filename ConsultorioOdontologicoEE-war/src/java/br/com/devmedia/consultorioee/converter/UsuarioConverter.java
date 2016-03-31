package br.com.devmedia.consultorioee.converter;

import br.com.devmedia.consultorioee.model.Usuario;
import br.com.devmedia.consultorioee.service.UsuarioService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vsaueia
 */
@FacesConverter("usuarioConverter")
public class UsuarioConverter implements Converter {

    private final UsuarioService usuarioService = lookupUsuarioServiceBean();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        return lookupUsuarioServiceBean().findByNome(value.toString());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        return ((Usuario) value).getNome();
    }

    private UsuarioService lookupUsuarioServiceBean() {
        try {
            Context c = new InitialContext();
            return (UsuarioService) c.lookup("java:global/ConsultorioOdontologicoEE/ConsultorioOdontologicoEE-ejb/UsuarioService!br.com.devmedia.consultorioee.service.UsuarioService");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
