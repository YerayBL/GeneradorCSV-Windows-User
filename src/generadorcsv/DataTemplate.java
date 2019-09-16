/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadorcsv;

/**
 *
 * @author usuario
 */
public class DataTemplate {

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

    public DataTemplate(String id_empresa, String empresa, String customerNo, String nombre, String mail, String docDate, String dueDate, String docNo, String description, String importe, String saldo) {
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.customerNo = customerNo;
        this.nombre = nombre;
        this.mail = mail;
        this.docDate = docDate;
        this.dueDate = dueDate;
        this.docNo = docNo;
        this.description = description;
        this.importe = importe;
        this.saldo = saldo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    @Override
    public String toString() {
        return id_empresa + ";" + empresa + ";" + customerNo + ";" + nombre + ";" + mail + ";" + saldo + ";" + docDate + ";" + dueDate + ";" + docNo + ";" + description + ";" + importe;
    }

    public String toString2() {
        return ";" + docDate + ";" + dueDate + ";" + docNo + ";" + description + ";" + importe;
        //+ ";" + saldo
    }
}
