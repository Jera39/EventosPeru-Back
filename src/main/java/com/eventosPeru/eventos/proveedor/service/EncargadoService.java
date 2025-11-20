package com.eventosPeru.eventos.proveedor.service;

import com.eventosPeru.eventos.auth.model.Usuario;
import com.eventosPeru.eventos.auth.service.AuthService;
import com.eventosPeru.eventos.common.exception.ResourceNotFoundException;
import com.eventosPeru.eventos.proveedor.dto.CalificacionResponse;
import com.eventosPeru.eventos.proveedor.dto.EncargadoRequest;
import com.eventosPeru.eventos.proveedor.dto.EncargadoResponse;
import com.eventosPeru.eventos.proveedor.model.Encargado;
import com.eventosPeru.eventos.proveedor.repository.EncargadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EncargadoService {

    private final EncargadoRepository encargadoRepository;
    private final AuthService authService;

    @Transactional
    public EncargadoResponse registrarEncargado(EncargadoRequest request) {
        // Buscar si existe un usuario con ese email
        Usuario usuario = null;
        try {
            usuario = authService.obtenerUsuarioPorEmail(request.getEmail());
        } catch (ResourceNotFoundException e) {
            // Si no existe usuario, creamos el encargado sin usuario asociado
        }

        // Crear el encargado
        Encargado encargado = Encargado.builder()
                .usuario(usuario)
                .nombre(request.getNombre())
                .especialidad(request.getEspecialidad())
                .experiencia(request.getExperiencia())
                .telefono(request.getTelefono())
                .build();

        Encargado encargadoGuardado = encargadoRepository.save(encargado);

        return mapearAEncargadoResponse(encargadoGuardado);
    }

    // Método interno para crear encargado cuando ya tenemos el usuario (desde AuthService)
    @Transactional
    public Encargado crearEncargadoConUsuario(Usuario usuario, String especialidad, String experiencia) {
        return Encargado.builder()
                .usuario(usuario)
                .nombre(usuario.getNombre())
                .especialidad(especialidad != null ? especialidad : "General")
                .experiencia(experiencia != null ? experiencia : "0 años")
                .telefono(usuario.getTelefono())
                .disponible(true)
                .build();
    }

    @Transactional
    public EncargadoResponse actualizarEncargado(Long encargadoId, EncargadoRequest request) {
        Encargado encargado = encargadoRepository.findById(encargadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Encargado no encontrado con ID: " + encargadoId));

        encargado.setNombre(request.getNombre());
        encargado.setEspecialidad(request.getEspecialidad());
        encargado.setExperiencia(request.getExperiencia());
        encargado.setTelefono(request.getTelefono());

        Encargado encargadoActualizado = encargadoRepository.save(encargado);

        return mapearAEncargadoResponse(encargadoActualizado);
    }

    @Transactional
    public EncargadoResponse actualizarDisponibilidad(Long encargadoId, Boolean disponible) {
        Encargado encargado = encargadoRepository.findById(encargadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Encargado no encontrado con ID: " + encargadoId));

        encargado.setDisponible(disponible);

        Encargado encargadoActualizado = encargadoRepository.save(encargado);

        return mapearAEncargadoResponse(encargadoActualizado);
    }

    @Transactional
    public void actualizarCalificacion(Long encargadoId, Integer nuevaCalificacion) {
        Encargado encargado = encargadoRepository.findById(encargadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Encargado no encontrado con ID: " + encargadoId));

        // Calcular nueva calificación promedio
        BigDecimal calificacionActual = encargado.getCalificacionPromedio();
        Integer totalCalificaciones = encargado.getTotalCalificaciones();

        BigDecimal suma = calificacionActual.multiply(BigDecimal.valueOf(totalCalificaciones));
        suma = suma.add(BigDecimal.valueOf(nuevaCalificacion));
        
        totalCalificaciones++;
        BigDecimal nuevoPromedio = suma.divide(BigDecimal.valueOf(totalCalificaciones), 2, java.math.RoundingMode.HALF_UP);

        encargado.setCalificacionPromedio(nuevoPromedio);
        encargado.setTotalCalificaciones(totalCalificaciones);

        encargadoRepository.save(encargado);
    }

    public List<EncargadoResponse> consultarDisponibles() {
        return encargadoRepository.findByDisponibleTrue().stream()
                .map(this::mapearAEncargadoResponse)
                .toList();
    }

    public List<EncargadoResponse> consultarPorEspecialidad(String especialidad) {
        return encargadoRepository.findByEspecialidad(especialidad).stream()
                .map(this::mapearAEncargadoResponse)
                .toList();
    }

    public List<EncargadoResponse> consultarDisponiblesPorEspecialidad(String especialidad) {
        return encargadoRepository.findByEspecialidadAndDisponibleTrue(especialidad).stream()
                .map(this::mapearAEncargadoResponse)
                .toList();
    }

    public EncargadoResponse obtenerEncargado(Long encargadoId) {
        Encargado encargado = encargadoRepository.findById(encargadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Encargado no encontrado con ID: " + encargadoId));

        return mapearAEncargadoResponse(encargado);
    }

    public List<EncargadoResponse> listarEncargados() {
        return encargadoRepository.findAll().stream()
                .map(this::mapearAEncargadoResponse)
                .toList();
    }

    public CalificacionResponse obtenerCalificacion(Long encargadoId) {
        Encargado encargado = encargadoRepository.findById(encargadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Encargado no encontrado con ID: " + encargadoId));

        return CalificacionResponse.builder()
                .encargadoId(encargado.getEncargadoId())
                .nombre(encargado.getNombre())
                .calificacionPromedio(encargado.getCalificacionPromedio())
                .totalCalificaciones(encargado.getTotalCalificaciones())
                .build();
    }

    public Encargado obtenerEncargadoPorId(Long encargadoId) {
        return encargadoRepository.findById(encargadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Encargado no encontrado con ID: " + encargadoId));
    }

    private EncargadoResponse mapearAEncargadoResponse(Encargado encargado) {
        return EncargadoResponse.builder()
                .encargadoId(encargado.getEncargadoId())
                .nombre(encargado.getNombre())
                .especialidad(encargado.getEspecialidad())
                .experiencia(encargado.getExperiencia())
                .telefono(encargado.getTelefono())
                .calificacionPromedio(encargado.getCalificacionPromedio())
                .totalCalificaciones(encargado.getTotalCalificaciones())
                .disponible(encargado.getDisponible())
                .build();
    }
}
