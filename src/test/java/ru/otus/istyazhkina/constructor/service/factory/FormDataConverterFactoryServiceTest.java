package ru.otus.istyazhkina.constructor.service.factory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.istyazhkina.constructor.service.FormDataConverter;
import ru.otus.istyazhkina.constructor.service.impl.converters.OrderToRaiseWagesDataConverter;
import ru.otus.istyazhkina.constructor.service.impl.converters.PowerOfAttorneyDataConverter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class FormDataConverterFactoryServiceTest {

    @Autowired
    private FormDataConverterFactoryService formDataConverterFactoryService;

    @Autowired
    private PowerOfAttorneyDataConverter powerOfAttorneyDataConverter;

    @Autowired
    private OrderToRaiseWagesDataConverter orderToRaiseWagesDataConverter;

    @Test
    void shouldReturnConverterByTemplateId() {
        FormDataConverter converterByTemplateId = formDataConverterFactoryService.getConverterByTemplateId("1");
        assertThat(converterByTemplateId).isInstanceOf(PowerOfAttorneyDataConverter.class);
    }

}