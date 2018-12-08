package factory;

import data_layer.domain.Teaching;

public class TeachingFactory {
    public static Teaching.TeachingBuilder generateTeachingBuilder() {
        return Teaching.builder();
    }

    public static Teaching generateTeaching() {
        return generateTeachingBuilder().build();
    }
}
