package de.fzi.cep.sepa.manager.execution.http;

import de.fzi.cep.sepa.manager.data.PipelineGraph;
import de.fzi.cep.sepa.manager.data.PipelineGraphBuilder;
import de.fzi.cep.sepa.manager.matching.InvocationGraphBuilder;
import de.fzi.cep.sepa.model.InvocableSEPAElement;
import de.fzi.cep.sepa.model.client.pipeline.Pipeline;
import de.fzi.cep.sepa.model.impl.graph.SecInvocation;
import de.fzi.cep.sepa.model.impl.graph.SepaInvocation;
import de.fzi.cep.sepa.storage.controller.StorageManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by riemer on 02.09.2016.
 */
public class PipelineStorageService {

    private Pipeline pipeline;

    public PipelineStorageService(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public void addPipeline() {
        PipelineGraph pipelineGraph = new PipelineGraphBuilder(pipeline).buildGraph();
        InvocationGraphBuilder builder = new InvocationGraphBuilder(pipelineGraph, pipeline.getPipelineId());
        List<InvocableSEPAElement> graphs = builder.buildGraphs();

        List<SecInvocation> secs = filter(graphs, SecInvocation.class);
        List<SepaInvocation> sepas = filter(graphs, SepaInvocation.class);

        pipeline.setSepas(sepas);
        pipeline.setActions(secs);

        StorageManager.INSTANCE.getPipelineStorageAPI().store(pipeline);
    }

    private <T> List<T> filter(List<InvocableSEPAElement> graphs, Class<T> clazz) {
        return graphs
                .stream()
                .filter(graph -> clazz.isInstance(graph))
                .map(graph -> clazz.cast(graph))
                .collect(Collectors.toList());
    }
}
