package pe.edu.idat.app_carwash_web.controller;

import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.app_carwash_web.model.bd.Cliente;
import pe.edu.idat.app_carwash_web.model.bd.dto.RespuestaGeneral;
import pe.edu.idat.app_carwash_web.service.IClienteService;

import java.util.List;
@AllArgsConstructor
@Controller
@RequestMapping("/clientes")
public class ClienteController {
    private IClienteService iClienteService;
    @GetMapping("")

    public String frmCliente (Model model){
        model.addAttribute("listcliente",
                iClienteService.listarCliente());
        return "pcliente/frmcliente";

    }
    @GetMapping("/listar")
    @ResponseBody
    public List<Cliente> listarCliente(){

        return iClienteService.listarCliente();
    }

    @GetMapping("/{idcliente}")
    @ResponseBody
    public Cliente obtenerCliente(@PathVariable("idcliente") Integer idcliente){
        return iClienteService.obtenerCliente(idcliente);
    }

    @PostMapping("")
    @ResponseBody
    public Cliente registrarCliente(@RequestBody Cliente cliente){
        return iClienteService.guardarCliente(cliente);
    }

    @PutMapping("/{idcliente}")
    @ResponseBody
    public Cliente actualizarCliente(@PathVariable("idcliente") Integer idcliente,
                                     @RequestBody Cliente cliente){
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setClienteid(idcliente);
        nuevoCliente.setNombre(cliente.getNombre());
        nuevoCliente.setApellido(cliente.getApellido());
        nuevoCliente.setTipodocumento(cliente.getTipodocumento());
        nuevoCliente.setNumerodocumento(cliente.getNumerodocumento());
        nuevoCliente.setTelefono(cliente.getTelefono());
        nuevoCliente.setDireccion(cliente.getDireccion());
        nuevoCliente.setEstadocliente(cliente.isEstadocliente());
        return iClienteService.guardarCliente(cliente);
    }
    @PostMapping("/registrar")
    @ResponseBody
    public RespuestaGeneral guardarCliente(
            @RequestBody Cliente cliente){
        String mensaje = "Producto registrado correctamente";
        boolean resultado = true;
        try {
            iClienteService.guardarCliente(cliente);
        }catch (Exception ex){
            mensaje = "Error: Ocurrio un error al conectarse a la BD";
            resultado = false;
        }
        return RespuestaGeneral.builder().mensaje(mensaje)
                .resultado(resultado).build();
    }

}



