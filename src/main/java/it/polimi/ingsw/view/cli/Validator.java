package it.polimi.ingsw.view.cli;
import java.util.*;

/**
 * This class validate input from the client. In particular ip address and port.
 */
public class Validator {

    /**
     * Check if an ip address is valid.
     * @param address address to be validated.
     * @return {code @true} if it is ok, {code @false} otherwise.
     */
    public static boolean validateIpAddress(String address) {
        boolean result = false;
        StringTokenizer t = new StringTokenizer(address, ".");
        int a = Integer.parseInt(t.nextToken());
        int b = Integer.parseInt(t.nextToken());
        int c = Integer.parseInt(t.nextToken());
        int d = Integer.parseInt(t.nextToken());
        if ((a >= 0 && a <= 255) && (b >= 0 && b <= 255)
                && (c >= 0 && c <= 255) && (d >= 0 && d <= 255))
            result = true;
        return result;
    }

    /**
     * Check if a port is valid.
     * @param port port to be validated.
     * @return {code @true} if it is ok, {code @false} otherwise.
     */
    public static boolean validatePort(String port) {
        int portInt = Integer.parseInt(port);
        return portInt >= 1 && portInt <=65535;
    }
}
