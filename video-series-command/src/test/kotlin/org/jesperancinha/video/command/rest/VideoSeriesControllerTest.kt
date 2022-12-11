package org.jesperancinha.video.command.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.WordSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.Called
import io.mockk.every
import io.mockk.slot
import io.mockk.verify
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.axonframework.eventhandling.tokenstore.TokenStore
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.jesperancinha.video.command.commands.AddVideoSeriesCommand
import org.jesperancinha.video.core.data.Genre
import org.jesperancinha.video.core.data.VideoSeriesDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = [VideoSeriesCommandInitializer::class])
@Import(TestConfig::class)
class VideoSeriesControllerTest(
    @Autowired
    private val mvc: MockMvc,
    @Autowired
    private val commandGateway: DefaultCommandGateway

    ) : WordSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    private val objectMapper = ObjectMapper()

    @MockkBean(relaxed = true)
    lateinit var tokenStore: TokenStore

    @MockkBean(relaxed = true)
    lateinit var eventStorageEngine: EventStorageEngine

    init {
        "should receive data and responde correctly" should {
            "should send a video title" {
//                every { commandGateway.send<VideoSeriesDto>(any<AddVideoSeriesCommand>()) }
//                val slotFilm = slot<AddVideoSeriesCommand>()
                val film = VideoSeriesDto.builder()
                    .id("1")
                    .name("Silence of the Lambs")
                    .cashValue(BigDecimal.valueOf(1_000_000))
                    .genre(Genre.HORROR)
                    .build()
                with(mvc) {
                    perform(
                        post("/video-series")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(film))
                    )
                        .andExpect(status().isOk)
                }

//                verify { commandGateway.send<VideoSeriesDto>(capture(slotFilm)) }
//                verify { tokenStore wasNot Called }
//                verify { eventStorageEngine wasNot Called }
//
//                val captured = slotFilm.captured
//                captured.shouldNotBeNull()
//                captured.name shouldBe film.name
//                captured.cashValue shouldBe film.cashValue
//                captured.genre shouldBe film.genre
            }
        }

    }
}

