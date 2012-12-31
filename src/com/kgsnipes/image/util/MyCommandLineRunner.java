/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kgsnipes.image.util;

import com.google.javascript.jscomp.CommandLineRunner;
import com.google.javascript.jscomp.CompilerOptions;

/**
 *
 * @author kaushikganguly
 */
class MyCommandLineRunner extends CommandLineRunner {
    MyCommandLineRunner(String[] args) {
      super(args);
    }
 
     protected CompilerOptions createOptions() {
      CompilerOptions options = super.createOptions();
      //addMyCrazyCompilerPassThatOutputsAnExtraFile(options);
      return options;
   }
 
    public static void main(String[] args) {
      MyCommandLineRunner runner = new MyCommandLineRunner(args);
      if (runner.shouldRunCompiler()) {
        runner.run();
      } else {
       // System.exit(-1);
      }
    }
  }