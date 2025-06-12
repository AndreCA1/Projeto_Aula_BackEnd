package ifmg.edu.br.Prj_BackEnd.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ResquestTokenDTO {
    @NotBlank(message = "Campo obrigatório")
    @Email(message = "Email inválido")
    private String email;

    public ResquestTokenDTO() {
    }

    public ResquestTokenDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
