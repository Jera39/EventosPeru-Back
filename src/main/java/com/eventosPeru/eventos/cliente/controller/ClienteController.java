package com.eventosPeru.eventos.cliente.controller;

import com.eventosPeru.eventos.cliente.dto.ClienteRequest;
import com.eventosPeru.eventos.cliente.dto.ClienteResponse;
import com.eventosPeru.eventos.cliente.dto.HistorialEventoResponse;
import com.eventosPeru.eventos.cliente.service.ClienteService;
import com.eventosPeru.eventos.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<ApiResponse<ClienteResponse>> crearCliente(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.crearCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Cliente creado exitosamente", response));
    }

    @PutMapping("/{clienteId}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<ApiResponse<ClienteResponse>> actualizarCliente(
            @PathVariable Long clienteId,
            @Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.actualizarCliente(clienteId, request);
        return ResponseEntity.ok(ApiResponse.success("Cliente actualizado exitosamente", response));
    }

    @GetMapping("/{clienteId}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<ClienteResponse>> obtenerCliente(@PathVariable Long clienteId) {
        ClienteResponse response = clienteService.obtenerPerfil(clienteId);
        return ResponseEntity.ok(ApiResponse.success("Cliente obtenido exitosamente", response));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ApiResponse<ClienteResponse>> obtenerClientePorUsuarioId(@PathVariable Long usuarioId) {
        ClienteResponse response = clienteService.obtenerPerfilPorUsuarioId(usuarioId);
        return ResponseEntity.ok(ApiResponse.success("Cliente obtenido exitosamente", response));
    }

    @GetMapping("/perfil")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<ApiResponse<ClienteResponse>> obtenerPerfilActual() {
        ClienteResponse response = clienteService.obtenerPerfilActual();
        return ResponseEntity.ok(ApiResponse.success("Perfil obtenido exitosamente", response));
    }

    @GetMapping("/{clienteId}/historial")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<HistorialEventoResponse>>> obtenerHistorialEventos(
            @PathVariable Long clienteId) {
        List<HistorialEventoResponse> historial = clienteService.obtenerHistorialEventos(clienteId);
        return ResponseEntity.ok(ApiResponse.success("Historial de eventos obtenido exitosamente", historial));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ClienteResponse>>> listarClientes() {
        List<ClienteResponse> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(ApiResponse.success("Clientes listados exitosamente", clientes));
    }
}
