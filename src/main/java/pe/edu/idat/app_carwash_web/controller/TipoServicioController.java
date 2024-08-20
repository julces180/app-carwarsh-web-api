package pe.edu.idat.app_carwash_web.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.app_carwash_web.model.bd.dto.RespuestaGeneral;
import pe.edu.idat.app_carwash_web.model.bd.dto.TipoServicioDto;
import pe.edu.idat.app_carwash_web.service.ITipoServicioService;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/tipo-servicio")
public class TipoServicioController {
    private  ITipoServicioService tipoServicioService;

    @GetMapping ("")
    public String frmtiposervicios(Model model) {
        model.addAttribute("listtiposervicio",
            tipoServicioService.listarTipoServicio());
        return "ptiposervicios/frmtiposervicios";
    }
    @GetMapping("/listar")
    @ResponseBody
    public List<TipoServicioDto> listarTipoServicios() {
        return tipoServicioService.listarTipoServicio();
    }
    @PostMapping("/registrar")
    @ResponseBody
    public RespuestaGeneral guardarTipoServicio(@RequestBody TipoServicioDto tipoServicioDto) {
        String mensaje = "Tipo de servicio registrado correctamente";
        boolean resultado = true;
        try {
            tipoServicioService.guardarTipoServicio(tipoServicioDto);
        } catch (Exception ex) {
            mensaje = "Ha ocurrido un error: " + ex.getMessage();
            resultado = false;
            // Opcionalmente, podrías registrar la excepción completa
            ex.printStackTrace();
        }
        return RespuestaGeneral.builder().mensaje(mensaje).resultado(resultado).build();
    }
}