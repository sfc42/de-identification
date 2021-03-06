/*
 * (C) Copyright IBM Corp. 2020
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.ibm.whc.deid.shared.pojo.config.masking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import com.ibm.whc.deid.shared.util.InvalidMaskingConfigurationException;
import org.junit.Test;

public class BinningMaskingProviderConfigTest {

  @Test
  public void testValidate() throws Exception {
    BinningMaskingProviderConfig config = new BinningMaskingProviderConfig();
    config.validate();

    config.setUnspecifiedValueHandling(-1);
    try {
      config.validate();
      fail("expected exception");
    } catch (InvalidMaskingConfigurationException e) {
      assertEquals("`unspecifiedValueHandling` must be [0..3]", e.getMessage());
    }
    config.setUnspecifiedValueHandling(2);
    config.validate();

    config.setBinSize(0);
    try {
      config.validate();
      fail("expected exception");
    } catch (InvalidMaskingConfigurationException e) {
      assertEquals("`binSize` must be greater than 0", e.getMessage()); 
    }

    config.setBinSize(1);
    config.validate();

    config.setBinSize(1000000);
    config.validate();

    config.setBinSize(-1);
    try {
      config.validate();
      fail("expected exception");
    } catch (InvalidMaskingConfigurationException e) {
      assertEquals("`binSize` must be greater than 0", e.getMessage());
    }
  }

  @Test
  public void testSetFormat() {
    BinningMaskingProviderConfig config = new BinningMaskingProviderConfig();
    config.setFormat("abc");
    assertEquals("abc", config.getFormat());
    config.setFormat(null);
    assertEquals(BinningMaskingProviderConfig.DEFAULT_FORMAT, config.getFormat());
  }

  @SuppressWarnings("unlikely-arg-type")
  @Test
  public void testEqualsHashCode() {
    BinningMaskingProviderConfig config = new BinningMaskingProviderConfig();
    assertFalse(config.equals(null));
    assertFalse(config.equals("test"));
    assertFalse(config.equals(new AddressMaskingProviderConfig()));
    assertTrue(config.equals(config));
    // multiple calls, same value
    assertEquals(config.hashCode(), config.hashCode());

    BinningMaskingProviderConfig other = new BinningMaskingProviderConfig();
    assertTrue(config.equals(other));
    assertEquals(config.hashCode(), other.hashCode());

    config.setUnspecifiedValueHandling(2);
    assertFalse(config.equals(other));
    assertNotEquals(config.hashCode(), other.hashCode());
    other.setUnspecifiedValueHandling(2);
    assertTrue(config.equals(other));
    assertEquals(config.hashCode(), other.hashCode());

    config.setUnspecifiedValueReturnMessage("x");
    assertFalse(config.equals(other));
    assertNotEquals(config.hashCode(), other.hashCode());
    other.setUnspecifiedValueReturnMessage("x");
    assertTrue(config.equals(other));
    assertEquals(config.hashCode(), other.hashCode());

    config.setBinSize(27);
    assertFalse(config.equals(other));
    assertNotEquals(config.hashCode(), other.hashCode());
    other.setBinSize(27);
    assertTrue(config.equals(other));
    assertEquals(config.hashCode(), other.hashCode());

    config.setFormat("%s");
    assertFalse(config.equals(other));
    assertNotEquals(config.hashCode(), other.hashCode());
    other.setFormat("%s");
    assertTrue(config.equals(other));
    assertEquals(config.hashCode(), other.hashCode());
    config.setFormat(null);
    assertFalse(config.equals(other));
    assertNotEquals(config.hashCode(), other.hashCode());
    other.setFormat(null);
    assertTrue(config.equals(other));
    assertEquals(config.hashCode(), other.hashCode());

    config.setStartValue(2);
    assertFalse(config.equals(other));
    assertNotEquals(config.hashCode(), other.hashCode());
    other.setStartValue(2);
    assertTrue(config.equals(other));
    assertEquals(config.hashCode(), other.hashCode());

    config.setUseStartValue(!config.isUseStartValue());
    assertFalse(config.equals(other));
    assertNotEquals(config.hashCode(), other.hashCode());
    other.setUseStartValue(!other.isUseStartValue());
    assertTrue(config.equals(other));
    assertEquals(config.hashCode(), other.hashCode());
  }

}
