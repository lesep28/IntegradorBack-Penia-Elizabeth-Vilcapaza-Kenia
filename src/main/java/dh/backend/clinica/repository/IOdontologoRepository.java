package dh.backend.clinica.repository;

import dh.backend.clinica.entity.Odontologo;
import dh.backend.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {


    List<Odontologo> findByApellidoOrNombre(String apellido, String nombre);

    List<Odontologo> findByApellidoStartingWith(String prefix);


}
