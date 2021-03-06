/* Glazed Lists                                                 (c) 2003-2006 */
/* http://publicobject.com/glazedlists/                      publicobject.com,*/
/*                                                     O'Dell Engineering Ltd.*/
package ca.odell.glazedlists.matchers;

import java.util.function.Predicate;

/**
 * Determines which values should be filtered.
 *
 * <p>For best safety, implementations of {@link Matcher} should be
 * <a href="http://en.wikipedia.org/wiki/Immutable_object">immutable</a>. This
 * guarantees that {@link ca.odell.glazedlists.FilterList}s can safely call
 * {@link #matches(Object) matches()} without synchronization.
 *
 * <p>As of Glazed Lists 1.12 Matcher was adapted to extend the {@link Predicate} interface.
 * This way you can use an existing Matcher everywhere a predicate is expected.
 *  
 * <p>In order to create dynamic filtering, use a
 * {@link ca.odell.glazedlists.matchers.MatcherEditor}, which
 * can create immutable {@link Matcher} Objects each time the matching constraints
 * change.
 *
 * @author <a href="mailto:rob@starlight-systems.com">Rob Eden</a>
 * @see ca.odell.glazedlists.FilterList
 * @see ca.odell.glazedlists.matchers.MatcherEditor
 */
@FunctionalInterface
public interface Matcher<E> extends Predicate<E> {

    /**
     * Return true if an item matches a filter.
     *
     * @param item The item possibly being filtered.
     */
    public boolean matches(E item);
    
    @Override
    default boolean test(E item) {
        return matches(item);
    }
}