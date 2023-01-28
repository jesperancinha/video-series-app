package org.jesperancinha.video.command.rest

import org.axonframework.commandhandling.gateway.CommandGateway
import org.jesperancinha.video.command.commands.AddVideoSeriesCommand
import org.jesperancinha.video.core.data.VideoSeriesDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/video-series")
class VideoSeriesController @Autowired constructor (private val commandGateway: CommandGateway) {
    @PostMapping
    fun postNewVideoSeries(
        @RequestBody videoSeriesDto: VideoSeriesDto
    ) : ResponseEntity<Unit> = commandGateway.send<Any>(
            AddVideoSeriesCommand(
                id = videoSeriesDto.id,
                name = videoSeriesDto.name,
                volumes = videoSeriesDto.volumes,
                genre = videoSeriesDto.genre,
                cashValue = videoSeriesDto.cashValue
            )
        ).run { ResponseEntity.ok().build() }
}