/**
 * Copyright 2007-2015, Kaazing Corporation. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kaazing.k3po.driver.internal.behavior.handler.codec;

import static org.jboss.netty.buffer.ChannelBuffers.buffer;
import static org.jboss.netty.buffer.ChannelBuffers.wrappedBuffer;

import java.nio.ByteOrder;
import java.util.function.Supplier;

import javax.el.ValueExpression;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

public class WriteExpressionEncoder implements MessageEncoder {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(WriteExpressionEncoder.class);

    private final Supplier<byte[]> supplier;
    private final ValueExpression expression;

    public WriteExpressionEncoder(Supplier<byte[]> supplier, ValueExpression expression) {
        this.supplier = supplier;
        this.expression = expression;
    }

    @Override
    public ChannelBuffer encode(ByteOrder endian) {

        final byte[] value = supplier.get();
        final ChannelBuffer result;
        if (value != null) {
            result = wrappedBuffer(value);
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Value of expression is null. Encoding as a 0 length buffer");
            }
            result = buffer(0);
        }
        return result;
    }

    @Override
    public String toString() {
        return expression.getExpressionString();
    }

}
