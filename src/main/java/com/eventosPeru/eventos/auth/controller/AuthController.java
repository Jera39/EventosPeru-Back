package com.eventosPeru.eventos.auth.controller;

import com.eventosPeru.eventos.auth.dto.*;
import com.eventosPeru.eventos.auth.service.AuthService;
import com.eventosPeru.eventos.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<ApiResponse<AuthResponse>> registrar(@Valid @RequestBody RegistroRequest request) {
        AuthResponse response = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario registrado exitosamente", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login exitoso", response));
    }

    @PostMapping("/recuperar-password")
    public ResponseEntity<ApiResponse<String>> recuperarPassword(@Valid @RequestBody RecuperarPasswordRequest request) {
        String token = authService.solicitarRecuperacionPassword(request);
        return ResponseEntity.ok(ApiResponse.success(
                "Se ha enviado un correo con las instrucciones para recuperar tu contraseña",
                token
        ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetearPassword(request);
        return ResponseEntity.ok(ApiResponse.success("Contraseña actualizada exitosamente", null));
    }
}
