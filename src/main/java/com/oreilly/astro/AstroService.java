package com.oreilly.astro;

import com.oreilly.astro.json.Assignment;
import com.oreilly.astro.json.AstroResponse;

import java.util.Map;
import java.util.stream.Collectors;

public class AstroService {
    private final Gateway<AstroResponse> gateway;

    public AstroService(Gateway<AstroResponse> gateway) {
        this.gateway = gateway;
    }

    public Map<String, Long> getAstroData() {
        AstroResponse response = gateway.getResponse();
        return extractMap(response);
    }

    private Map<String, Long> extractMap(AstroResponse data) {
        return data.getPeople()
                .stream()
                .collect(Collectors.groupingBy(
                        Assignment::getCraft, Collectors.counting()));
    }
}
