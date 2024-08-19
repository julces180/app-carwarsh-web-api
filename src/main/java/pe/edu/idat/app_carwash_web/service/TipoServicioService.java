package pe.edu.idat.app_carwash_web.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.idat.app_carwash_web.model.bd.Acciones;
import pe.edu.idat.app_carwash_web.model.bd.DetalleServicio;
import pe.edu.idat.app_carwash_web.model.bd.TipoServicio;
import pe.edu.idat.app_carwash_web.model.bd.dto.AccionesDto;
import pe.edu.idat.app_carwash_web.model.bd.dto.TipoServicioDto;
import pe.edu.idat.app_carwash_web.repository.AccionesRepository;
import pe.edu.idat.app_carwash_web.repository.TipoServicioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TipoServicioService implements ITipoServicioService {
    private TipoServicioRepository tipoServicioRepository;
    private AccionesRepository accionesRepository;


    @Override
    public List<TipoServicioDto> listarTipoServicio() {
        List<TipoServicioDto> tipoServicioDtoList = new ArrayList<>();
        for (TipoServicio tipoServicio : tipoServicioRepository.findAll()) {
            TipoServicioDto tipoServicioDto = TipoServicioDto.builder()
                    .tiposervicioid(tipoServicio.getTiposervicioid())
                    .descripciontps(tipoServicio.getDescripciontps())
                    .tipovehiculo(tipoServicio.getTipovehiculo())
                    .estadotps(tipoServicio.getEstadotps())
                    .acciones(tipoServicio.getAcciones().stream()
                            .map(accion -> AccionesDto.builder()
                                    .accionesid(accion.getId().getAccionesid())
                                    .descripcion(accion.getAcciones().getDescripcion())
                                    .precio(accion.getAcciones().getPrecio())
                                    .estadoacc(accion.getAcciones().isEstadoacc())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
            tipoServicioDtoList.add(tipoServicioDto);
        }
        return tipoServicioDtoList;
    }


    @Override
    @Transactional
    public TipoServicio guardarTipoServicio(TipoServicioDto tipoServicioDto) {
        TipoServicio tipoServicio = new TipoServicio();
        tipoServicio.setDescripciontps(tipoServicioDto.getDescripciontps());
        tipoServicio.setTipovehiculo(tipoServicioDto.getTipovehiculo());
        tipoServicio.setEstadotps(tipoServicioDto.getEstadotps());

        // Asignar las acciones al tipo de servicio a través de DetalleServicio
        Set<DetalleServicio> detalleServicios = tipoServicioDto.getAcciones().stream()
                .map(accionDto -> {
                    Acciones accion = accionesRepository.findById(accionDto.getAccionesid())
                            .orElseThrow(() -> new IllegalArgumentException("Acción no encontrada: " + accionDto.getAccionesid()));

                    DetalleServicio detalleServicio = new DetalleServicio();
                    detalleServicio.setAcciones(accion);
                    detalleServicio.setTiposervicio(tipoServicio);

                    return detalleServicio;
                })
                .collect(Collectors.toSet());

        tipoServicio.setAcciones(detalleServicios);

        // Guardar el tipo de servicio
        return tipoServicioRepository.save(tipoServicio);
    }
    @Override
    public TipoServicio obtenerTipoServicio(Integer idtiposervicio) {
        return tipoServicioRepository.findById(idtiposervicio).orElse(null);
    }

}

