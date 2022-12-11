package org.jesperancinha.video.rest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.jesperancinha.video.command.AddVideoSeriesCommand;
import org.jesperancinha.video.data.VideoSeriesDto;
import org.jesperancinha.video.domain.VideoSeries;
import org.jesperancinha.video.repository.VsaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VsaController {

    private final CommandGateway commandGateway;

    private final VsaRepository vsaRepository;

    @Autowired
    public VsaController(CommandGateway commandGateway, VsaRepository vsaRepository) {
        this.commandGateway = commandGateway;
        this.vsaRepository = vsaRepository;
    }

    @PostMapping("/video/series")
    public ResponseEntity<Void> createVideoSeries(@RequestBody VideoSeriesDto videoSeriesDto) {
        AddVideoSeriesCommand command = new AddVideoSeriesCommand(
                videoSeriesDto.id().toString(),
                videoSeriesDto.name(),
                videoSeriesDto.volumes(),
                videoSeriesDto.cashValue(),
                videoSeriesDto.genre());
        commandGateway.send(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/video/history")
    public ResponseEntity<List<VideoSeries>> listAllVideoSeriesHistory() {
        return ResponseEntity.ok().body(vsaRepository.readAll());
    }
}
