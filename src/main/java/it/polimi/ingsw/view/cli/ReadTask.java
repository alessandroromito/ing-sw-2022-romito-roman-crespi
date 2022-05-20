package it.polimi.ingsw.view.cli;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class ReadTask implements Callable<String> {

    private final BufferedReader br;

    public ReadTask() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String call() throws Exception {
        while(!br.ready()) {
            Thread.sleep(250);
        }
        return br.readLine();
    }
}
