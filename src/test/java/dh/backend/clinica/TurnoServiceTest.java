package dh.backend.clinica;

import dh.backend.clinica.dto.request.TurnoRequestDto;
import dh.backend.clinica.dto.response.TurnoResponseDto;
import dh.backend.clinica.entity.Domicilio;
import dh.backend.clinica.entity.Odontologo;
import dh.backend.clinica.entity.Paciente;
import dh.backend.clinica.service.impl.OdontologoService;
import dh.backend.clinica.service.impl.PacienteService;
import dh.backend.clinica.service.impl.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class TurnoServiceTest {

    @Autowired
    TurnoService turnoService;
    TurnoRequestDto turno1;
    TurnoRequestDto turno2;
    @Autowired
    PacienteService pacienteService;
    Paciente paciente;
    Paciente pacienteDesdeDb;

    @Autowired
    OdontologoService odontologoService;
    Odontologo odontologo;
    Odontologo odontologoDesdeDb;

    @BeforeEach
    void cargarDatos(){
        Domicilio domicilio = new Domicilio(null,"Fakes",145,"PISO","Buenos Aires");
        paciente = new Paciente();
        paciente.setApellido("Velislav");
        paciente.setNombre("Camila");
        paciente.setDni("48974646");
        paciente.setFechaIngreso(LocalDate.of(2020,7,15));
        paciente.setDomicilio(domicilio);
        pacienteDesdeDb = pacienteService.guardarPaciente(paciente);

        odontologo = new Odontologo();
        odontologo.setApellido("Vilcapaza");
        odontologo.setNombre("Camila");
        odontologo.setNroMatricula("48990004");
        odontologoDesdeDb = odontologoService.guardarOdontologo(odontologo);

        turno1 = new TurnoRequestDto();
        turno1.setPaciente_id(pacienteDesdeDb.getId());
        turno1.setOdontologo_id(odontologoDesdeDb.getId());
        turno1.setFecha("2023-09-13");
        turnoService.guardarTurno(turno1);

        turno2 = new TurnoRequestDto();
        turno2.setPaciente_id(pacienteDesdeDb.getId());
        turno2.setOdontologo_id(odontologoDesdeDb.getId());
        turno2.setFecha("2023-09-19");
        turnoService.guardarTurno(turno2);

    }


    @Test
    @DisplayName("Listar todos los turnos")
    void caso1(){
        //Dado
        List<TurnoResponseDto> turnos ;
        // cuando
        turnos = turnoService.buscarTodos();
        // entonces
        assertFalse(turnos.isEmpty());
    }
}