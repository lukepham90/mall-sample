package com.uuhnaut69.common.test;

import com.uuhnaut69.common.constant.AppConstant;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author uuhnaut
 * @project mall
 */
public class AppConstantTest {

    @Test
    public void testSystemConstantId() {
        UUID systemId = AppConstant.SYSTEM;
        assertThat(systemId).isNotNull();
    }
}
