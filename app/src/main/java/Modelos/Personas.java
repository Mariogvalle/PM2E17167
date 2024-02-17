package Modelos;

public class Personas {
    private Integer id;
    private String pais;
    private String nombres;
    private String telefono;
    private String nota;
    private String imagen;

    public Personas() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Personas(Integer id, String pais, String nombres, String telefono, String nota, String imagen) {
        this.id = id;
        this.pais = pais;
        this.nombres = nombres;
        this.telefono = telefono;
        this.nota= nota;
        this.imagen = imagen;
    }
}
