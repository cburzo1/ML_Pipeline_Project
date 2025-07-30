package com.chrisweatherproject.weatherproject.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class raw_data_service_test {

        @Mock
        private raw_data_repo rdr;

        @InjectMocks
        private raw_data_service_impl rds;

        private ObjectMapper mapper = new ObjectMapper(); // real one if you want to verify JSON

        @Test
        void test_add_raw_data() throws Exception {
            // Arrange
            raw_data data = new raw_data();
            data.setEmployee_name("Alice");
            data.setSalary(50000.00F);
            data.setPosition("Engineer");
            data.setYears_of_exp(3);

            ArrayList<raw_data> inputList = new ArrayList<>();
            inputList.add(data);

            // Act
            rds.add_raw_data(inputList);

            // Assert
            verify(rdr, times(1)).save(data);
        }
}
