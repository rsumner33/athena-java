package com.athena.attacks;

import com.athena.utils.CounterList;
import com.athena.utils.FileUtils;
import com.athena.utils.HashManager;
import com.athena.utils.StringUtils;
import com.athena.utils.enums.CharSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Probabilistic extends Attack {
    private final String PROB_FILEPATH = "prob.txt";
    private CounterList<Byte> candidateElements;

    public Probabilistic(String hashes_filename, int hashType) {
        super.setHashType(hashType, hashes_filename);
        super.setHashman(new HashManager(hashes_filename));

        this.candidateElements = new CounterList<>();
    }

    @Override
    public void attack() {
        for (int i = 0; i < candidateElements.size(); i++) {
            if (!super.isAllCracked()) {
                super.checkAttempt(StringUtils.listToByteArray(candidateElements.get(i)));
            } else {
                return;
            }
        }
    }

    @Override
    public ArrayList<byte[]> getNextCandidates() {
        try {
            for (byte[] fileBuffer : FileUtils.getFileChunk(PROB_FILEPATH)) {
                for (byte[] candidate : StringUtils.formatFileBytes(fileBuffer)) {
                    parseCandidate(candidate);
                }
            }

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void parseCandidate(byte[] candidate) {
        boolean modifier = false;

        for (byte b : candidate) {
            switch (b) {
                case 63:
                    modifier = true;
                default:
                    if (modifier) {
                        switch (b) {
                            case 108:
                                candidateElements.add(CharSet.LOWER_ALPHABETIC.getCharsList());
                                modifier = false;
                                break;
                            case 100:
                                candidateElements.add(CharSet.NUMERIC.getCharsList());
                                modifier = false;
                                break;
                            case 115:
                                candidateElements.add(CharSet.SPECIAL.getCharsList());
                                modifier = false;
                                break;
                            case 117:
                                candidateElements.add(CharSet.UPPER_ALPHABETIC.getCharsList());
                                modifier = false;
                                break;
                            default:
                                break;
                        }
                    } else {
                        candidateElements.add(Collections.singletonList(b));
                    }
            }
        }
    }
}