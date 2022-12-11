package org.jesperancinha.video.command.rest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.jesperancinha.video.command.aggregates.VideoSeriesAggregate;
import org.jesperancinha.video.command.commands.AddVideoSeriesCommand;
import org.jesperancinha.video.command.commands.VsaCommandGateway;
import org.jesperancinha.video.core.data.VideoSeriesDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video-series")
public class VideoSeriesController {

    private final VsaCommandGateway commandGateway;

    public VideoSeriesController(VsaCommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public void postNewVideoSeries(
            @RequestBody
                    VideoSeriesDto videoSeriesDto) throws Exception {
        commandGateway.sendCommand(
                AddVideoSeriesCommand.builder()
                        .name(videoSeriesDto.name())
                        .volumes(videoSeriesDto.volumes())
                        .genre(videoSeriesDto.genre())
                        .cashValue(videoSeriesDto.cashValue())
                        .build());
    }
}
