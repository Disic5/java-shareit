
package ru.den.shareitserver.booking.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserShortDtoJsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerializeAndDeserialize() throws Exception {
        UserShortDto dto = UserShortDto.builder().id(7L).build();

        String json = objectMapper.writeValueAsString(dto);
        UserShortDto result = objectMapper.readValue(json, UserShortDto.class);

        assertThat(result.getId()).isEqualTo(7L);
    }
}
