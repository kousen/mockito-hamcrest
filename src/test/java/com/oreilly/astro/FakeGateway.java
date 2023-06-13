package com.oreilly.astro;

import com.oreilly.astro.json.Assignment;
import com.oreilly.astro.json.AstroResponse;

import java.util.Arrays;

public class FakeGateway implements Gateway<AstroResponse> {
    @Override
    public AstroResponse getResponse() {
        return new AstroResponse(7, "Success",
                Arrays.asList(new Assignment("Kathryn Janeway", "USS Voyager"),
                        new Assignment("Seven of Nine", "USS Voyager"),
                        new Assignment("Will Robinson", "Jupiter 2"),
                        new Assignment("Lennier", "Babylon 5"),
                        new Assignment("James Holden", "Rocinante"),
                        new Assignment("Naomi Negata", "Rocinante"),
                        new Assignment("Ellen Ripley", "Nostromo")));
    }
}
