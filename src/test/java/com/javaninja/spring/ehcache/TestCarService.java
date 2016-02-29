package com.javaninja.spring.ehcache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author norris.shelton
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringContext.class)
public class TestCarService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CarService carService;

    private String vin;

    /**
     * Creates cars to look for.
     */
    @Before
    public void before() {
        Car car;
        for (int i = 0; i < 10; i++) {
            car = carService.createCar();
            assertNotNull(car);
            assertNotNull(car.getMake());
            assertNotNull(car.getModel());
            assertNotNull(car.getVin());

            // store the value of the last car
            vin = car.getVin();
        }
    }

    /**
     * Tests the get car method.
     * @throws Exception
     */
    @Test
    public void testGetCar() throws Exception {
        // the first time the get cars method is called, the cache is empty
        logger.info("beginning of first run");
        Car car = carService.getCar(vin);  // find the last car added
        assertNotNull(car);
        assertEquals(vin, car.getVin());
        logger.info("end of first run");

        // the second time the get cars method is called, the cache is fully populated
        logger.info("beginning of second run");
        car = carService.getCar(vin);  // find the last car added
        assertNotNull(car);
        assertEquals(vin, car.getVin());
        logger.info("end of first run");
    }
}