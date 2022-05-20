package it.polimi.ingsw.view.cli;
import java.util.*;

public class Validator {

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

    public static boolean validatePort(String port) {
        int portInt = Integer.parseInt(port);
        return portInt >= 1 && portInt <=65535;
    }
}
