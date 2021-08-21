package ru.otus.istyazhkina.constructor.service.impl;

import org.springframework.stereotype.Service;
import ru.morpher.ws3.Client;
import ru.morpher.ws3.ClientBuilder;
import ru.morpher.ws3.russian.RussianClient;

@Service
public class MorpherService {

    public static final Client morpher = new ClientBuilder()
            .useUrl("http://ws3.morpher.ru")
            .useToken("98ef42b3-cf71-479b-901d-92a8bc1a8036")
            .build();

    public RussianClient russianMorpher() {
        return morpher.russian();
    }
}
