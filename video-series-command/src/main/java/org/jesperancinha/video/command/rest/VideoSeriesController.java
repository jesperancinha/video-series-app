package org.jesperancinha.video.command.rest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.jesperancinha.video.command.commands.AddVideoSeriesCommand;
import org.jesperancinha.video.core.data.VideoSeriesDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video-series")
public class VideoSeriesController {

    private final CommandGateway commandGateway;

    public VideoSeriesController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public void postNewVideoSeries(
            @RequestBody
                    VideoSeriesDto videoSeriesDto) {
        commandGateway.send(
                AddVideoSeriesCommand.builder()
                        .name(videoSeriesDto.name())
                        .volumes(videoSeriesDto.volumes())
                        .genre(videoSeriesDto.genre())
                        .cashValue(videoSeriesDto.cashValue())
                        .build());
    }
}
