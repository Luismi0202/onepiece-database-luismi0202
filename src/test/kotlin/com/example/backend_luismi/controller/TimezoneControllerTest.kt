package com.example.backend_luismi.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(TimezoneController::class)
class TimezoneControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `test convert UK midnight to Spain time`() {
        mockMvc.perform(
            get("/api/timezone/convert-uk-to-spain")
                .param("time", "00:00")
                .param("date", "2025-12-19")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.ukTime").exists())
            .andExpect(jsonPath("$.spainTime").exists())
            .andExpect(jsonPath("$.timeDifference").exists())
    }

    @Test
    fun `test convert UK time without date parameter`() {
        mockMvc.perform(
            get("/api/timezone/convert-uk-to-spain")
                .param("time", "00:00")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.ukTime").exists())
            .andExpect(jsonPath("$.spainTime").exists())
            .andExpect(jsonPath("$.timeDifference").exists())
    }

    @Test
    fun `test convert with default midnight time`() {
        mockMvc.perform(
            get("/api/timezone/convert-uk-to-spain")
                .param("date", "2025-12-19")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.ukTime").exists())
            .andExpect(jsonPath("$.spainTime").exists())
    }
}
