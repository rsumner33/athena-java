/*
 * Copyright (C) 2017 Jack Green
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.athena;

import com.athena.attacks.Dictionary;
import com.athena.utils.FileUtils;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Athena {
    @Option(name = "-i", aliases = "--input", usage = "Input file to use")
    private static String hashFile_filename = "input.lst";
    @Option(name = "-d", aliases = "--dictionary-file", handler = StringArrayOptionHandler.class, usage = "Dictionary file to use")
    private static String[] wordlist_filename;
    @Option(name = "-m", aliases = "--mode", usage = "Attack mode to use")
    private static int mode;

    private static void parseArgs(String[] args) {
        try {
            CmdLineParser clp = new CmdLineParser(Athena.class);
            clp.parseArgument(args);

//            hashFile_length = FileUtils.getLineCount(hashFile_filename);
//            hashfile_bytes = FileUtils.getBytes(hashFile_filename);
//            uniques = FileUtils.getUniques(hashFile_filename);
//
//            modeName = Mode.getMode(mode).getModeName();
//
//            if (Mode.getMode(mode).requiresDict()) {
//                wordlist_length = FileUtils.getLineCount(wordlist_filename[0]);
//                wordlist_bytes = FileUtils.getBytes(wordlist_filename[0]);
//            }
//
//            if (Mode.getMode(mode).requiresDict2()) {
//                wordlist_length2 = FileUtils.getLineCount(wordlist_filename[1]);
//                wordlist_bytes2 = FileUtils.getBytes(wordlist_filename[1]);
//            }
        } catch (CmdLineException ex) {
            Logger.getLogger(Athena.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Athena.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Athena.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void initAttack() {
        switch (mode) {
            case 101:
                Dictionary dictionary = new Dictionary(wordlist_filename[0], hashFile_filename);
                dictionary.attack();
                break;

            default:
                break;
        }
    }

    public static void main(String[] args) {
        parseArgs(args);
        initAttack();
    }
}
