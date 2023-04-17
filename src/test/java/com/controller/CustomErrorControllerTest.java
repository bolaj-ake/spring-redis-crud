package com.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CustomErrorController.class})
@ExtendWith(SpringExtension.class)
class CustomErrorControllerTest {
    @Autowired
    private CustomErrorController customErrorController;

    @Test
    void testConstructor() {
        assertEquals("/error", (new CustomErrorController()).getErrorPath());
    }

    @Test
    void testHandleError() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/error");
        MockMvcBuilders.standaloneSetup(this.customErrorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "<html><body><h2>Error Page</h2><div>Status code: <b>null</b></div><div>Exception Message: <b>N/A</b>"
                                        + "</div><body></html>"));
    }

    @Test
    void testHandleError2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/error", "");
        MockMvcBuilders.standaloneSetup(this.customErrorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "<html><body><h2>Error Page</h2><div>Status code: <b>null</b></div><div>Exception Message: <b>N/A</b>"
                                        + "</div><body></html>"));
    }
}

