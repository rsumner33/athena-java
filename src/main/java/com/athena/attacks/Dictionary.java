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

package com.athena.attacks;

import com.athena.Athena;
import com.athena.hashfamily.Hash;
import com.athena.utils.FileUtils;
import com.athena.utils.HashManager;
import com.athena.utils.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dictionary extends Attack {
    private String wordlist_filename;

    public Dictionary(String wordlist_filename, String hashes_filename, int hashType) {
        this.wordlist_filename = wordlist_filename;
        this.setHashType(hashType, hashes_filename);
        super.setHashman(new HashManager(hashes_filename));
    }

    public ArrayList<byte[]> getNextCandidates() {
        return FileUtils.getFileChunk(wordlist_filename);
    }

    private void setHashType(int hashType, String hashes_filename) {
        if (hashType == 0 || !Hash.hashTypeExists(hashType)) {
            try (BufferedReader br = new BufferedReader(new FileReader(hashes_filename))) {
                super.setHashType(Hash.getHashType(br.readLine()));
            } catch (IOException ex) {
                Logger.getLogger(Athena.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            super.setHashType(new ArrayList<>(Collections.singletonList(hashType)));
        }
    }
}