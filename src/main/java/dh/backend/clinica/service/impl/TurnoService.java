package dh.backend.clinica.service.impl;

import dh.backend.clinica.dto.request.TurnoModifyDto;
import dh.backend.clinica.dto.request.TurnoRequestDto;
import dh.backend.clinica.dto.response.OdontologoResponseDto;
import dh.backend.clinica.dto.response.PacienteResponseDto;
import dh.backend.clinica.dto.response.TurnoResponseDto;
import dh.backend.clinica.entity.Odontologo;
import dh.backend.clinica.entity.Paciente;
import dh.backend.clinica.entity.Turno;
import dh.backend.clinica.exception.ResourceNotFoundException;
import dh.backend.clinica.repository.ITurnoRepository;
import dh.backend.clinica.service.IOdontologoService;
import dh.backend.clinica.service.IPacienteService;
import dh.backend.clinica.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TurnoService implements ITurnoService {

    private final Logger logger = LoggerFactory.getLogger(TurnoService.class);
    private ITurnoRepository turnoRepository;
    private IPacienteService pacienteService;
    private IOdontologoService odontologoService;

    @Autowired
    private ModelMapper modelMapper;

    public TurnoService(ITurnoRepository turnoRepository, IPacienteService pacienteService, IOdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto){
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());
        Turno turno = new Turno();
        Turno turnoDesdeDB = null;
        TurnoResponseDto turnoResponseDto = null;
        if(paciente.isPresent() && odontologo.isPresent()){
            //Armado del turno desde el turno request dto
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            //obtengo el turno persistido con el id
            turnoDesdeDB = turnoRepository.save(turno);

            //Armado del turno response dto desde el turno obtenido de la base de datos
            //turnoResponseDto = obtenerTurnoResponse(turnoDesdeDB);
           //armado con model mapper
           turnoResponseDto = convertirTurnoEnResponse(turnoDesdeDB);

        }

        return turnoResponseDto;
    }

    @Override
    public Optional<TurnoResponseDto> buscarPorId(Integer id) {
       Optional<Turno> turno = turnoRepository.findById(id);
       if(turno.isPresent()){
           TurnoResponseDto turnoRespuesta = convertirTurnoEnResponse(turno.get());
           return Optional.of(turnoRespuesta);
       }else{
           return null;
       }
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnoDesdeDB = turnoRepository.findAll();
        List<TurnoResponseDto> turnosRespuesta = new ArrayList<>();
        for (Turno t: turnoDesdeDB){
            //model mapper
            TurnoResponseDto turnoRespuesta = convertirTurnoEnResponse(t);
            logger.info("turno "+turnoRespuesta);
            turnosRespuesta.add(turnoRespuesta);
        }

        return turnosRespuesta;
    }

    @Override
    public void modificarTurno(TurnoModifyDto turnoModifyDto) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoModifyDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoModifyDto.getOdontologo_id());
        if(paciente.isPresent() && odontologo.isPresent()){
            Turno turno = new Turno(
                    turnoModifyDto.getId(),
                    paciente.get(), odontologo.get(), LocalDate.parse(turnoModifyDto.getFecha())
                    );
            turnoRepository.save(turno);
        }

    }

    @Override
    public void eliminarTurno(Integer id){
        Optional<TurnoResponseDto> turnoEncontrado = buscarPorId(id);
        if(turnoEncontrado.isPresent()){
            turnoRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }

    @Override
    public Optional<TurnoResponseDto> buscarTurnosPorPaciente(String pacienteApellido) {
        Optional<Turno> turno =turnoRepository.buscarPorApellidoPaciente(pacienteApellido);
        TurnoResponseDto turnoParaResponder = null;
        if(turno.isPresent()){
            turnoParaResponder =convertirTurnoEnResponse(turno.get());
        }
        return  Optional.ofNullable(turnoParaResponder);
    }


    private TurnoResponseDto convertirTurnoEnResponse(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        return  turnoResponseDto;
    }


}
