/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;
import static spark.Spark.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import spark.ModelAndView;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import spark.Filter;
import spark.Spark;
//import spark.template.handlebars.HandlebarsTemplateEngine;

public class Main {

    private static final String SESSION_NAME = "username";
    private static SessionFactory factory;

    public static void main(String[] args) {
//            port(4567);
        staticFiles.location("/public");

        Spark.exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });
        
                after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
        });


        try {
            factory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

//        get("/ver", (req, res) -> {
//            String id = req.queryParams("id");
//
//            Session session = factory.openSession();
//            Transaction tx = null;
//
//            StringBuilder ret = new StringBuilder();
//
//            try {
//                Cliente cli = (Cliente) session.get(Cliente.class, Integer.parseInt(id));
//                ret.append("Nombre:<b>"+cli.getNombre()+"</b><br>");
//                ret.append("Direccion:<b>"+cli.getDireccion()+"</b><br>");
//
//            } catch (HibernateException e) {
//               
//                e.printStackTrace();
//                return e.toString();
//            } finally {
//                session.close();
//            }
//            return ret.toString();
//        });

         get("/actualizar", (req, res) -> {
            String id = req.queryParams("id");

            Session session = factory.openSession();
            Transaction tx = null;

            StringBuilder ret = new StringBuilder();

            try {
                 tx = session.beginTransaction();
                Cliente cliente = (Cliente) session.get(Cliente.class, Integer.parseInt(id));
                Random r = new Random();
		
                String telef="809"+(r.nextInt((9999999 - 1000000) + 1) + 1000000);
                
                cliente.setTelefono(telef);
                 session.update(cliente); 
                tx.commit();
                ret.append("Nuevo telefono: "+telef);

            } catch (HibernateException e) {
               if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
                return e.toString();
            } finally {
                session.close();
            }
            return ret.toString();
        });
         
        get("/listar", (req, res) -> {

            Session session = factory.openSession();
            Transaction tx = null;

            StringBuilder ret = new StringBuilder();

            ret.append("<table><tr><th>ID</th><th>Nombre</th></tr>");
            try {
                tx = session.beginTransaction();
                List clientes = session.createQuery("FROM Cliente").list();

                for (Iterator iterator = clientes.iterator(); iterator.hasNext();) {
                    Cliente cliente = (Cliente) iterator.next();
                    ret.append("<tr><td>" + cliente.getIDcliente() + "</td>");
                    ret.append("<td>" + cliente.getNombre() + "</td>");  
                    ret.append("<td>" + "<a href=/actualizar?id="+cliente.getIDcliente()+">Actualizar</a>" + "</td></tr>");
                }
                ret.append("</table>");
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
            return ret.toString();
        });
        
//            get("/form", (req, res)->{
//                StringBuilder ret = new StringBuilder();
//                ret.append("<form action='/agregar' method='post'>");
//                ret.append("Nombre:<br>"+"<input type='text' name='nombre'>"
//
//                +"Telefono:<br>"
//                +"<input type=\"text\" name=\"telefono\" >" 
//                +"<br><br>"
//                +"Direccion:<br>"
//                +"<input type=\"text\" name=\"direccion\">" 
//                +"<br><br>"
//                +"Estado:<br>"	
//                +"<input type=\"text\" name=\"estado\">" 
//                +"<br><br>"
//                +"<input type=\"submit\" value=\"Registrar\">" 
//                +"</form>" );
//                      
//                return ret.toString();
//            });
                      
        post("/agregar", (req, res) -> {
        
        String Nombre = req.queryParams("Nombre");
        String Telefono = req.queryParams("Telefono");
        String Direccion = req.queryParams("Direccion");
        
        int Estado = Integer.parseInt(req.queryParams("Estado"));
        
            String insertID = "";
            Session session = factory.openSession();
            Transaction tx = null;

            try {
                tx = session.beginTransaction();
                Cliente cliente = new Cliente(Nombre, Telefono, Direccion, Estado);
                insertID = session.save(cliente).toString();
                tx.commit();

            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
            return new Gson().toJson("Agregado");
        });
        


        get("/", (req, res) -> {
            String name = req.session().attribute(SESSION_NAME);

            if (name == null) {
                String html = "<html><body>";
                if (req.queryParams("err") != null) {
                    html += "Error login";
                }
                html += "<form action=\"/login\" "
                        + "method=\"POST\"> Usuario:<input type=\"text\" name=\"user\"/>"
                        + "<br />Password<input type=\"passowrd\" name=\"pass\" /><input type=\"submit\" value=\"go\"/></form></body></html>";
                return html;
            } else {
                return String.format("<html><body>Hello, %s!</body></html>", name);
            }
        });

        post("/login", (request, response) -> {
            String user = request.queryParams("user");
            String pass = request.queryParams("pass");
            if (user.toLowerCase().equals("admin") && pass.equals("123456")) {
                request.session().attribute(SESSION_NAME, user);
                response.redirect("/");
            } else {
                response.redirect("/?err=1");
            }
            return null;
        });

        get("/clear", (request, response) -> {
            request.session().removeAttribute(SESSION_NAME);
            response.redirect("/");
            return null;
        });
        

        post("/reservar", (req, res)->{
        
        int IDcliente = Integer.parseInt(req.params("IDcliente"));
        String fecha_llegada = req.params("fecha_llegada");
        String fecha_salida = req.params("fecha_salida");
        
        
            String insertID = "";
            Session session = factory.openSession();
            Transaction tx = null;

            try {
                tx = session.beginTransaction();
                Reservacion reserva = new Reservacion(IDcliente, fecha_llegada, fecha_salida);
                insertID = session.save(reserva).toString();
                tx.commit();

            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
            return new Gson().toJson("id");
        });
        
        
        get("/ver/:id", (req, res) -> {
            String id = req.params("id");

            Session session = factory.openSession();
            Transaction tx = null;

            StringBuilder ret = new StringBuilder();

            try {
                Cliente cli = (Cliente) session.get(Cliente.class, Integer.parseInt(id));
              return new Gson().toJson(cli);
             
              
            } catch (HibernateException e) {
              
                e.printStackTrace();
                return e.toString();
            } finally {
                session.close();
            }
         
        });
        
    }
}
