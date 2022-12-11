package org.jesperancinha.video.command;

import lombok.Data;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.jesperancinha.video.data.Genre;

import java.math.BigDecimal;

@Value
@Data
public class AddVideoSeriesCommand {

	@TargetAggregateIdentifier
	String id;

	String name;

	Integer volumes;

	BigDecimal cashValue;

	Genre genre;

}

