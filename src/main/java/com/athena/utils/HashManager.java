/* * Copyright (C) 2017 Jack Green * * This program is free software: you can redistribute it and/or modify 
* it under the terms of the GNU General Public License as published by * the Free Software Foundation, either version 3 of the License, or * (at your option) any later version. 
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

import java.util.ArrayList;
import java.util.HashMap;

import static com.athena.utils.StringUtils.byteArrayToHexString;

public class HashManager {    
	private HashMap<String, byte[]> hashes;
	private ArrayList<String> cracked;
	
	public HashManager(String hashes_filename) {
		hashes = new HashMap<>();
		cracked = new ArrayList<>();
		
		setHashes(hashes_filename);
	}
	
	private void setHashes(String hashes_filename) {
		ArrayList<byte[]> arr = StringUtils.formatFileBytes(FileUtils.getFileChunk(hashes_filename));
		for (byte[] b : arr) {
			hashes.put(byteArrayToHexString(b), b);
		}
	}
	
	public HashMap<String, byte[]> getHashes() {
		return hashes;
	}
	
	public ArrayList<String> getCracked() {
		return cracked;
	}
	
	public int getCrackedAmt() {
		return cracked.size();
	}
	
	public boolean hashExists(byte[] hash) {
		return hashes.containsValue(hash);
	}
	
	public void setCracked(String hash) {
		hashes.remove(hash);
		cracked.add(hash);
	}
}
