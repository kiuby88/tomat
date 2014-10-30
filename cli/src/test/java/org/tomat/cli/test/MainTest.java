package org.tomat.cli.test;

import com.google.common.base.Joiner;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.tomat.cli.Main;


/**
 * Unit test for simple App.
 */
public class MainTest{


    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MainTest.class);
    }

    @Test
    public void test()
    {
        // simple command parsing example
        git("translate", "-i", "file");

    }

    private void git(String... args)
    {
        System.out.println("$ git " + Joiner.on(' ').join(args));
        Main.main(args);
        System.out.println();
    }
}