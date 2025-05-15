package ifmg.edu.br.Prj_BackEnd.dtos;

import ifmg.edu.br.Prj_BackEnd.entities.Role;
import ifmg.edu.br.Prj_BackEnd.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserDTO {
    private long id;
    @NotBlank(message = "Campo obrigatório")
    private String firstName;
    @NotBlank(message = "Campo obrigatório")
    private String lastName;
    @Email(message = "Favor informar Email válido")
    private String email;
    private Set<RoleDTO> roles = new HashSet<>();

    public UserDTO() {

    }

    public UserDTO(long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserDTO(User entity) {
        email = entity.getEmail();
        lastName = entity.getLastName();
        firstName = entity.getFirstName();
        id = entity.getId();

        entity.getRoles().forEach(role -> roles.add(new RoleDTO(role)));
    }

    public UserDTO(UserDTO entity, Set<RoleDTO> roles) {
        this.roles = roles;
        this.email = entity.getEmail();
        this.lastName = entity.getLastName();
        this.firstName = entity.getFirstName();
        this.id = entity.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserDTO user)) return false;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
