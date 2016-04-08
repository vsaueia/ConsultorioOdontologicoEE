package br.com.devmedia.consultorioee.rest;

import br.com.devmedia.consultorioee.model.Imagem;
import br.com.devmedia.consultorioee.service.ImagemService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("image")
@RequestScoped
public class ImageResource {

    @Context
    private UriInfo context;

    @EJB
    private ImagemService imagemService;

    public ImageResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{idOrcamento}/{idCategoria}")
    public List<Imagem> getImagensData(@PathParam("idOrcamento") long idOrcamento,@PathParam("idCategoria") long idCategoria) {
        System.out.println("Id do orcamento eh : "+idOrcamento);
        System.out.println("Id da Categoria eh : "+idCategoria);

        return imagemService.findByOrcamentoIdECategoriaId(idOrcamento, idCategoria);
    }

}
