package com.oreilly.assertj;

import com.oreilly.astro.AstroService;
import com.oreilly.astro.FakeGateway;
import com.oreilly.astro.json.AstroResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AstroTests {
    @Test
    void simpleAssertions() {
        AstroService service = new AstroService(new FakeGateway());
        Map<String, Long> astroData = service.getAstroData();
        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);
            assertThat(number).isNotNegative();
            assertThat(craft).isNotBlank();
        });
    }

    @Test
    void mapAssertions() {
        AstroService service = new AstroService(new FakeGateway());
        Map<String, Long> astroData = service.getAstroData();
        System.out.println(astroData);
        assertThat(astroData)
                .hasSize(5)
                .containsEntry("USS Voyager", 2L)
                .containsEntry("Jupiter 2", 1L)
                .containsEntry("Babylon 5", 1L)
                .containsEntry("Rocinante", 2L)
                .containsEntry("Nostromo", 1L);
    }

    @Test
    void moreMapAssertions() {
        AstroService service = new AstroService(new FakeGateway());
        Map<String, Long> astroData = service.getAstroData();
        assertThat(astroData)
                .containsExactlyInAnyOrderEntriesOf(
                        Map.ofEntries(
                                Map.entry("USS Voyager", 2L),
                                Map.entry("Jupiter 2", 1L),
                                Map.entry("Babylon 5", 1L),
                                Map.entry("Rocinante", 2L),
                                Map.entry("Nostromo", 1L)
                        ))
                .containsOnlyKeys("USS Voyager", "Jupiter 2", "Babylon 5", "Rocinante", "Nostromo")
                .allSatisfy((craft, number) -> {
                    assertThat(number).isNotNegative();
                    assertThat(craft).isNotBlank();
                })
                .anySatisfy((craft, number) -> assertThat(craft).contains("Voyager"));
    }

    @Test
    void listFilteredOn() {
        AstroService service = new AstroService(new FakeGateway());
        Map<String, Long> astroData = service.getAstroData();
        assertThat(astroData.keySet())
                .filteredOn(craft -> craft.contains("o"))
                .hasSize(4)
                .containsOnly("Babylon 5", "Nostromo", "USS Voyager", "Rocinante");
    }

    @Test
    void extracting() {
        AstroResponse response = new FakeGateway().getResponse();
        assertThat(response.getPeople())
                .extracting("craft")
                .containsOnly("USS Voyager", "Jupiter 2", "Babylon 5", "Rocinante", "Nostromo");
    }
}
