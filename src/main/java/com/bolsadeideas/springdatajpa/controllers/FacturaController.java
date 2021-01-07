package com.bolsadeideas.springdatajpa.controllers;

import com.bolsadeideas.springdatajpa.models.entities.Cliente;
import com.bolsadeideas.springdatajpa.models.entities.Factura;
import com.bolsadeideas.springdatajpa.models.entities.ItemFactura;
import com.bolsadeideas.springdatajpa.models.entities.Producto;
import com.bolsadeideas.springdatajpa.models.services.IClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IClienteService iClienteService;


    @GetMapping("form/{clienteId}")
    public String crear(@PathVariable(name = "clienteId") Long clienteId, Model model, RedirectAttributes flash, SessionStatus status) {

        Cliente cliente = iClienteService.findOne(clienteId);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la BD");
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Crear Factura");

        //status.setComplete();

        return "factura/form";
    }

    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody
    List<Producto> cargarProductos(@PathVariable String term) {

        return iClienteService.findByNombre(term);
    }

    @PostMapping("/form")
    public String guardar(@Valid Factura factura,
          BindingResult result,
          Model model,
          @RequestParam(name = "item_id[]", required = false) Long[] itemId,
          @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
          RedirectAttributes flash,
          SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Crear factura");
            return "factura/form";
        }

        if (itemId == null || itemId.length == 0) {
            model.addAttribute("titulo", "Crear factura");
            model.addAttribute("error", "Error: no se han seleccionado productos");
            return "factura/form";
        }

        for (int i = 0; i < itemId.length; i++) {
            Producto producto = iClienteService.findProductoById(itemId[i]);

            ItemFactura linea = new ItemFactura();
            linea.setProducto(producto);
            linea.setCantidad(cantidad[i]);

            factura.addItemFactura(linea);

            log.info("ID " + itemId[i].toString() + ", cantidad: " + cantidad[i]);
        }

        iClienteService.saveFactura(factura);
        status.setComplete();

        flash.addFlashAttribute("success", "Factura creada con éxito");

        return "redirect:/ver/" + factura.getCliente().getId();
    }


    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id,
              Model model,
              RedirectAttributes flash){

        Factura factura = iClienteService.fetchByIdWithClienteWithItemFacturaWithProducto(id); //iClienteService.findFacturaById(id);

        if (factura == null) {
            flash.addFlashAttribute("error", "La factura no existe en la base de datos");
            return "redirect:/listar";
        }

        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Factura: ".concat(factura.getDescripcion()));

        return "factura/ver";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        Factura factura = iClienteService.findFacturaById(id);

        if (factura != null) {
            iClienteService.deleteFactura(id);
            flash.addFlashAttribute("success", "Factura eliminada con éxito");

            return "redirect:/ver/" + factura.getCliente().getId();
        }

        flash.addFlashAttribute("error", "La factura no existe en la base de datos");
        return "redirect:/listar";
    }

}
