package org.proof.fork.join.parallel.build;

import java.util.Set;

/**
 * Markelov Ruslan markelov@jet.msk.su
 */
public class Component {
    private final String id;
    private final Set<String> deps;

    public Component(String id, Set<String> deps) {
        this.id = id;
        this.deps = deps;
    }

    public String getId() {
        return id;
    }

    public Set<String> getDeps() {
        return deps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Component component = (Component) o;

        return id.equals(component.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Component{" +
                "id='" + id + '\'' +
                ", deps=" + deps +
                '}';
    }
}
