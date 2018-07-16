/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package controller;

import cart.ShoppingCart;
import entity.Make;
import entity.Car;
import java.io.IOException;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import session.MakeFacade;
import session.OrderManager;
import session.CarFacade;
import validate.Validator;

/**
 *
 * @author tgiunipero
 */
@WebServlet(name = "Controller",
            loadOnStartup = 1,
            urlPatterns = {"/make",
                           "/addToCart",
                           "/viewCart",
                           "/updateCart",
                           "/checkout",
                           "/purchase",
                           "/chooseLanguage"})
public class ControllerServlet extends HttpServlet {

    private String surcharge;

    @EJB
    private MakeFacade makeFacade;
    @EJB
    private CarFacade carFacade;
    @EJB
    private OrderManager orderManager;


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        // initialize servlet with configuration information
        surcharge = servletConfig.getServletContext().getInitParameter("deliverySurcharge");

        // store make list in servlet context
        getServletContext().setAttribute("makes", makeFacade.findAll());
    }


    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        Make selectedMake;
        Collection<Car> makeCars;


        // if make page is requested
        switch (userPath) {
            case "/make":
                // get categoryId from request
                String categoryId = request.getQueryString();
                if (categoryId != null) {
                    
                    // get selected make
                    selectedMake = makeFacade.find(Short.parseShort(categoryId));
                    
                    // place selected make in session scope
                    session.setAttribute("selectedMake", selectedMake);
                    
                    // get all makes for selected make
                    makeCars = selectedMake.getCarCollection();
                    
                    // place make makes in session scope
                    session.setAttribute("makeCars", makeCars);
                }
                
                
                // if cart page is requested
                break;
            case "/viewCart":
                String clear = request.getParameter("clear");
                if ((clear != null) && clear.equals("true")) {
                    
                    ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                    cart.clear();
                }   userPath = "/cart";
                
                
                // if checkout page is requested
                break;
            case "/checkout":
                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                // calculate total
                cart.calculateTotal(surcharge);
                
                // forward to checkout page and switch to a secure channel
                
                
                // if user switches language
                break;
            case "/chooseLanguage":
                // get language choice
                String language = request.getParameter("language");
                // place in request scope
                request.setAttribute("language", language);
                String userView = (String) session.getAttribute("view");
                if ((userView != null) &&
                        (!userView.equals("/index"))) {     // index.jsp exists outside 'view' folder
                    // so must be forwarded separately
                    userPath = userView;
                } else {
                    
                    // if previous view is index or cannot be determined, send user to welcome page
                    try {
                        request.getRequestDispatcher("/index.jsp").forward(request, response);
                    } catch (IOException | ServletException ex) {
                    }
                    return;
                }   break;
            default:
                break;
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (IOException | ServletException ex) {
        }
    }


    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");  // ensures that user input is interpreted as
                                                // 8-bit Unicode (e.g., for Czech characters)

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        Validator validator = new Validator();


        // if addToCart action is called
        switch (userPath) {
            case "/addToCart":
                {
                    // if user is adding item to cart for first time
                    // create cart object and attach it to user session
                    if (cart == null) {
                        
                        cart = new ShoppingCart();
                        session.setAttribute("cart", cart);
                    }       // get user input from request
                    String carId = request.getParameter("carId");
                    if (!carId.isEmpty()) {
                        
                        Car car = carFacade.find(Integer.parseInt(carId));
                        cart.addItem(car);
                    }       userPath = "/make";
                    
                    
                    // if updateCart action is called
                    break;
                }
            case "/updateCart":
                {
                    // get input from request
                    String carId = request.getParameter("carId");
                    String quantity = request.getParameter("quantity");
                    boolean invalidEntry = validator.validateQuantity(carId, quantity);
                    if (!invalidEntry) {
                        
                        Car car = carFacade.find(Integer.parseInt(carId));
                        cart.update(car, quantity);
                    }       userPath = "/cart";
                    
                    
                    // if purchase action is called
                    break;
                }
            case "/purchase":
                if (cart != null) {
                    
                    // extract user data from request
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    String phone = request.getParameter("phone");
                    String address = request.getParameter("address");
                    String city = request.getParameter("city");
                    String state = request.getParameter("state");
                    String ccNumber = request.getParameter("creditcard");
                    
                    // validate user data
                    boolean validationErrorFlag = false;
                    validationErrorFlag = validator.validateForm(name, email, phone, address, city, state, ccNumber, request);
                    
                    // if validation error found, return user to checkout
                    if (validationErrorFlag == true) {
                        request.setAttribute("validationErrorFlag", validationErrorFlag);
                        userPath = "/checkout";

                        // otherwise, save order to database
                    } else {
                        
                        int orderId = orderManager.placeOrder(name, email, phone, address, city, state, ccNumber, cart);
                        
                        // if order processed successfully send user to confirmation page
                        if (orderId != 0) {
                            
                            // in case language was set using toggle, get language choice before destroying session
                            Locale locale = (Locale) session.getAttribute("javax.servlet.jsp.jstl.fmt.locale.session");
                            String language = "";
                            
                            if (locale != null) {
                                
                                language = (String) locale.getLanguage();
                            }
                            
                            // dissociate shopping cart from session
                            cart = null;
                            
                            // end session
                            session.invalidate();
                            
                            if (!language.isEmpty()) {                       // if user changed language using the toggle,
                                // reset the language attribute - otherwise
                                request.setAttribute("language", language);  // language will be switched on confirmation page!
                            }
                            
                            // get order details
                            Map orderMap = orderManager.getOrderDetails(orderId);
                            
                            // place order details in request scope
                            request.setAttribute("customer", orderMap.get("customer"));
                            request.setAttribute("cars", orderMap.get("cars"));
                            request.setAttribute("orderRecord", orderMap.get("orderRecord"));
                            request.setAttribute("orderedProducts", orderMap.get("orderedProducts"));
                            
                            userPath = "/confirmation";
                            
                            // otherwise, send back to checkout page and display error
                        } else {
                            userPath = "/checkout";
                            request.setAttribute("orderFailureFlag", true);
                        }
                    }
                }   break;
            default:
                break;
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (IOException | ServletException ex) {
        }
    }

}