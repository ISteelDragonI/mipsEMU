/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 or later.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.danielt3131.mipsemu.machine;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The mips emulator that will read a file written in MIPS and then execute those commands using virtual registers
 */
public class MipsMachine {

    //Register Variables
    private int hi, lo; //high and low of multiplication and division
    private int pc; //program counter

    private int[] register = new int[32];

    //Machine memory
    private byte[] memory;

    //labels
    private ArrayList<Label> labels;

    /**
     * Constructor for the mips emulator
     *
     * @param memorySize the amount of memory the machine will have in bytes
     */
    public MipsMachine(int memorySize){

        memory = new byte[memorySize];

    }

    /**
     * Reads in a file and puts the instructions into memory
     *
     * @param fileInputStream the input stream which contains the mips file
     */
    public void readFile(InputStream fileInputStream) throws FileNotFoundException {

        int tp = 0; //tp for text pointer : where to place word in text block of memory

        Scanner fileScanner = new Scanner(fileInputStream);

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            Scanner lineScanner = new Scanner(line);

            //Get where to start writing code
            lineScanner.useDelimiter(":");
            tp = lineScanner.nextInt(2);

            //System.out.println("Starting writing at memory " + tp);

            lineScanner.reset();

            //Get rest of the line
            String code = lineScanner.nextLine();

            //remove ':'
            code = code.substring(1);
            //System.out.println(code);
            //remove spaces
            while(code.contains(" "))
            {
                code = code.substring(0, code.indexOf(" ")) + code.substring(code.indexOf(" ") + 1);
                //System.out.println(code);
            }
            //look at next 8 characters and convert to byte and then put in memory
            while(code.length() != 0)
            {
                String part = code.substring(0,8);
                Log.d("MipsMachine.readFile Part", part);
                byte b = (byte)Integer.parseInt(part,2); //Byte.parseByte crashes due to signed bit so this is a workaround
                memory[tp] = b;
                code = code.substring(8);
                tp++;
                //System.out.printf("Writing %d at %d%n", b, tp - 1);
            }

        }
    }

    /**
     * has the machine read from the program counter to fetch and execute the next instruction
     */
    public void nextStep() {
        //todo have the machine read from the program counter to fetch and execute the next instruction
    }


}
