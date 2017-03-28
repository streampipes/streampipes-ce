package de.fzi.cep.sepa.sources.kd2.streams;

import de.fzi.cep.sepa.model.impl.EventSchema;
import de.fzi.cep.sepa.model.impl.EventStream;
import de.fzi.cep.sepa.model.impl.eventproperty.EventProperty;
import de.fzi.cep.sepa.model.impl.graph.SepDescription;
import de.fzi.cep.sepa.sources.kd2.config.Kd2Variables;
import de.fzi.cep.sepa.sources.kd2.utils.BiodataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by riemer on 18.11.2016.
 */
public class EmotionalArousalStream extends AbstractBioDataStream {

    @Override
    public EventStream declareModel(SepDescription sep) {
        EventStream stream = prepareStream(Kd2Variables.EmotionalArousal.topic());

        EventSchema schema = new EventSchema();
        List<EventProperty> eventProperties = new ArrayList<EventProperty>();
        eventProperties.addAll(getPreparedProperties());
        eventProperties.add(BiodataUtils.getArousalProperty());

        schema.setEventProperties(eventProperties);
        stream.setEventSchema(schema);
        stream.setName(Kd2Variables.EmotionalArousal.eventName());
        stream.setDescription(Kd2Variables.EmotionalArousal.description());
        stream.setUri(sep.getUri() + "/arousal");

        return stream;
    }
}
