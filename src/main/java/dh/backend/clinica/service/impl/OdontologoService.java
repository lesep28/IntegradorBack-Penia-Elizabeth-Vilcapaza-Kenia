package dh.backend.clinica.service.impl;


import dh.backend.clinica.entity.Odontologo;
import dh.backend.clinica.exception.ResourceNotFoundException;
import dh.backend.clinica.repository.IOdontologoRepository;
import dh.backend.clinica.service.IOdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {

    static final Logger logger = LoggerFactory.getLogger(OdontologoService.class);

    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        logger.info("Guardando odontologo: {}", odontologo);
        return odontologoRepository.save(odontologo);
    }

    @Override
    public Optional<Odontologo> buscarPorId(Integer id) {
        Optional<Odontologo> odontologoEncontrado = odontologoRepository.findById(id);
        if(odontologoEncontrado.isPresent()){
            logger.info("Odontologo encontrado con id: {}", id);
            return odontologoEncontrado;
        } else {
            logger.warn("Odontologo no encontrado con id: {}", id);
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
    }


    @Override
    public List<Odontologo> buscarTodos() {
        return odontologoRepository.findAll();
    }

    @Override
    public void modificarOdontologo(Odontologo odontologo) {
        odontologoRepository.save(odontologo);
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        logger.info("Eliminando odontologo con ID: {}", id);
        Optional<Odontologo> odontologoEncontrado = buscarPorId(id);
        if(odontologoEncontrado.isPresent()){
            odontologoRepository.deleteById(id);
        } else {
            logger.warn("Odontologo con ID {} no encontrado", id);
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
    }

    @Override
    public List<Odontologo> buscarPorApellidoONombre(String apellido, String nombre) {
        return odontologoRepository.findByApellidoOrNombre(apellido, nombre);
    }

    @Override
    public List<Odontologo> buscarPorApellidoQueComienzaCon(String prefix) {
            return odontologoRepository.findByApellidoStartingWith(prefix);
        }



}
