public class UsuarioActual {
    private static UsuarioActual instance;
    private String usuario;
    private UsuarioActual() {
    }
    public static UsuarioActual getInstance() {
        if (instance == null) {
            instance = new UsuarioActual();
        }
        return instance;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getUsuario() {
        return usuario;
    }
}
