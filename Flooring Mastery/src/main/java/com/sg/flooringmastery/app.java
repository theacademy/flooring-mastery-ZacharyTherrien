package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringController;
import com.sg.flooringmastery.dao.order.OrderDao;
import com.sg.flooringmastery.dao.order.OrderDaoFileImpl;
import com.sg.flooringmastery.dao.product.ProductDao;
import com.sg.flooringmastery.dao.product.ProductDaoFileImpl;
import com.sg.flooringmastery.dao.tax.TaxDao;
import com.sg.flooringmastery.dao.tax.TaxDaoFileImpl;
import com.sg.flooringmastery.service.FlooringServiceLayerImpl;
import com.sg.flooringmastery.ui.FlooringView;
import com.sg.flooringmastery.ui.UserIO;
import com.sg.flooringmastery.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class app {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringController controller = ctx.getBean("controller", FlooringController.class);
        controller.run();
    }
}
