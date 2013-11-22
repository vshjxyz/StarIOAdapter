package com.uforge.plugins;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.starmicronics.stario.PortInfo;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;
import com.starmicronics.stario.StarPrinterStatus;
public class PrinterFunctions
{
    /**
     * This function checks the status of the printer
     * @param context - Activity for displaying messages to the user
     * @param portName - Port name to use for communication. This should be (TCP:<IPAddress>)
     * @param portSettings - Should be blank
     * @param sensorActiveHigh - boolean variable to tell the sensor active of CashDrawer which is High
     */
    public static StarPrinterStatus GetStatus(Context context, String portName, String portSettings, boolean sensorActiveHigh) throws StarIOPortException {
        StarIOPort port = null;
        StarPrinterStatus status = null;
        try {
            port = StarIOPort.getPort(portName, portSettings, 10000, context);

            try {
                Thread.sleep(500);
            }
            catch(InterruptedException e) {}

            status = port.retreiveStatus();
        }
        catch (StarIOPortException e) {
            // Bubbling the exception up
            throw e;
        }
        finally {
            if(port != null) {
                try {
                    StarIOPort.releasePort(port);
                } catch (StarIOPortException e) {}
            }
        }
        return status;
    }

    /**
     * @return the port name of the first paired bluetooth device
     */
    public static String getFirstBTPrinter() {
        String portName = "";
        List<PortInfo> BTPortList;
        try {
            BTPortList  = StarIOPort.searchPrinter("BT:");

            for (PortInfo portInfo : BTPortList) {
                portName = portInfo.getPortName();
            }
        } catch (StarIOPortException e) {
            e.printStackTrace();
        }
        return portName;
    }

    private static byte[] convertFromListByteArrayTobyteArray(List<Byte> ByteArray)
    {
        byte[] byteArray = new byte[ByteArray.size()];
        for(int index = 0; index < byteArray.length; index++)
        {
            byteArray[index] = ByteArray.get(index);
        }

        return byteArray;
    }

    public static void CopyArray(byte[] srcArray, Byte[] cpyArray) {
        for (int index = 0; index < cpyArray.length; index++) {
            cpyArray[index] = srcArray[index];
        }
    }

    public static void SendCommand(Context context, String portName, String portSettings, ArrayList<Byte> byteList) throws StarIOPortException {
        StarIOPort port = null;
        try {
            /*
                using StarIOPort3.1.jar (support USB Port)
                Android OS Version: upper 2.2
            */
            port = StarIOPort.getPort(portName, portSettings, 10000, context);
            /*
                using StarIOPort.jar
                Android OS Version: under 2.1
                port = StarIOPort.getPort(portName, portSettings, 10000);
            */
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) { }

            /*
               Using Begin / End Checked Block method
               When sending large amounts of raster data,
               adjust the value in the timeout in the "StarIOPort.getPort"
               in order to prevent "timeout" of the "endCheckedBlock method" while a printing.

               *If receipt print is success but timeout error occurs(Show message which is "There was no response of the printer within the timeout period."),
                 need to change value of timeout more longer in "StarIOPort.getPort" method. (e.g.) 10000 -> 30000
             */
            StarPrinterStatus status = port.retreiveStatus();
//          StarPrinterStatus status = port.beginCheckedBlock();

            if (status.offline) {
                throw new StarIOPortException("A printer is offline");
            }

            byte[] commandToSendToPrinter = convertFromListByteArrayTobyteArray(byteList);
            port.writePort(commandToSendToPrinter, 0, commandToSendToPrinter.length);

//          status = port.endCheckedBlock();

            if (status.coverOpen) {
                throw new StarIOPortException("Printer cover is open");
            }
            else if (status.receiptPaperEmpty) {
                throw new StarIOPortException("Receipt paper is empty");
            }
            else if (status.offline) {
                throw new StarIOPortException("Printer is offline");
            }
        }
        catch (StarIOPortException e) {
            // Bubbling the exception up
            throw e;
        }
        finally {
            if (port != null) {
                try {
                    StarIOPort.releasePort(port);
                }
                catch (StarIOPortException e) { }
            }
        }
    }
}
