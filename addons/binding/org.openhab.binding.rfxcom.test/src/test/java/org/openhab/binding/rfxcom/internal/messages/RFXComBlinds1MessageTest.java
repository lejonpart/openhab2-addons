/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.rfxcom.internal.messages;

import org.junit.Test;
import org.openhab.binding.rfxcom.internal.exceptions.RFXComException;
import org.openhab.binding.rfxcom.internal.exceptions.RFXComNotImpException;
import org.openhab.binding.rfxcom.internal.messages.RFXComBlinds1Message.Commands;
import org.openhab.binding.rfxcom.internal.messages.RFXComBlinds1Message.SubType;

import javax.xml.bind.DatatypeConverter;

import static org.junit.Assert.assertEquals;

/**
 * Test for RFXCom-binding
 *
 * @author Martin van Wingerden
 * @since 1.9.0
 */
public class RFXComBlinds1MessageTest {
    private void testMessage(String hexMsg, SubType subType, int seqNbr, String deviceId, int signalLevel,
            RFXComBlinds1Message.Commands command) throws RFXComException, RFXComNotImpException {
        final RFXComBlinds1Message msg = (RFXComBlinds1Message) RFXComMessageFactory
                .createMessage(DatatypeConverter.parseHexBinary(hexMsg));
        assertEquals("SubType", subType, msg.subType);
        assertEquals("Seq Number", seqNbr, (short) (msg.seqNbr & 0xFF));
        assertEquals("Sensor Id", deviceId, msg.getDeviceId());
        assertEquals("Command", command, msg.command);
        assertEquals("Signal Level", signalLevel, msg.signalLevel);

        byte[] decoded = msg.decodeMessage();

        assertEquals("Message converted back", hexMsg, DatatypeConverter.printHexBinary(decoded));
    }

    @Test
    public void testSomeMessages() throws RFXComException, RFXComNotImpException {
        testMessage("0919040600A21B010280", SubType.T4, 6, "41499.1", 8, Commands.STOP);

        testMessage("091905021A6280010000", SubType.T5, 2, "1729152.1", 0, Commands.OPEN);
    }
}
