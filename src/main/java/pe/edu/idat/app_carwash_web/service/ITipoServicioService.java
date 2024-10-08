package pe.edu.idat.app_carwash_web.service;

import jakarta.transaction.Transactional;
import pe.edu.idat.app_carwash_web.model.bd.TipoServicio;
import pe.edu.idat.app_carwash_web.model.bd.dto.TipoServicioDto;

import java.util.List;

public interface ITipoServicioService {
    List<TipoServicioDto> listarTipoServicio();
    void guardarTipoServicio(TipoServicioDto tipoServicioDto);

    void actualizarTipoServicio(TipoServicioDto tipoServicioDto);
    void eliminarTipoServicio(Integer tiposervicioid);

    TipoServicio obtenerTipoServicio(Integer idtiposervicio);
}
