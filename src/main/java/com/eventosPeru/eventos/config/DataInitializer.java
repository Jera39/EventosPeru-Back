package com.eventosPeru.eventos.config;

import com.eventosPeru.eventos.auth.model.Permiso;
import com.eventosPeru.eventos.auth.model.Rol;
import com.eventosPeru.eventos.auth.repository.PermisoRepository;
import com.eventosPeru.eventos.auth.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Crear permisos si no existen
        if (permisoRepository.count() == 0) {
            Permiso createEvent = Permiso.builder()
                    .nombre("CREATE_EVENT")
                    .descripcion("Crear eventos")
                    .build();

            Permiso updateEvent = Permiso.builder()
                    .nombre("UPDATE_EVENT")
                    .descripcion("Actualizar eventos")
                    .build();

            Permiso deleteEvent = Permiso.builder()
                    .nombre("DELETE_EVENT")
                    .descripcion("Eliminar eventos")
                    .build();

            Permiso viewEvent = Permiso.builder()
                    .nombre("VIEW_EVENT")
                    .descripcion("Ver eventos")
                    .build();

            Permiso manageProviders = Permiso.builder()
                    .nombre("MANAGE_PROVIDERS")
                    .descripcion("Gestionar proveedores")
                    .build();

            permisoRepository.save(createEvent);
            permisoRepository.save(updateEvent);
            permisoRepository.save(deleteEvent);
            permisoRepository.save(viewEvent);
            permisoRepository.save(manageProviders);
        }

        // Crear roles si no existen
        if (rolRepository.count() == 0) {
            Set<Permiso> permisos = new HashSet<>(permisoRepository.findAll());

            // Rol Cliente
            Set<Permiso> permisosCliente = new HashSet<>();
            permisos.stream()
                    .filter(p -> p.getNombre().equals("CREATE_EVENT") || 
                                 p.getNombre().equals("VIEW_EVENT") || 
                                 p.getNombre().equals("UPDATE_EVENT"))
                    .forEach(permisosCliente::add);

            Rol rolCliente = Rol.builder()
                    .nombre("ROLE_CLIENTE")
                    .descripcion("Cliente del sistema")
                    .permisos(permisosCliente)
                    .build();

            // Rol Proveedor
            Set<Permiso> permisosProveedor = new HashSet<>();
            permisos.stream()
                    .filter(p -> p.getNombre().equals("VIEW_EVENT") || 
                                 p.getNombre().equals("UPDATE_EVENT"))
                    .forEach(permisosProveedor::add);

            Rol rolProveedor = Rol.builder()
                    .nombre("ROLE_PROVEEDOR")
                    .descripcion("Proveedor/Encargado del sistema")
                    .permisos(permisosProveedor)
                    .build();

            // Rol Admin
            Rol rolAdmin = Rol.builder()
                    .nombre("ROLE_ADMIN")
                    .descripcion("Administrador del sistema")
                    .permisos(permisos)
                    .build();

            rolRepository.save(rolCliente);
            rolRepository.save(rolProveedor);
            rolRepository.save(rolAdmin);

            System.out.println("✅ Roles y permisos inicializados correctamente");
        }
    }
}
