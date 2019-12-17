package org.jesperancinha.video.command.rest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.jesperancinha.video.command.commands.AddSeriesCommand;
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
    public void postNewVideoSeries(@RequestBody VideoSeriesDto videoSeriesDto) {
        commandGateway.send(
                AddSeriesCommand.builder()
                        .name(videoSeriesDto.getName())
                        .volumes(videoSeriesDto.getVolumes())
                        .genre(videoSeriesDto.getGenre())
                        .cashValue(videoSeriesDto.getCashValue())
                        .build());
    }
}
