package org.jclouds.aliyun.v20150301.domain;

/**
 * Created by Administrator on 2015/4/23.
 */
import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.ConstructorProperties;
import java.util.Set;

import org.jclouds.javax.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ImmutableSet;

/**
 * Resource found in a paginated collection
 *
 * @see <a href=
"http://docs.openstack.org/api/openstack-compute/1.1/content/Paginated_Collections-d1e664.html"
/>
 */
public class Resource implements Comparable<Resource> {

    public static Builder<?> builder() {
        return new ConcreteBuilder();
    }

    public Builder<?> toBuilder() {
        return new ConcreteBuilder().fromResource(this);
    }

    public abstract static class Builder<T extends Builder<T>>  {
        protected abstract T self();

        protected String id;
        protected String name;
        protected Set<Link> links = ImmutableSet.of();

        /**
         * @see Resource#getId()
         */
        public T id(String id) {
            this.id = id;
            return self();
        }

        /**
         * @see Resource#getName()
         */
        public T name(String name) {
            this.name = name;
            return self();
        }

        /**
         * @see Resource#getLinks()
         */
        public T links(Set<Link> links) {
            this.links = ImmutableSet.copyOf(checkNotNull(links, "links"));
            return self();
        }

        public T links(Link... in) {
            return links(ImmutableSet.copyOf(in));
        }

        public Resource build() {
            return new Resource(id, name, links);
        }

        public T fromResource(Resource in) {
            return this
                    .id(in.getId())
                    .name(in.getName())
                    .links(in.getLinks());
        }
    }

    private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
        @Override
        protected ConcreteBuilder self() {
            return this;
        }
    }

    private final String id;
    private final String name;
    private final Set<Link> links;

    @ConstructorProperties({
            "id", "name", "links"
    })
    protected Resource(String id, @Nullable String name, @Nullable Set<Link> links) {
        this.id = checkNotNull(id, "id");
        this.name = name;
        this.links = links == null ? ImmutableSet.<Link>of() : ImmutableSet.copyOf(checkNotNull(links, "links"));
    }

    /**
     * When providing an ID, it is assumed that the resource exists in the current OpenStack
     * deployment
     *
     * @return the id of the resource in the current OpenStack deployment
     */
    public String getId() {
        return this.id;
    }

    /**
     * @return the name of the resource
     */
    @Nullable
    public String getName() {
        return this.name;
    }

    /**
     * @return the links of the id address allocated to the new server
     */
    public Set<Link> getLinks() {
        return this.links;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, links);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Resource that = Resource.class.cast(obj);
        return Objects.equal(this.id, that.id)
                && Objects.equal(this.name, that.name)
                && Objects.equal(this.links, that.links);
    }

    protected ToStringHelper string() {
        return Objects.toStringHelper(this)
                .add("id", id).add("name", name).add("links", links);
    }

    @Override
    public String toString() {
        return string().toString();
    }

    @Override
    public int compareTo(Resource that) {
        if (that == null)
            return 1;
        if (this == that)
            return 0;
        return this.getId().compareTo(that.getId());
    }

}