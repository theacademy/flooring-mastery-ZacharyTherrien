package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringController;
import com.sg.flooringmastery.dao.order.OrderDao;
import com.sg.flooringmastery.dao.product.ProductDao;
import com.sg.flooringmastery.dao.tax.TaxDao;
import com.sg.flooringmastery.service.FlooringServiceLayerImpl;
import com.sg.flooringmastery.ui.FlooringView;
import com.sg.flooringmastery.ui.UserIO;
import com.sg.flooringmastery.ui.UserIOConsoleImpl;

public class app {
    public static void main(String[] args) {
        System.out.println("main");
        // Create the user input
        UserIO io = new UserIOConsoleImpl();
        // Create view with user input
        FlooringView myView = new FlooringView(io);
        // Create the DAOs
        OrderDao orderDao = null;
        TaxDao taxDao = null;
        ProductDao productDao = null;
        // Create the service layer with the DAOs
        FlooringServiceLayerImpl myServiceLayer = new FlooringServiceLayerImpl(orderDao, taxDao, productDao);
        // Create the controller with the view and service layer
        FlooringController controller = new FlooringController(myView, myServiceLayer);
        // Finally run the program
        controller.run();
    }
}
