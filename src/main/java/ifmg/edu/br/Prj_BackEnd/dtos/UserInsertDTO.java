package ifmg.edu.br.Prj_BackEnd.dtos;

public class UserInsertDTO extends UserDTO{
    private String password;

    public UserInsertDTO() {
        super();
    }

    public UserInsertDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
