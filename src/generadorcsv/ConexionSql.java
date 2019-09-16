package generadorcsv;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;

public class ConexionSql {

    //VARIABLES CsvToTxT
    private String s = "";
    private ArrayList<DataTemplate> a = new ArrayList<DataTemplate>();
    private String sortCsvPath;
    private String csvFile = "/fichero.csv";
    private String csvFileAuto = "/fichero-Auto.csv";
    private BufferedReader br = null;
    private String line2 = "";
    private String cvsSplitBy = ";";

    //Variables DataTamplate
    private String id_empresa;
    private String empresa;
    private String customerNo;
    private String nombre;
    private String mail;
    private String docDate;
    private String dueDate;
    private String docNo;
    private String description;
    private String importe;
    private String saldo;

    private Connection conexion = null;
    private String servidor = "khcuy9chlt.database.windows.netsss";
    private String port = "1433.22";
    private String database = "ASERTECss";
    private String user = "tecnicooo";
    private String password = "Abc80585359gfd";
    private String url = "";
    private int num = 0;
    private String line = "";
    private String headers = "";
    private char simbolo = '&';
    String SQL = "SELECT * FROM dbo.T_CAL_REUNIONES";

    ArrayList<String[]> ar;
    File file;
    File fileB;
    String path = "C:/Users/usuario/desktop/informacion.csv";
    File file2;
    //cambiar para saber donde va la data de la query que guarda
    File aux2 = new File("");
    String basePath = aux2.getAbsolutePath();
    String path2 = basePath + "/MemoriaVentana.txt";
    //cambiar para saber donde va la configuracion de la consulta automatica
    String path3 = basePath + "/Auto-Query.txt";
    String path4 = basePath + "/GeneradorCSV CMD COMAND.bat";

    public ConexionSql() {
        //Conexion();

    }

    public void Conexion(String consulta, char simbolo, String server, String dataBasee, String path) {
        sortCsvPath = path;
        file = new File(path);
        //System.out.println("soy la path" + file.getAbsolutePath());
        //String ruta = file.getAbsolutePath();
        if (file.exists()) {
            //System.out.println("existo");
        } else {
            //System.out.println("no existo");
            try {
                file2 = new File(path);
            } catch (Exception e) {
                System.out.println("ruta no valida");
            }
        }
        //String connectionUrl = "jdbc:sqlserver://" + server + ":" + ports + ";databaseName=" + dataBasee + ";user=" + us + ";password=" + pass;
        String connectionUrl = "jdbc:sqlserver://" + server + ";databaseName=" + dataBasee + ";integratedSecurity = true";
        System.out.println(connectionUrl);
        SQL = consulta;
        try {
            Connection con = DriverManager.getConnection(connectionUrl);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(SQL);
            //obtain the structure of the query

            ResultSetMetaData rsmd = rs.getMetaData();

            String[] nmb = new String[rsmd.getColumnCount()];
            String[] headers = new String[rsmd.getColumnCount()];
            for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                headers[i - 1] = rsmd.getColumnName(i);
                //System.out.println(headers[i - 1]);
            }

            //headers
            //}
            try {

                FileWriter outputfile = new FileWriter(file);
                CSVWriter writer = new CSVWriter(outputfile, simbolo);

                writer.writeNext(headers);

                //System.out.println();
                while (rs.next()) {
                    //num++;
                    for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                        line = rs.getString(rsmd.getColumnName(i));
                        //System.out.print(line);
                        nmb[i - 1] = line;
                        //le pasamos una string que un no esta creada

                    }
                    writer.writeNext(nmb);

                    //System.out.println();
                }
                //escribe el fichero del escritorio
                //String writeTxt = d1 + "//s//" + d2 + "//s//" + d3 + "//s//" + d4 + "//s//" + d5 + "//s//" + d6 + "//s//" + d7 + "//s//" + d8;
                writer.close();
                con.close();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e);
            }

            System.out.println("He conectado");
            JOptionPane.showMessageDialog(null, "Csv exportado con éxito");
            /*try {
             Thread.sleep(4000);
             } catch (Exception e) {
             }*/
            generateTxtOfCsv(true);
            JOptionPane.showMessageDialog(null, "Csv ordenado");

        } catch (SQLServerException e3) {
            System.out.println(e3);

            JOptionPane.showMessageDialog(null, "Consulta no valida");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            //JOptionPane.showMessageDialog(null, "La conexion no va bien");
        }
    }

    public boolean conectado(String server, String dataBase) {
        String connectionUrl = "jdbc:sqlserver://" + server + ";databaseName=" + dataBase + ";integratedSecurity = true";
        System.out.println(connectionUrl);
        try {
            Connection con = DriverManager.getConnection(connectionUrl);
            //System.out.println("bien hecho");
            return true;
        } catch (Exception e) {
            //System.out.println("mal");
            return false;
        }
    }

    public String[] Reader(String path) throws FileNotFoundException {
        File file2 = new File(path2);
        Scanner sc = new Scanner(file2);

        // we just need to use //s// as delimiter 
        sc.useDelimiter("//s//");

        String d1 = (sc.next());
        String d2 = (sc.next());
        String d3 = (sc.next());
        String d4 = (sc.next());
        String d5 = (sc.next());

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
        System.out.println(d4);
        System.out.println(d5);

        String cadena[] = new String[5];
        cadena[0] = d1;
        cadena[1] = d2;
        cadena[2] = d3;
        cadena[3] = d4;
        cadena[4] = d5;

        //escribir en el archivo txt
        return (cadena);
    }

    public Connection getConexion() {
        return conexion;
    }

    public Connection cerrarConexion() {
        try {
            conexion.close();
            System.out.println("Cerrando conexion a " + url + " . . . . . Ok");
        } catch (SQLException ex) {
            System.out.println(ex);

        }
        conexion = null;
        return conexion;
    }

    //writer de recuperacion de datos
    public void writeTxtMemoria(String d1, String d2, String d3, String d4, String d5) {
        FileWriter fichero = null;
        try {

            fichero = new FileWriter(path2);

            // Escribimos linea a linea en el fichero
            String writeTxt
                    = d1 + "//s//" + d2 + "//s//" + d3 + "//s//" + d4 + "//s//"
                    + d5 + "//s//";
            fichero.write(writeTxt);

            fichero.close();

        } catch (Exception ex) {
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
        }
    }

    public void writeTxtAutoQuery(String d1, String d2, String d3, String d4, String d5) {
        FileWriter fichero = null;
        //d4 = Encripter(d4);
        try {

            fichero = new FileWriter(path3);

            // Escribimos linea a linea en el fichero
            String writeTxt
                    = d1 + "//s//" + d2 + "//s//" + d3 + "//s//" + d4 + "//s//"
                    + d5 + "//s//";
            fichero.write(writeTxt);

            fichero.close();
            JOptionPane.showMessageDialog(null, "Su consulta ha sido guardada para su ejecución automática");

        } catch (Exception ex) {
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
        }
        writeBatAutoQuery();
    }

    public void writeBatAutoQuery() {
        FileWriter fichero = null;
        //d4 = Encripter(d4);
        try {

            fichero = new FileWriter(path4);

            // Escribimos linea a linea en el fichero
            String writeTxt = "cd " + basePath + "\njava -jar GeneradorCSV.jar start";
            fichero.write(writeTxt);

            fichero.close();
            JOptionPane.showMessageDialog(null, "Se ha generado un BAT");

        } catch (Exception ex) {
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
        }
    }

    // APARTADO 2 - Extrae un CSV por si solo
    String server;
    //String ports;
    String dataBasee;
    String us;
    String pass;
    String ruta;
    String sQLQuery;
    char simbolo2;

    public void getData() throws FileNotFoundException {
        //archivo de configuración
        //cambiar para saber donde va
        File file2 = new File(path3);
        Scanner sc = new Scanner(file2);

        // usamos //s// como delimitador
        sc.useDelimiter("//s//");

        String d1 = (sc.next());
        String d2 = (sc.next());
        String d3 = (sc.next());
        String d4 = (sc.next());
        String d5 = (sc.next());


        /*System.out.println(d1);
         System.out.println(d2);
         System.out.println(d3);
         System.out.println(d4);
         System.out.println(d5);
         System.out.println(d6);
         System.out.println(d7);
         System.out.println(d8);*/
        char algo = d2.charAt(0);

        sQLQuery = d1;
        simbolo2 = algo;
        server = d3;
        //ports = d6;
        dataBasee = d4;
        path = d5;
        path = d5.substring(0, d5.length() - 4) + "-Auto.csv";
        //System.out.println(simbolo2);
    }

    public void Conexion2() throws FileNotFoundException {
        this.getData();

        file = new File(path);
        sortCsvPath = path;
        //comprobar si existe fichero
        //System.out.println("soy la path" + file.getAbsolutePath());
        //String ruta = file.getAbsolutePath();
        /*if (!file.exists()) {
         System.out.println("no hay archivo");
         } else {*/

        String connectionUrl = "jdbc:sqlserver://" + server + ";databaseName=" + dataBasee + ";integratedSecurity=true";
        System.out.println(connectionUrl);
        try {

            //connect with the DB
            Connection con = DriverManager.getConnection(connectionUrl);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sQLQuery);
            //obtain the structure of the query
            System.out.println("ok");
            ResultSetMetaData rsmd = rs.getMetaData();
            String[] nmb = new String[rsmd.getColumnCount()];
            String[] headers = new String[rsmd.getColumnCount()];
            for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                headers[i - 1] = rsmd.getColumnName(i);
            }

            try {
                FileWriter outputfile = new FileWriter(file);
                CSVWriter writer = new CSVWriter(outputfile, simbolo2);
                writer.writeNext(headers);
                while (rs.next()) {
                    //num++;
                    for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                        line = rs.getString(rsmd.getColumnName(i));
                        nmb[i - 1] = line;
                        //le pasamos una string que un no esta creada
                    }
                    writer.writeNext(nmb);
                }
                //escribe el fichero del escritorio
                writer.close();
                con.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println();
            System.out.println("Conexión realizada");
            //JOptionPane.showMessageDialog(null, "Csv exportado con éxito");
            System.out.println("fichero extraido con éxito");
            try {
                Thread.sleep(4000);
            } catch (Exception e) {
            }
            generateTxtOfCsv(false);
            System.out.println("Csv ordenado");
        } catch (SQLServerException e3) {

            //JOptionPane.showMessageDialog(null, "Consulta no valida");
            System.out.println("Parametros de fichero 'Auto-Query' incorrectos");
            System.out.println(e3);
        } catch (Exception e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "La conexion no va bien");
            System.out.println("La conexion no va bien");
        }
    }

    //Parte 3
    public static void generadorFichero() {
        String q2 = "C:\\GeneradorCSV\\Auto-Query.txt";
        FileWriter fichero = null;
        PrintWriter pw = null;
        String autoQuery = "query_string//s//valor_de_separacion//s//user_name//s//pass_encryp//s//server_name//s//puerto//s//database_name//s//directorio_guardar_CSV//s//";
        try {
            fichero = new FileWriter(q2);
            pw = new PrintWriter(fichero);
            pw.println(autoQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void generateTxtOfCsv(Boolean aux) {
        deleteTxt();
        readCsv(aux);
        while (a.size() > 0) {
            sortList();
            System.out.println(s);
            s = s + "\n";
            writeTxtOfCsv(s, aux);
        }
    }

    public void sortList() {
        int r = 0;
        s = "";
        s = s + a.get(r).toString();
        String num = a.get(r).getCustomerNo();
        a.remove(r);
        for (int z = 0; z < a.size(); z++) {
            if (num.equals(a.get(z).getCustomerNo())) {
                s = s + a.get(z).toString2();
                a.remove(z);
                z--;
            }
        }
    }

    public void readCsv(Boolean aux) {
        try {
            //br = new BufferedReader(new FileReader(csvFile));
            //System.out.println("SOY readCsv line 455 "+sortCsvPath);
            if (aux) {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(sortCsvPath.substring(0, sortCsvPath.length() - 12) + csvFile), "ISO-8859-1"));
            } else {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(sortCsvPath.substring(0, sortCsvPath.length() - 17) + csvFileAuto), "ISO-8859-1"));

            }
            br.readLine();
            while ((line2 = br.readLine()) != null) {
                saveData();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeTxtOfCsv(String data, Boolean aux) {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            //System.out.println("SOY writeTxtOfCsv line 480 "+sortCsvPath);
            if (aux) {
                System.out.println("writeCsv " + sortCsvPath.substring(0, sortCsvPath.length() - 12) + "/Fichero_Ordenado.csv");
                fileB = new File(sortCsvPath.substring(0, sortCsvPath.length() - 12) + "/Fichero_Ordenado.csv");
            } else {
                fileB = new File(sortCsvPath.substring(0, sortCsvPath.length() - 17) + "/Fichero_Ordenado.csv");
            }

            // Si el archivo no existe, se crea!
            if (!fileB.exists()) {
                fileB.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(fileB.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(data);
            System.out.println("información agregada!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void saveData() {
        String[] datos = line2.split(cvsSplitBy);
        id_empresa = datos[0];
        empresa = datos[1];
        customerNo = datos[2];
        nombre = datos[3];
        mail = datos[4];
        docDate = datos[5];
        dueDate = datos[6];
        docNo = datos[7];
        description = datos[8];
        importe = datos[9];
        saldo = datos[10];
        DataTemplate dt = new DataTemplate(id_empresa, empresa, customerNo, nombre, mail, docDate, dueDate, docNo, description, importe, saldo);
        a.add(dt);
    }

    private void deleteTxt() {

        System.out.println(sortCsvPath.substring(0, sortCsvPath.length() - 12) + "/Fichero_Ordenado.csv");
        File file = new File(sortCsvPath.substring(0, sortCsvPath.length() - 12) + "/Fichero_Ordenado.csv");

        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
    }

}
