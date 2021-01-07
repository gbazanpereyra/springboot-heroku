package com.bolsadeideas.springdatajpa.controllers;

import com.bolsadeideas.springdatajpa.models.entities.Cliente;
import com.bolsadeideas.springdatajpa.models.services.IClienteService;
import com.bolsadeideas.springdatajpa.models.services.IUploadFileService;
import com.bolsadeideas.springdatajpa.util.paginator.PageRender;

import com.bolsadeideas.springdatajpa.view.xml.ClienteList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;


@Controller
@SessionAttributes("cliente")
public class ClienteController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IUploadFileService uploadFileService;


    @Autowired
    @Qualifier("clienteService")
    private IClienteService clienteService;

    @Autowired
    private MessageSource messageSource;

    //METODO API REST
    @GetMapping("/listar-rest")
    public @ResponseBody ClienteList /*List<Cliente>*/ listarRest(){

        return new ClienteList(clienteService.findAll());
    }


    @GetMapping({"/listar", "/"})
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page,
                         Model model,
                         Authentication authentication,
                         HttpServletRequest request,
                         Locale locale) {

        //Utilizamos auth de forma estática
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            logger.info("Hola usuario autenticado de forma estática, tu user name es ".concat(auth.getName()));
        }

        //Control de autenticación usando hasRole propio
        if(hasRole("ROLE_ADMIN")) {
            logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso"));
        } else {
            logger.info("Hola ".concat(auth.getName()).concat(" no tienes acceso"));
        }

        //Control de autenticación utilizando SecurityContextHolderAwareRequestWrapper
        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "");
        if (securityContext.isUserInRole("ROLE_ADMIN")) {
            logger.info("Controlamos la autenticación del usuario usado 'SecurityContextHolderAwareRequestWrapper'. Usuario autenticado: ".concat(auth.getName()));
        } else {
            logger.info("No tienes acceso con 'SecurityContextHolderAwareRequestWrapper'");
        }

        //Control de autenticación utilizando el HttpServletRequest
        if (request.isUserInRole("ROLE_ADMIN")) {
            logger.info("Controlamos la autenticación del usuario usado 'HttpServletRequest'. Usuario autenticado: ".concat(auth.getName()));
        } else {
            logger.info("No tienes acceso con 'HttpServletRequest'");
        }


        //Paginación
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Cliente> clientes = clienteService.findAll(pageRequest);
        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

        model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);
        return "listar";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/form")
    public String crear(Model model) {
        Cliente cliente = new Cliente();

        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Formulario de Clientes");
        model.addAttribute("tituloBtn", "Crear cliente");

        return "form";
    }

    //Sirve tanto para nuevos usuarios como para modificar los que ya están en la BD
    @Secured("ROLE_ADMIN")
    @PostMapping("/form")
    public String guardar(@Valid /*@ModelAttribute(name = "cliente")*/ Cliente cliente, BindingResult result,
                          Model model, RedirectAttributes flash,
                          SessionStatus status, @RequestParam("file") MultipartFile foto) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Clientes");
            model.addAttribute("tituloBtn", "Crear cliente");

            if (cliente.getId() != null) {
                model.addAttribute("titulo", "Modificar Cliente");
                model.addAttribute("tituloBtn", "Modificar cliente");
            }

            return "form";
        }

        if (!foto.isEmpty()) {
            //Controlamos para eliminar la foto
            if (cliente.getId() != null
                    && cliente.getId() > 0
                    && cliente.getFoto() != null
                    && cliente.getFoto().length() > 0) {

                uploadFileService.delete(cliente.getFoto());
            }

            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            flash.addFlashAttribute("info", "Has subido correctamente " + uniqueFilename);
            cliente.setFoto(uniqueFilename);

        }


        String messaggeFlash = (cliente.getId() != null) ? "Cliente modificado con éxito" : "Cliente creado con éxito";

        clienteService.save(cliente);

        status.setComplete();
        flash.addFlashAttribute("success", messaggeFlash);
        return "redirect:listar";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Cliente cliente = null;

        if (id > 0) {
            cliente = clienteService.findOne(id);
            if (cliente == null) {
                flash.addFlashAttribute("error", "El ID del cliente no existe en el sistema!");
                return "redirect:/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
            return "redirect:/listar";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        model.addAttribute("tituloBtn", "Modificar cliente");

        return "/form";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            Cliente cliente = clienteService.findOne(id);

            clienteService.delete(id);
            flash.addFlashAttribute("success", "Cliente eliminado con éxito");

            //eliminamos la foto
            if (uploadFileService.delete(cliente.getFoto())) {
                flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminada con éxito");
            }
        }

        return "redirect:/listar";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Cliente cliente = clienteService.fetchByIdWithFacturas(id); //clienteService.findOne(id);

        //Valido si el cliente existe
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");

            return "redirect:/listar";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Detalle de cliente ".concat(cliente.getNombre()));

        return "ver";
    }

    //Cuando se hace un GET para la imagen, este método se encarga de retornnar la imagen
    @Secured("ROLE_USER")
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

        Resource recurso = null;
        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    private boolean hasRole(String role) {
        SecurityContext context = SecurityContextHolder.getContext();

        if (context == null) {
            return false;
        }

        Authentication auth = context.getAuthentication();

        if (auth == null) {
            return false;
        }

        Collection<? extends GrantedAuthority> auhtorities = auth.getAuthorities();

        return auhtorities.contains(new SimpleGrantedAuthority(role));

        /*for (GrantedAuthority authority : auhtorities) {
            if (role.equals(authority.getAuthority())) {
                logger.info("Hola ".concat(auth.getName()).concat("tu rol es").concat(authority.getAuthority()));
                return true;
            }
        }

        return false;*/
    }
}
