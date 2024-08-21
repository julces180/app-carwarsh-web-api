package pe.edu.idat.app_carwash_web.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.idat.app_carwash_web.model.bd.DetalleServicio;
import pe.edu.idat.app_carwash_web.model.bd.TipoServicio;
import pe.edu.idat.app_carwash_web.model.bd.pk.TipoAccionesId;

public interface DetalleServicioRepository extends JpaRepository<DetalleServicio, TipoAccionesId> {
    @Modifying
    @Transactional
    @Query("DELETE FROM DetalleServicio ds WHERE ds.tiposervicio = :tipoServicio")
    void deleteAllByTipoServicio(@Param("tipoServicio") TipoServicio tipoServicio);
}

