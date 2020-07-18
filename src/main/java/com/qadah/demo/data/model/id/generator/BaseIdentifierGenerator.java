package com.qadah.demo.data.model.id.generator;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

/**
 * A custom Id generator based on combination of long time hex string and UUID
 *
 * @author Ehab Qadah
 */
public class BaseIdentifierGenerator extends UUIDGenerator {

    private static final int NUMBER_OF_CHARS_IN_ID_PART = -5;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        // Generate a custom ID for the new entity
        final String uuid = super.generate(session, obj).toString();
        final long longTimeRandom = System.nanoTime() + System.currentTimeMillis() + new Random().nextLong() + Objects.hash(obj);
        final String timeHex = Long.toHexString(longTimeRandom);
        return StringUtils.substring(timeHex, NUMBER_OF_CHARS_IN_ID_PART) + StringUtils.substring(uuid, NUMBER_OF_CHARS_IN_ID_PART);
    }
}