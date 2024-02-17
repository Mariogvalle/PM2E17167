package Configuracion;

public class Transacciones {
    //Nombre de la base de datos
    public static final String DBName = "PMEXAMEN";

    //Creación de las tablas de las bases de datos.
    public static final String TablePersonas = "personas";

    //Creacion de los campos de base de datos

    public static final String id = "id";
    public static final String pais = "pais";
    public static final String nombres = "nombres";
    public static final String telefono = "telefono";
    public static final String nota = "nota";
    public static final String imagen = "imagen";


    // DDL Create
    public static final String CreateTablePersonas = "Create table "+ TablePersonas +"("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT, pais TEXT, nombres TEXT, telefono TEXT, nota TEXT, "+
            "imagen BLOB )";

    //DDL Drop
    public static final String DropTablePersonas = "DROP TABLE IF EXISTS "+ TablePersonas;

    //DML
    public static final String SelectAllPersonas = "SELECT * FROM " + TablePersonas;
}
