package dh.backend.clinica;

import dh.backend.clinica.entity.Domicilio;
import dh.backend.clinica.entity.Paciente;
import dh.backend.clinica.service.impl.PacienteService;
import org.junit.jupiter.api.BeforeAll;
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
class PacienteServiceTest {

    @Autowired
    PacienteService pacienteService;
    Paciente paciente;
    Paciente pacienteDesdeDb;

    @BeforeEach
    void cargarDatos(){
        Domicilio domicilio = new Domicilio(null,"Falas",145,"PISO","Buenos Aires");
        paciente = new Paciente();
        paciente.setApellido("Velasquez");
        paciente.setNombre("Camila");
        paciente.setDni("48974646");
        paciente.setFechaIngreso(LocalDate.of(2020,7,15));
        paciente.setDomicilio(domicilio);
        pacienteDesdeDb = pacienteService.guardarPaciente(paciente);
    }


    @Test
    @DisplayName("Listar todos los pacientes")
           void caso1(){
       //Dado
      List<Paciente> pacientes;
       // cuando
        pacientes = pacienteService.buscarTodos();
   // entonces
        assertFalse(pacientes.isEmpty());
  }
}
