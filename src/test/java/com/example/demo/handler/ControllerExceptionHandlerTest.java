package com.example.demo.handler;

import com.example.demo.controller.ItemController;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@WebMvcTest(ControllerExceptionHandler.class)
public class ControllerExceptionHandlerTest {
    @Autowired
    private ControllerExceptionHandler controllerExceptionHandler;

    @Test
    public void handleValidationError1_returnTrue() {

    }

    @Test
    public void handleValidationError2_returnTrue() {

    }

}
