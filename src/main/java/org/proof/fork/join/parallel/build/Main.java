package org.proof.fork.join.parallel.build;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Markelov Ruslan markelov@jet.msk.su
 */
public class Main {
    public static void main(String... args) {
        Build build = new Build(new HashSet<>(Arrays.asList(
                new Component("3", new HashSet<>(Arrays.asList("4fail", "5"))),
                new Component("4fail", new HashSet<>(Arrays.asList("5"))),
                new Component("5", new HashSet<>(Arrays.asList())),
                new Component("0", new HashSet<>(Arrays.asList("2", "3"))),
                new Component("1", new HashSet<>(Arrays.asList("2", "3"))),
                new Component("2", new HashSet<>(Arrays.asList("3")))
        )));
        System.out.println(build.execute(1));
    }
}
