package dh.backend.clinica;
import dh.backend.clinica.entity.Odontologo;
import dh.backend.clinica.service.impl.OdontologoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class OdontologoServiceTest {

    @Autowired
    OdontologoService odontologoService;
    Odontologo odontologo;
    Odontologo odontologoDesdeDb;

    @BeforeEach
    void cargarDatos(){
        odontologo = new Odontologo();
        odontologo.setApellido("Vilcapaza");
        odontologo.setNombre("Camila");
        odontologo.setNroMatricula("48990004");
        odontologoDesdeDb = odontologoService.guardarOdontologo(odontologo);
    }


    @Test
    @DisplayName("Listar todos los odontologos")
    void caso1(){
        //Dado
        List<Odontologo> odontologos;
        // cuando
        odontologos = odontologoService.buscarTodos();
        // entonces
        assertFalse(odontologos.isEmpty());
    }
}