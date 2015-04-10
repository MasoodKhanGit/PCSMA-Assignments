/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Masood
 */
@WebServlet(urlPatterns = {"/myServlet"})
public class myServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String videoName = "Gladiator";
    String type;
    String length;
    String size;
    String availability;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
//        x = request.getParameter("sn");
        videoName = request.getParameter("videoName");
        type = request.getParameter("type");
        length = request.getParameter("length");
        size = request.getParameter("size");
        availability = request.getParameter("availability");

        Enumeration<String> en = request.getAttributeNames();
        int count = 0;
        while (en.hasMoreElements()) {
            en.nextElement();
            count++;
        }

        response.setStatus(200);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
//            out.println("Varible: " + x);
            out.println("VideoName: " + videoName);
            out.println("Type: " + type);
            out.println("Length: " + length);
            out.println("Size: " + size);
            out.println("Availability: " + availability);
            out.println("Count: " + count);
            out.println("en: " + en);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String x = request.getParameter("q");
        PrintWriter out = response.getWriter();
        if (x.equalsIgnoreCase("videoName")) {
            out.write("videoName: " + videoName); // here default status is 200
        } else {
            response.setStatus(400);
            out.write("Bad Request");
        }
        out.close();
//        processRequest(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        videoName = request.getParameter("videoName");
        type = request.getParameter("type");
        length = request.getParameter("length");
        size = request.getParameter("size");
        availability = request.getParameter("availability");

        
        PrintWriter out = response.getWriter();
        out.println("*** Successfully Created ***");
        out.println("VideoName: " + videoName);
        out.println("Type: " + type);
        out.println("Length: " + length);
        out.println("Size: " + size);
        out.println("Availability: " + availability);
        
        response.setStatus(201);
        
        out.close();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        videoName = request.getParameter("videoName");
//        videoName = "abc";
        PrintWriter out = response.getWriter();
        if (!videoName.equals(null)) {
            out.write("**** Video Name Updated ****");
            out.write("videoName: " + videoName); // here default status is 200
        } else {
            response.setStatus(404);
            out.write("Not Found");
        }
        out.close();
        
        
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        
        response.setContentType("text/html;charset=UTF-8");
        String x = request.getParameter("q");
        PrintWriter out = response.getWriter();
        if (x.equalsIgnoreCase("videoName")) {
            videoName = null;
            out.write("*** videoName Deleted ***"); // here default status is 200
            
        } else {
            response.setStatus(404);
            out.write("Not Found");
        }
        out.close();
    }

}
