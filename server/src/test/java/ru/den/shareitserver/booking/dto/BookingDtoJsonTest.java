
package ru.den.shareitserver.booking.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class BookingDtoJsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerializeAndDeserialize() throws Exception {
        BookingDto dto = BookingDto.builder()
                .id(10L)
                .start(LocalDateTime.of(2025, 1, 1, 12, 0))
                .end(LocalDateTime.of(2025, 1, 1, 13, 0))
                .item(ItemShortDto.builder().id(1L).name("Drill").build())
                .booker(UserShortDto.builder().id(2L).build())
                .status(ru.den.shareitserver.booking.model.BookingStatus.APPROVED)
                .build();

        String json = objectMapper.writeValueAsString(dto);
        BookingDto result = objectMapper.readValue(json, BookingDto.class);

        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getItem().getName()).isEqualTo("Drill");
        assertThat(result.getStatus()).isEqualTo(ru.den.shareitserver.booking.model.BookingStatus.APPROVED);
    }
}
