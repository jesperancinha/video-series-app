package org.jesperancinha.video.rest

import org.axonframework.commandhandling.gateway.CommandGateway
import org.jesperancinha.video.command.AddVideoSeriesCommand
import org.jesperancinha.video.data.VideoSeriesDto
import org.jesperancinha.video.domain.VideoSeries
import org.jesperancinha.video.repository.VsaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class VsaController @Autowired constructor(
    private val commandGateway: CommandGateway,
    private val vsaRepository: VsaRepository
) {
    @PostMapping("/video/series")
    fun createVideoSeries(@RequestBody videoSeriesDto: VideoSeriesDto): ResponseEntity<Void> {
        val command = AddVideoSeriesCommand(
            videoSeriesDto.id.toString(),
            videoSeriesDto.name,
            videoSeriesDto.volumes,
            videoSeriesDto.cashValue,
            videoSeriesDto.genre
        )
        commandGateway.send<Any>(command)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/video/history")
    fun listAllVideoSeriesHistory(): ResponseEntity<List<VideoSeries?>?> {
        return ResponseEntity.ok().body(
            vsaRepository.readAll()
        )
    }
}