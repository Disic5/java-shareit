
package ru.den.shareitserver.booking.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ItemShortDtoJsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerializeAndDeserialize() throws Exception {
        ItemShortDto dto = ItemShortDto.builder().id(5L).name("Bike").build();

        String json = objectMapper.writeValueAsString(dto);
        ItemShortDto result = objectMapper.readValue(json, ItemShortDto.class);

        assertThat(result.getId()).isEqualTo(5L);
        assertThat(result.getName()).isEqualTo("Bike");
    }
}
