/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbytegt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;

import java.math.BigInteger;

/**
 *
 * @author KByteGt
 */
public class usac_lib {
    //RESPUESTA
    static final int REQUEST_OK = 200;
    static final int REQUEST_CREATED = 201;
    static final int REQUEST_ACEPTED = 202;
    static final int REQUEST_UPDATED = 203;
    static final int REQUEST_DELETED = 204;
    static final int REQUEST_NO_CONTENT = 205;
    //ERROR
    static final int REQUEST_ERROR = 400;
    static final int REQUEST_UNAUTHORIZED = 401;
    static final int REQUEST_NOT_FOUND = 404;
    static final int REQUEST_TIMEOUT = 408;
    //SERVIDOR
    static final int REQUEST_SERVER_ERROR = 500;
    static final int REQUEST_AUTHENTICATION_REQUIRED = 511;
    
    /////////////////////////////////////////////////////////
    static String carpeta = "";
    static String ip = "";
    static int puerto = 0;
    /////////////////////////////////////////////////////////
    static Gson json;
    static Security security;
    static TablaHash usuarios;
    static ArbolB categoria;
    static ArbolAVL biblioteca;
    static UIlogin ui_login = new UIlogin();
    static UIlibrary ui_library = new UIlibrary();
    
    static boolean isLogin = false;
    static int carnetLogin;
    static String nombreLogin;
    static String apellidoLogin;
    static String carreraLogin;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // TODO code application logic here
        //json = new GsonBuilder().serializeNulls().create();
        try {
             usuarios = new TablaHash(45);
            biblioteca = new ArbolAVL();

            System.out.println("Preparando pruebas");
            test();

            //Cargar el LogIn
            try {
                System.out.println("Abriendo ventana LogIn");
                ui_login.setVisible(!isLogin);
            } catch (Exception e) {
                System.out.println("Error al abrir la ventana UIlogin");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error:\n"+e.getMessage());
        }
       
//        
        

    }    
    public static void LogIn(){
        //Inicio de sesión exitosa
        System.out.println("Usuario "+carnetLogin+" conectado...");
        isLogin = true;
        ui_login.setVisible(!isLogin);
        ui_library.setVisible(isLogin);
        ui_library.setMenuCarnet("["+Integer.toString(carnetLogin)+"] "+apellidoLogin+" "+nombreLogin);
        if(biblioteca.getRaiz() != null){
            ui_library.actualizarJTree(biblioteca.getListaCategorias());
        }
        //ui_library.actualizarJTable(null);
        
    }
    
    public static void LogOut(){
        System.out.println("Saliendo de la biblioteca virtual");
        isLogin = false;
        carnetLogin = 0;
        nombreLogin = "";
        apellidoLogin = "";
        carreraLogin = "";
        ui_library.setVisible(isLogin);
        ui_login.limpiar();
        ui_login.setVisible(!isLogin);
    }
    
    public static void reLogIn(){
        //Inicio de sesión exitosa
//        System.out.println("Usuario "+carnetLogin+" conectado...");
//        isLogin = true;
//        ui_login.setVisible(!isLogin);
//        ui_library.setVisible(isLogin);
        NodoUsuario temp = usuarios.buscar(carnetLogin);
        ui_library.setMenuCarnet("["+Integer.toString(carnetLogin)+"] "+temp.getApellido()+" "+temp.getNombre());
//        if(biblioteca.getRaiz() != null){
//            ui_library.actualizarJTree(biblioteca.getListaCategorias());
//        }
    }
    
    public static ListaCategorias getCategorias(){
        return biblioteca.getListaCategorias();
    }
    
    public static boolean ingresarLibros(String txt){
        try {
            System.out.println(" - Cargando libros...");
            JsonParser parser = new JsonParser();
            JsonObject json_libros = parser.parse(txt).getAsJsonObject();
            
            JsonArray lib_array = json_libros.getAsJsonArray("Libros").getAsJsonArray();
            for(JsonElement lib : lib_array){
                JsonObject lib_obj = lib.getAsJsonObject();
                String isbn = lib_obj.get("ISBN").getAsString();
                String titulo = lib_obj.get("Titulo").getAsString();
                String autor = lib_obj.get("Autor").getAsString();
                String editorial = lib_obj.get("Editorial").getAsString();
                int anio = lib_obj.get("Año").getAsInt();
                String edicion = lib_obj.get("Edicion").getAsString();
                String cat = lib_obj.get("Categoria").getAsString();
                String idioma = lib_obj.get("Idioma").getAsString();
                
                Libro libro = new Libro(new BigInteger(isbn),titulo,autor,editorial,anio,edicion,cat,idioma,carnetLogin);
                biblioteca.insertarLibro(libro);
                
                System.out.println("["+isbn+"] "+titulo+" - "+cat);
            }
            ui_library.actualizarJTree(biblioteca.getListaCategorias());
            System.out.println("Recorrido del árbol AVL");
            biblioteca.recorrer();
            return true;
        } catch (JsonSyntaxException e) {
            JOptionPane.showMessageDialog(null, "Error:\n"+e.getMessage());
            System.err.println(" Problemas al cargar los libros...");
            return false;
        }
    }
    
    public static boolean registrarLibro(Libro libro){
        try {
            biblioteca.insertarLibro(libro);
            ui_library.actualizarJTree(biblioteca.getListaCategorias());
             System.out.println("Recorrido del árbol AVL");
            biblioteca.recorrer();
            return true;
        } catch (Exception e) {
            return false;
        }
       
    }
    public static boolean ingresarUsuarios(String txt){
        try {
            System.out.println(" - Cargando usuarios...");
            JsonParser parser = new JsonParser();
            JsonObject json_usuarios = parser.parse(txt).getAsJsonObject();

            JsonArray user_array = json_usuarios.getAsJsonArray("Usuarios").getAsJsonArray();
            for(JsonElement user : user_array){
                JsonObject user_obj = user.getAsJsonObject();
                int carnet = user_obj.get("Carnet").getAsInt();
                String nombre = user_obj.get("Nombre").getAsString();
                String apellido = user_obj.get("Apellido").getAsString();
                String carrera = user_obj.get("Carrera").getAsString();
                String password = user_obj.get("Password").getAsString();

                NodoUsuario usuario = new NodoUsuario(carnet,nombre,apellido,carrera,security.getMD5(password));
                usuarios.insertar(usuario);
                
                System.out.println("["+carnet+"] "+nombre+" "+apellido+" - "+carrera+" > "+password);
            }  
            
            System.out.println("Fin de carga de usuarios...");
            return true;
        } catch (JsonSyntaxException e) {
            JOptionPane.showMessageDialog(null, "Error:\n"+e.getMessage());
            System.err.println(" Problema al cargar los usuarios...");
            return false;
        }
                
    }
    
    public static boolean registrarUsuario(NodoUsuario usuario){
        int respuesta = usuarios.insertar(usuario);
        return respuesta == REQUEST_OK;
    }
            
    public static String getMessage(int request, String txt){
        String r = "";
        switch(request){
            case REQUEST_OK:
                JOptionPane.showMessageDialog(null, "Operación realizada con éxito:\n"+txt);
                break;
            case REQUEST_CREATED:
                JOptionPane.showMessageDialog(null, "Operación creada con éxito:\n"+txt);
                break;
            case REQUEST_ACEPTED:
                JOptionPane.showMessageDialog(null, "Operación aceptada con éxito:\n"+txt);
                break;
            case REQUEST_UPDATED:
                JOptionPane.showMessageDialog(null, "Operación actualizada con éxito:\n"+txt);
                break;
            case REQUEST_DELETED:
                JOptionPane.showMessageDialog(null, "Operación eliminada con éxito:\n"+txt);
                break;
            case REQUEST_NO_CONTENT:
                JOptionPane.showMessageDialog(null, "No se pudo realizar la operación:\n"+txt);
                break;
                
            case REQUEST_ERROR:
                JOptionPane.showMessageDialog(null, "Error al efecutar:\n"+txt);
                break;
            case REQUEST_UNAUTHORIZED:
                JOptionPane.showMessageDialog(null, "Se necesita autorización para:\n"+txt);
                break;
            case REQUEST_NOT_FOUND:
                JOptionPane.showMessageDialog(null, "No se puedo realizar la operación:\n"+txt);
                break;
            case REQUEST_TIMEOUT:
                JOptionPane.showMessageDialog(null, "Tiempo de espera agotado:\n"+txt);
                break;
                
            case REQUEST_SERVER_ERROR:
                JOptionPane.showMessageDialog(null, "Error interno del servidor:\n"+txt);
                break;
            case REQUEST_AUTHENTICATION_REQUIRED:
                JOptionPane.showMessageDialog(null, "Se necesitan permisos para realizar la operación:\n"+txt);
                break;
            default:
                break;
        }
        return r;
    }
    
    public static void test(){
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        String json = gson.toJson(new NodoUsuario(0,"","","",""));
//        System.out.println(json);
        
            
        NodoUsuario u1 = new NodoUsuario(201503836,"Daniel","Lopez","Ciencias y Sistemas",security.getMD5("123456"));
//        NodoUsuario u2 = new NodoUsuario(201503476,"Ricardo","Cutz","Ingenieria en istemas",security.getMD5("12b456"));
//        NodoUsuario u3 = new NodoUsuario(201503477,"Juanito","Hernandez","Ingenieria Civil",security.getMD5("abf34543"));
//        NodoUsuario u4 = new NodoUsuario(202005878,"Antonio","Hernandez","Ingenieria Electrica",security.getMD5("asdfghjkl"));
//        NodoUsuario u5 = new NodoUsuario(201003866,"Pedrito","Lopez","Ciencias y Sistemas",security.getMD5("eewe3434"));
//        NodoUsuario u6 = new NodoUsuario(201103336,"Miguel","Lopez","Ciencias y Sistemas",security.getMD5("1234"));
//        NodoUsuario u7 = new NodoUsuario(109803834,"Juan","Lopez","Ciencias y Sistemas",security.getMD5("juan123"));
//        NodoUsuario u8 = new NodoUsuario(201504876,"Juan","Lemus","Ciencias y Sistemas",security.getMD5("1sss3356"));
//        
        usuarios.insertar(u1);
//        usuarios.insertar(u2);
//        usuarios.insertar(u3);
//        usuarios.insertar(u4);
//        usuarios.insertar(u5);
//        usuarios.insertar(u6);
//        usuarios.insertar(u7);
//        usuarios.insertar(u8);
        
        
//        NodoUsuario temp;
//        temp = usuarios.buscar(201503836);
//        if(temp != null){
//            String json_txt = json.toJson(temp);
//            System.out.println(json_txt);
//        } else {
//            
//            System.out.println("No existe el usuario:");
//        }
//        
//        NodoUsuario u9 = new NodoUsuario(201503836,"Daniel","Lopez","Ciencias y Sistemas",security.getMD5("5246"));
//        usuarios.insertar(u9);
//        
//        temp = usuarios.buscar(201503836);
//        if(temp != null){
//            //System.out.println(temp.getJSON());
//            //Obtene Json a parter de un objeto
//            String json_txt = json.toJson(temp);
//            System.out.println(json_txt);
//        } else {
//            System.out.println("No existe el usuario:");
//        }
//        
//        int request = usuarios.eliminar(109803834);
//        //getMessage(request,"Usuario "+ 109803834);
//
//        
//        temp = usuarios.buscar(109803834);
//        if(temp != null){
//            //System.out.println(temp.getJSON());
//            //Obtene Json a parter de un objeto
//            String json_txt = json.toJson(temp);
//            System.out.println(json_txt);
//        } else {
//            System.out.println("No existe el usuario:");
//        }
 
//        System.out.println("");
//
//        String g = usuarios.getGraphviz("TABLA HASH");
//        System.out.println(g);


//        //ARBOL B
//        categoria = new ArbolB(2);
//        
//        Libro lib1 = new Libro(new BigInteger("6"),"Curso de derecho constitucional","ESCOBAR, DAVID","3M España",1891,"2","Consulta","Español",201503836);
//        Libro lib2 = new Libro(new BigInteger("11"),"Canales de comercialización","ESCOBAR, FRANCISCO","3R EDITORES",1999,"1","Consulta","Ingles",201503836);
//        Libro lib3 = new Libro(new BigInteger("5"),"Planeación estratégica aplicada","ESCOBAR, FRANCISCO ANDRÉS","3M España",1895,"2","Investigacion","Español",201503836);
//        Libro lib4 = new Libro(new BigInteger("4"),"A New Directions Book","ESCOBAR, JORGE R","3M España",1991,"2","Otros","Español",201503836);
//        Libro lib5 = new Libro(new BigInteger("8"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Fantasia","Español",201503836);
//        Libro lib6 = new Libro(new BigInteger("9"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Fantasia","Español",201503836);
//        Libro lib7 = new Libro(new BigInteger("12"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        Libro lib8 = new Libro(new BigInteger("21"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        Libro lib9 = new Libro(new BigInteger("74"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        Libro lib10 = new Libro(new BigInteger("75"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        Libro lib11 = new Libro(new BigInteger("76"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        Libro lib12 = new Libro(new BigInteger("77"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        Libro lib13 = new Libro(new BigInteger("78"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        Libro lib14 = new Libro(new BigInteger("79"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        Libro lib15 = new Libro(new BigInteger("13"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        Libro lib16 = new Libro(new BigInteger("22"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        Libro lib17 = new Libro(new BigInteger("32"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        Libro lib18 = new Libro(new BigInteger("49"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        Libro lib19 = new Libro(new BigInteger("52"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        Libro lib20 = new Libro(new BigInteger("62"),"A Plume Book","ESCOBEDO ARIAS DE TORALLA, LAURA LUCRECIA","3M España",1997,"2","Consulta","Español",201503836);
//        
//         
//        
//        categoria.insertar(lib1);
//        categoria.insertar(lib2);
//        categoria.insertar(lib3);
//        categoria.insertar(lib4);
//        categoria.insertar(lib5);
//        categoria.insertar(lib6);
//        categoria.insertar(lib7);
//        categoria.insertar(lib8);
//        categoria.insertar(lib9);
//        categoria.insertar(lib10);
//        categoria.insertar(lib11);
//        categoria.insertar(lib12);
//        categoria.insertar(lib13);
//        categoria.insertar(lib14);
//        categoria.insertar(lib15);
//        categoria.insertar(lib16);
//        categoria.insertar(lib17);
//        categoria.insertar(lib18);
//        categoria.insertar(lib19);
//        categoria.insertar(lib20);
//        
//        categoria.recorrer();
//        
//        //Eliminar libros
////        
//        categoria.eliminar(new BigInteger("12"));
//        categoria.recorrer();
////        
////        categoria.eliminar(new BigInteger("1"));
////        categoria.recorrer();
//        
//        System.out.println(categoria.getGraphviz("x"));
        
        //categoria.buscarISBN(new BigInteger("10765"));
        
        
        //ARBOL AVL
//        biblioteca = new ArbolAVL();
       
//        biblioteca.insertar("Historia", 201503836);
//////        biblioteca.setRaiz(biblioteca.insertar("Historia", new BigInteger("201503836")));
//        biblioteca.insertar("Ciencia", 201503836);
//        biblioteca.insertar("Biologia", 201503836);
//        biblioteca.insertar("Infantil", 201503836);
//        biblioteca.insertar("Religion", 201503836);
//        biblioteca.insertar("Consulta", 201503836);
//        System.out.println("\nArbol ABL:");
//        biblioteca.recorrer();
        
//        biblioteca.setRaiz(biblioteca.eliminar("Historia", new BigInteger("2222")));
//        System.out.println("\nArbol ABL:");
//        biblioteca.recorrer(biblioteca.getRaiz());
//        
//        biblioteca.setRaiz(biblioteca.eliminar("Historia", new BigInteger("201503836")));
//        System.out.println("\nArbol ABL:");
//        biblioteca.recorrer(biblioteca.getRaiz());
        
//        NodoCategoria temp;
//        temp = biblioteca.buscar("Religion");
//        if(temp != null){
//            System.out.println(temp.getNombre()+ " - "+temp.getUsuario());
////            if(temp.getNombre().compareTo("Religion") == 0){
////                System.out.println(temp.getNombre()+ " - "+temp.getUsuario());
////            } else {
////                System.out.println("Categoria no encontrada");
////            }
//            
//        } else {
//            System.out.println("No se encontro la categoria");
//        }
//        
//        temp = biblioteca.buscar("Historia");
//        if(temp != null){
//            System.out.println(temp.getNombre()+ " - "+temp.getUsuario());
////            if(temp.getNombre().compareTo("Religion") == 0){
////                System.out.println(temp.getNombre()+ " - "+temp.getUsuario());
////            } else {
////                System.out.println("Categoria no encontrada");
////            }
//            
//        } else {
//            System.out.println("No se encontro la categoria");
//        }

//          ListaCategorias lista = biblioteca.getListaCategorias();
//          NodoLC temp = lista.getInicio();
//          System.out.println(" - Imprimir Lista de categorias de Árbol AVL");
//          while(temp.getSiguiente() != null){
//              System.out.println(temp.getCategoria());
//              temp = temp.getSiguiente();
//          }
//          System.out.println(temp.getCategoria());

//            ArbolB tempTree;
//            ListaLibros listal = null;
//
//            if(biblioteca.existe("Ciencia")){
//                System.out.println("Categoria Ciencia existe");
//                
//                tempTree = biblioteca.buscar("Ciencia").getLibros();
//                
//                listal = tempTree.getListaLibros();
//                tempTree.recorrer();
//
//            } else {
//                System.out.println("No existe categoria Ciencia");
//            }
//                       
//            
//            //biblioteca.buscar("Ciencia").recorrer();
//            if(listal != null){
//                System.out.println(" total en lista: "+listal.getContador());
//                NodoLL temp = listal.getInicio();
//                while(temp.getSiguiente() != null){
//                    System.out.println(" -> "+temp.getLibro().getTitulo());
//                    temp = temp.getSiguiente();
//                }
//                System.out.println(" -> "+temp.getLibro().getTitulo());
//            }

//            //Prueba de inreso de libros por medio de AVL
//            biblioteca.insertarLibro(lib1);
//            biblioteca.insertarLibro(lib2);
//            biblioteca.insertarLibro(lib3);
//            biblioteca.insertarLibro(lib4);
//            biblioteca.insertarLibro(lib5);
//            biblioteca.insertarLibro(lib6);
//            
//            biblioteca.recorrer();
//            
//            ArbolB tempTree;
//            NodoCategoria temp;
//            
//            System.out.println(" Buscando Ciencia");
//            temp = biblioteca.buscar("Ciencia");
//            if(temp != null)
//                temp.recorrer();
//            
//            System.out.println(" Buscando Consulta");
//            temp = biblioteca.buscar("Consulta");
//            if(temp != null)
//                temp.recorrer();
            
    }
}
