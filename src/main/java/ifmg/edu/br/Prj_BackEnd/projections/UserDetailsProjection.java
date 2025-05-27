package ifmg.edu.br.Prj_BackEnd.projections;

public interface UserDetailsProjection {

    String getUserEmail();
    String getPassword();
    Long getRoleId();
    String getAuthority();
}
