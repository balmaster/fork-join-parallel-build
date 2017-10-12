package org.proof.fork.join.parallel.build;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * Markelov Ruslan markelov@jet.msk.su
 */
public class Build {

    private final Set<Component> components;
    private final Map<String, BuildTask> taskMap;


    public Build(Set<Component> components) {
        this.components = components;
        this.taskMap = components.stream()
                .collect(Collectors.toMap(o -> o.getId(), o -> new BuildTask(o)));
    }

    public BuildState execute(int parallelism) {
        final ForkJoinPool pool = new ForkJoinPool(parallelism);
        return pool.invoke(new RecursiveTask<BuildState>() {
            @Override
            protected BuildState compute() {
                taskMap.forEach((component, buildTask) -> pool.execute(buildTask));
                return taskMap.values().stream()
                        // wait all
                        .map(buildTask -> buildTask.join())
                        .anyMatch(buildState -> buildState == BuildState.FAIL) ? BuildState.FAIL : BuildState.SUCCESS;
            }
        });
    }

    /**
     * Markelov Ruslan markelov@jet.msk.su
     */
    public class BuildTask extends RecursiveTask<BuildState> {
        private final Component component;

        public BuildTask(Component component) {
            this.component = component;
        }

        protected BuildState compute() {
            System.out.println("begin build: " + component);
            boolean isFail = component.getDeps().stream()
                    // wait build all deps
                    .map(id -> taskMap.get(id).join())
                    .anyMatch(buildState -> buildState == BuildState.FAIL);

            if (isFail) {
                System.out.println("deps FAIL build: " + component);
                return BuildState.FAIL;
            } else {
                if (component.getId().contains("fail")) {
                    System.out.println("FAIL build: " + component);
                    return BuildState.FAIL;
                } else {

                    System.out.println("SUCCESS build: " + component);
                    return BuildState.SUCCESS;
                }
            }
        }
    }
}
