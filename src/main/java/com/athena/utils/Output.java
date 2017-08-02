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

package com.athena.utils;

import com.athena.Mode;

public class Output {
    private final String VERSION = "2.0";
    private final StringBuilder stdout;
    private String status;
    private String hashfile_filename;
    private int hashFile_length;
    private int hashFile_bytes;
    private int hashFile_uniques;
    private String hashType;
    private int mode;

    public Output() {
        stdout = new StringBuilder();
    }

    public void printInit() {
        System.out.println("Starting AthenaHRT version " + VERSION);
    }

    public void printStatus(int recoveredAmt, int totalAmt) {
        System.out.println("\n" +
                "Status....: " + this.status +
                "Input.....: " + this.hashfile_filename + " (" + this.hashFile_length + " lines, " + this.hashFile_bytes + " bytes)" +
                "Hash Type.: " + this.hashType +
                "Mode......: " + Mode.getMode(this.mode).getModeName() +
                "Recovered.: " + recoveredAmt + "/" + totalAmt + " (" + (recoveredAmt / totalAmt) * 100 + ")\n");
    }

    public void printStatus(String status, int mode, String hashType, String hashfile_filename, int recoveredAmt, int totalAmt) {
        setStatus(status);
        setMode(mode);
        setHashType(hashType);
        setHashfile_filename(hashfile_filename);
        setHashFile_length(FileUtils.getLineCount(hashfile_filename));
        setHashFile_bytes(FileUtils.getBytes(hashfile_filename));
        setHashFile_uniques(FileUtils.getUniques(hashfile_filename));

        System.out.println("\n" +
                "Status....: " + this.status +
                "Input.....: " + this.hashfile_filename + " (" + this.hashFile_length + " lines, " + this.hashFile_bytes + " bytes)" +
                "Hash Type.: " + this.hashType +
                "Mode......: " + Mode.getMode(this.mode).getModeName() +
                "Recovered.: " + recoveredAmt + "/" + totalAmt + " (" + (recoveredAmt / totalAmt) * 100 + ")\n");
    }

    public void printCracked(String hash, String plaintext) {
        System.out.println(hash + ":" + plaintext);
    }

    private void setStatus(String status) {
        this.status = status;
    }

    private void setMode(int mode) {
        this.mode = mode;
    }

    private void setHashType(String hashType) {
        this.hashType = hashType;
    }

    private void setHashfile_filename(String hashfile_filename) {
        this.hashfile_filename = hashfile_filename;
    }

    private void setHashFile_length(int hashFile_length) {
        this.hashFile_length = hashFile_length;
    }

    private void setHashFile_bytes(int hashFile_bytes) {
        this.hashFile_bytes = hashFile_bytes;
    }

    private void setHashFile_uniques(int hashFile_uniques) {
        this.hashFile_uniques = hashFile_uniques;
    }
}