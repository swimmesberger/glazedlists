/**
 * Glazed Lists
 * http://glazedlists.dev.java.net/
 *
 * COPYRIGHT 2003 O'DELL ENGINEERING LTD.
 */
package ca.odell.glazedlists.io;

import java.util.*;
import ca.odell.glazedlists.*;
import ca.odell.glazedlists.event.*;
import ca.odell.glazedlists.impl.io.*;
// NIO
import java.nio.*;
import java.nio.channels.*;
import java.io.*;
// for being a JUnit test case
import junit.framework.*;
import ca.odell.glazedlists.*;
// regular expressions
import java.util.regex.*;
import java.text.ParseException;
// logging
import java.util.logging.*;
import java.text.ParseException;

/**
 * Tests the ListEventCoder.
 *
 * @author <a href="mailto:jesse@swank.ca">Jesse Wilson</a>
 */
public class ListEventCoderTest extends TestCase {
    
    /** encodes java.lang.Integer */
    private ByteCoder intCoder = new IntegerCoder();

    /**
     * Prepare for the test.
     */
    public void setUp() {
    }

    /**
     * Clean up after the test.
     */
    public void tearDown() {
    }
    
    /**
     * Tests that a list event can be encoded and decoded.
     */
    public void testEncodeDecode() throws IOException {
        // prepare the encoding list
        EventList toEncode = new BasicEventList();
        EventEncoderListener encoder = new EventEncoderListener(intCoder);
        toEncode.addListEventListener(encoder);
        
        // prepare the decoding list
        EventList toDecode = new BasicEventList();
        
        // change, encode, decode
        toEncode.add(new Integer(8));
        Bufferlo add8Encoding = (Bufferlo)encoder.getEncodings().remove(0);
        ListEventCoder.bytesToListEvent(add8Encoding, toDecode, intCoder);
        assertEquals(toEncode, toDecode);
        
        // multichange, encode, decode
        List addAll = Arrays.asList(new Object[] { new Integer(6), new Integer(7), new Integer(5), new Integer(3), new Integer(0), new Integer(9) });
        toEncode.addAll(addAll);
        Bufferlo addAllEncoding = (Bufferlo)encoder.getEncodings().remove(0);
        ListEventCoder.bytesToListEvent(addAllEncoding, toDecode, intCoder);
        assertEquals(toEncode, toDecode);
    }

    /**
     * Tests that a snapshot can be decoded.
     */
    public void testSnapshotDecode() throws IOException {
        // prepare the encoding list
        EventList toEncode = new BasicEventList();
        
        // prepare the decoding list
        EventList toDecode = new BasicEventList();
        toDecode.add(new Integer(1));
        toDecode.add(new Integer(2));
        toDecode.add(new Integer(4));
        
        // change, encode, decode
        List entireList = Arrays.asList(new Object[] { new Integer(8), new Integer(6), new Integer(7), new Integer(5), new Integer(3), new Integer(0), new Integer(9) });
        toEncode.addAll(entireList);
        Bufferlo entireListEncoding = ListEventCoder.listToBytes(toEncode, intCoder);
        ListEventCoder.bytesToListEvent(entireListEncoding, toDecode, intCoder);
        assertEquals(toEncode, toDecode);
    }
}