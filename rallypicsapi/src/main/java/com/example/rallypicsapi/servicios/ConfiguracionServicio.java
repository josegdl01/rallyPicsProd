package com.example.rallypicsapi.servicios;

import com.example.rallypicsapi.modelos.Configuracion;
import com.example.rallypicsapi.repositorios.ConfiguracionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfiguracionServicio {

    @Autowired
    private ConfiguracionRepositorio configuracionRepositorio;

    public Optional<Configuracion> getConfiguracion(Long id) {
        return configuracionRepositorio.findById(id);
    }

    public Configuracion upsertConfiguracion(Configuracion configuracion) {
        return configuracionRepositorio.save(configuracion);
    }

    public void cerrarSorteo() {
        Configuracion config = configuracionRepositorio.findById(1L).orElse(new Configuracion());
        config.setCerrado(true);
        configuracionRepositorio.save(config);
    }

    public boolean estaCerrado() {
        Configuracion config = configuracionRepositorio.findById(1L).orElse(new Configuracion());
        return config.isCerrado();
    }
}
