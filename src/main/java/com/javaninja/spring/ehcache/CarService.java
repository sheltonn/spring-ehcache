package com.javaninja.spring.ehcache;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author norris.shelton
 */
@Service
public class CarService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private List<Car> cars = new LinkedList<>();

    /**
     * Creates a new car.
     * @return Car object with values populated.
     */
    public Car createCar() {
        Car car = new Car();
        car.setMake("Make-" + RandomStringUtils.randomAlphabetic(10));
        car.setModel("Model-" + RandomStringUtils.randomAlphabetic(10));
        car.setVin("Vin-" + RandomStringUtils.randomAlphanumeric(30));

        // Add to my list of all cars
        cars.add(car);
        logger.info("added car to list {}", car);

        return car;
    }

    @Cacheable(value = "cars")
    public Car getCar(String vin) {
        logger.info("inside ");
        Car car = null;
        for (Car car1 : cars) {
            logger.info("iterating over car");
            if (car1.getVin().equals(vin)) {
                car = car1;
                logger.info("found car {}", car1);
            }
        }
        return car;
    }
}
