package com.eventosPeru.eventos.notificacion.service;

import com.eventosPeru.eventos.notificacion.dto.CorreoRequest;
import com.eventosPeru.eventos.notificacion.dto.NotificacionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificacionService {

    private final JavaMailSender mailSender;

    public NotificacionResponse enviarCorreo(CorreoRequest request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getDestinatario());
            message.setSubject(request.getAsunto());
            message.setText(request.getMensaje());

            mailSender.send(message);

            log.info("Correo enviado exitosamente a: {}", request.getDestinatario());

            return NotificacionResponse.builder()
                    .destinatario(request.getDestinatario())
                    .asunto(request.getAsunto())
                    .estado("ENVIADO")
                    .fechaEnvio(LocalDateTime.now())
                    .mensaje("Correo enviado exitosamente")
                    .build();

        } catch (Exception e) {
            log.error("Error al enviar correo a: {}", request.getDestinatario(), e);

            return NotificacionResponse.builder()
                    .destinatario(request.getDestinatario())
                    .asunto(request.getAsunto())
                    .estado("ERROR")
                    .fechaEnvio(LocalDateTime.now())
                    .mensaje("Error al enviar correo: " + e.getMessage())
                    .build();
        }
    }

    public void enviarCorreoRegistro(String destinatario, String nombre) {
        CorreoRequest request = CorreoRequest.builder()
                .destinatario(destinatario)
                .asunto("Bienvenido a Eventos Perú")
                .mensaje(String.format("""
                        Hola %s,
                        
                        ¡Bienvenido a Eventos Perú!
                        
                        Tu cuenta ha sido creada exitosamente.
                        Ya puedes empezar a gestionar tus eventos.
                        
                        Saludos,
                        Equipo de Eventos Perú
                        """, nombre))
                .build();

        enviarCorreo(request);
    }

    public void enviarNotificacionEstadoEvento(String destinatario, String tituloEvento, String estadoAnterior, String estadoNuevo) {
        CorreoRequest request = CorreoRequest.builder()
                .destinatario(destinatario)
                .asunto("Cambio de estado en tu evento")
                .mensaje(String.format("""
                        Hola,
                        
                        Te informamos que el estado de tu evento "%s" ha cambiado.
                        
                        Estado anterior: %s
                        Estado nuevo: %s
                        
                        Puedes revisar más detalles en tu panel de control.
                        
                        Saludos,
                        Equipo de Eventos Perú
                        """, tituloEvento, estadoAnterior, estadoNuevo))
                .build();

        enviarCorreo(request);
    }

    public void enviarRecordatorioEvento(String destinatario, String tituloEvento, LocalDateTime fechaEvento) {
        CorreoRequest request = CorreoRequest.builder()
                .destinatario(destinatario)
                .asunto("Recordatorio de evento próximo")
                .mensaje(String.format("""
                        Hola,
                        
                        Te recordamos que tu evento "%s" está programado para el %s.
                        
                        Por favor asegúrate de tener todo preparado.
                        
                        Saludos,
                        Equipo de Eventos Perú
                        """, tituloEvento, fechaEvento))
                .build();

        enviarCorreo(request);
    }

    public void enviarCorreoRecuperacionPassword(String destinatario, String token) {
        CorreoRequest request = CorreoRequest.builder()
                .destinatario(destinatario)
                .asunto("Recuperación de contraseña - Eventos Perú")
                .mensaje(String.format("""
                        Hola,
                        
                        Has solicitado recuperar tu contraseña.
                        
                        Usa el siguiente token para restablecer tu contraseña:
                        %s
                        
                        Este token es válido por 24 horas.
                        
                        Si no solicitaste este cambio, ignora este correo.
                        
                        Saludos,
                        Equipo de Eventos Perú
                        """, token))
                .build();

        enviarCorreo(request);
    }
}
