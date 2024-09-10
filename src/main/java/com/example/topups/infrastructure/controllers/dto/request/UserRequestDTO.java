package com.example.topups.infrastructure.controllers.dto.request;

import com.example.topups.domain.entities.Login;
import com.example.topups.domain.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.example.topups.infrastructure.controllers.dto.validation.PasswordMatches;
import com.example.topups.infrastructure.controllers.dto.validation.EmailMatches;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@PasswordMatches
@EmailMatches
public class UserRequestDTO {

    private static final Logger logger = LoggerFactory.getLogger(UserRequestDTO.class);

    @NotNull(message = "El nombre es obligatorio.")
    @NotBlank(message = "El campo nombre no puede estar vacío.")
    private String name;

    @NotNull(message = "El correo electrónico es obligatorio.")
    @NotBlank(message = "El campo correo electrónico no puede estar vacío.")
    @Email(message = "Formato del correo electrónico inválido.")
    private String email;

    @NotNull(message = "La confirmación del correo electrónico es obligatoria.")
    @NotBlank(message = "El campo de confirmación del correo electrónico no puede estar vacío.")
    @Email(message = "Formato de la confirmación del correo electrónico inválido.")
    private String confirmEmail;

    @NotNull(message = "El teléfono es obligatorio.")
    @NotBlank(message = "El campo teléfono no puede estar vacío.")
    @Size(min = 10, message = "El teléfono debe tener al menos 10 caracteres.")
    @Size(max = 12, message = "El teléfono no debe exceder los 12 caracteres.")
    private String phone;

    @NotNull(message = "El nombre de usuario es obligatorio.")
    @NotBlank(message = "El campo nombre de usuario no puede estar vacío.")
    private String username;

    @NotNull(message = "La contraseña es obligatoria.")
    @NotBlank(message = "El campo contraseña no puede estar vacío.")
    @Size(min = 12, message = "La contraseña debe tener al menos 12 caracteres.")
    private String password;

    @NotNull(message = "La confirmación de la contraseña es obligatoria.")
    @NotBlank(message = "El campo de confirmación de la contraseña no puede estar vacío.")
    @Size(min = 12, message = "La confirmación de la contraseña debe tener al menos 12 caracteres.")
    private String confirmPassword;

    public User toModel() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPhone(this.phone);

        logger.info("Conversión de UserRequestDTO a modelo User: nombre={}, email={}, teléfono={}",
                user.getName(), user.getEmail(), user.getPhone());

        return user;
    }

    public Login toModelLogin() {
        Login login = new Login();
        login.setUsername(this.username);
        login.setPassword(this.password);

        logger.info("Conversión de UserRequestDTO a modelo Login: username={}", login.getUsername());

        return login;
    }
}
