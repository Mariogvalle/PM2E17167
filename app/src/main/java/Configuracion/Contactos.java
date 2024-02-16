package Configuracion;

public class Contactos {
    //Nombre de la base de datos
    public static final String DBName = "PMEXAMNE";

    //Creación de las tablas de las bases de datos.
    public static final String TableContactos = "contactos";

    //Creacion de los campos de base de datos

    public static final String id = "id";
    public static final String pais = "pais";
    public static final String nombres = "nombres";
    public static final String telefono = "telefono";
    public static final String nota = "nota";
    public static final String imagen = "imagen";


    // DDL Create
    public static final String CreateTableContactos = "Create table "+ TableContactos +"("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT, pais TEXT, nombres TEXT, telefono TEXT, nota TEXT, "+
            "imagen TEXT )";

    //DDL Drop
    public static final String DropTableContactos = "DROP TABLE IF EXISTS "+ TableContactos;

    //DML
    public static final String SelectAllPersonas = "SELECT * FROM " + TableContactos;
}
