package org.tomat.cli.test;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;


/**
 * Created by Kiuby88 on 30/10/14.
 */
public class MainTest{


    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MainTest.class);
    }

    @Test
    public void test()
    {
        // simple command parsing example
        //git("translate", "file");

    }

    private void git(String... args)
    {
        //System.out.println("$ git " + Joiner.on(' ').join(args));
        //Main.main(args);
        //System.out.println();
    }
}