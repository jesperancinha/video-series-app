package org.jesperancinha.video.command.commands;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@EqualsAndHashCode
@ToString
public class ConfirmSeriesCommand {

    @TargetAggregateIdentifier
    private Long id;
}
