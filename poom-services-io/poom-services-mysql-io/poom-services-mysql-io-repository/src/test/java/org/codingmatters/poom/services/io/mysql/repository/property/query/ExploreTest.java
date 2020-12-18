package org.codingmatters.poom.services.io.mysql.repository.property.query;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.codingmatters.generated.ComplexValue;
import org.codingmatters.generated.json.ComplexValueWriter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.*;

public class ExploreTest {

    private final JsonFactory jsonFactory = new JsonFactory();

    @Test
    public void name() throws Exception {
        ComplexValue value = ComplexValue.builder()
                .dateProp(LocalDate.now())
                .timeProp(LocalTime.now())
                .datetimeProp(LocalDateTime.now())
                .tzdatetimeProp(ZonedDateTime.now(ZoneId.of("+2")))
                .build();
        System.out.println(this.json(value));

        /*
        "dateProp":"2020-12-17"
        "timeProp":"20:43:01.490445"
        "datetimeProp":"2020-12-17T20:43:01.490533"
        "tzdatetimeProp":"2020-12-17T20:43:01.491519+01:00"
         */
    }

    private String json(ComplexValue value) throws IOException {
        String json;
        try(ByteArrayOutputStream out = new ByteArrayOutputStream(); JsonGenerator generator = this.jsonFactory.createGenerator(out)) {
            new ComplexValueWriter().write(generator, value);
            generator.flush();
            json = out.toString();
        }
        return json;
    }
}
