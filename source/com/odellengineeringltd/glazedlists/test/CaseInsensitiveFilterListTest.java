/**
 * Glazed Lists
 * http://glazedlists.dev.java.net/
 *
 * COPYRIGHT 2003 O'DELL ENGINEERING LTD.
 */
package com.odellengineeringltd.glazedlists.test;

// for being a JUnit test case
import junit.framework.*;
// the core Glazed Lists package
import com.odellengineeringltd.glazedlists.*;
// standard collections
import java.util.*;

/**
 * This test verifies that the CaseInsensitiveFilterList works.
 *
 * @author <a href="mailto:jesse@odel.on.ca">Jesse Wilson</a>
 */
public class CaseInsensitiveFilterListTest extends TestCase {

    /** the source list */
    private BasicEventList unfilteredList = null;
    
    /** the filtered list */
    private CaseInsensitiveFilterList filteredList = null;
    
    /** for randomly choosing list indicies */
    private Random random = new Random();
    
    /**
     * Prepare for the test.
     */
    public void setUp() {
        unfilteredList = new BasicEventList();
        filteredList = new CaseInsensitiveFilterList(unfilteredList, new StringFilterator());
    }

    /**
     * Clean up after the test.
     */
    public void tearDown() {
        unfilteredList = null;
        filteredList = null;
    }

    /**
     * Test to verify that the filter is working correctly when values 
     * are being added to a list.
     */
    public void testFilterBeforeAndAfter() {
        // apply a filter
        String filter = "7";
        filteredList.getFilterEdit().setText(filter);
        
        // populate a list with strings
        for(int i = 1000; i < 2000; i++) {
            unfilteredList.add("" + i);
        }
        
        // build a control list of the desired results
        ArrayList controlList = new ArrayList();
        for(Iterator i = unfilteredList.iterator(); i.hasNext(); ) {
            String element = (String)i.next();
            if(element.indexOf(filter) != -1) controlList.add(element);
        }
        
        // verify the lists are equal
        assertEquals(controlList, filteredList);
        
        // destroy the filter
        filteredList.getFilterEdit().setText("");
        assertEquals(filteredList, unfilteredList);
        
        // apply the filter again and verify the lists are equal
        filteredList.getFilterEdit().setText(filter);
        assertEquals(controlList, filteredList);
    }

    /**
     * Test to verify that the filter is working correctly when the list
     * is changing by adds, removes and deletes.
     */
    public void testFilterDynamic() {
        // apply a filter
        String filter = "5";
        filteredList.getFilterEdit().setText(filter);
        
        // apply various operations to a list of strings
        for(int i = 0; i < 4000; i++) {
            int operation = random.nextInt(4);
            int value = random.nextInt(10);
            int index = unfilteredList.isEmpty() ? 0 : random.nextInt(unfilteredList.size());
            
            if(operation <= 1 || unfilteredList.isEmpty()) {
                unfilteredList.add(index, "" + value);
            } else if(operation == 2) {
                unfilteredList.remove(index);
            } else if(operation == 3) {
                unfilteredList.set(index, "" + value);
            }
        }
        
        // build a control list of the desired results
        ArrayList controlList = new ArrayList();
        for(Iterator i = unfilteredList.iterator(); i.hasNext(); ) {
            String element = (String)i.next();
            if(element.indexOf(filter) != -1) controlList.add(element);
        }
        
        // verify the lists are equal
        assertEquals(controlList, filteredList);
    }

    /**
     * A filterator for strings.
     */
    class StringFilterator implements Filterator {
        public String[] getFilterStrings(Object element) {
            return new String[] { (String)element };
        }
    }
}
