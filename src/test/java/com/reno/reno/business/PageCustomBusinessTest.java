package com.reno.reno.business;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PageCustomBusinessTest {

    @InjectMocks
    private PageCustomBusiness pageCustomBusiness;

    @Test
    public void testGetPageNumber() {
        Integer expectedPageNumber = 99;
        Integer actual = pageCustomBusiness.getPageNumber(expectedPageNumber);
        assertEquals(Integer.valueOf(expectedPageNumber), actual);
    }

    @Test
    public void testGetPageNumberCasePageNumberLessThanDefault() {
        Integer actual = pageCustomBusiness.getPageNumber(-99);
        assertEquals(Integer.valueOf(0), actual);
    }
}
