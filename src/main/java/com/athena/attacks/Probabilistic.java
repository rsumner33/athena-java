package com.athena.attacks;

import com.athena.utils.*;
import com.athena.utils.enums.CharSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Probabilistic extends Attack {
    private final String PROB_FILEPATH = "resources/prob.txt";
    private final String WORD_FILEPATH = "resources/words.txt";
    private final String NAME_FILEPATH = "resources/names.txt";

    private ArrayList<byte[]> words;
    private ArrayList<byte[]> names;
    private ArrayList<byte[]> candidates;
    private CounterList<byte[]> candidateElements;

    private int currentIndex = 0;

    public Probabilistic(String hashes_filename, int hashType) {
        super.setHashType(hashType, hashes_filename);
        super.setHashman(new HashManager(hashes_filename));
        super.initDigestInstance();

        this.candidateElements = new CounterList<>();
        this.candidates = new ArrayList<>();
        this.words = new ArrayList<>();
        this.names = new ArrayList<>();

        //System.out.println("Prob constructor");
        initElements();
        initCandidates();
    }

    @Override
    public void attack() {
        while (isMoreCandidates()) {
            /*System.out.println("cand - curr index: " + new String(candidates.get(currentIndex - 1)));
            System.out.println("ele - first: " + new String(ArrayUtils.stripList(candidateElements.get(0))));
            System.out.println("ele - size: " + candidateElements.size());*/
            for (int i = 0; i < candidateElements.size(); i++) {
                if (!super.isAllCracked()) {
                    super.checkAttempt(ArrayUtils.stripList(candidateElements.get(i)));
                } else {
                    return;
                }
            }
        }
    }

    public boolean isMoreCandidates() {
        try {
            if (currentIndex < candidates.size()) {
                candidateElements.clear();
                parseCandidate(candidates.get(currentIndex));
                currentIndex++;
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void parseCandidate(byte[] candidate) {
        //System.out.println(StringUtils.byteArrayToString(names.get(0)));
        for (byte b : candidate) {
            switch (b) {
                case 108:
                    candidateElements.add(CharSet.LOWER_ALPHABETIC.getCharsList());
                    break;
                case 100:
                    candidateElements.add(CharSet.NUMERIC.getCharsList());
                    break;
                case 115:
                    candidateElements.add(CharSet.SPECIAL.getCharsList());
                    break;
                case 117:
                    candidateElements.add(CharSet.UPPER_ALPHABETIC.getCharsList());
                    break;
                case 110:
                    candidateElements.add(names);
                    break;
                case 119:
                    candidateElements.add(words);
                    break;
                default:
                    break;
            }
        }
    }

    private void initCandidates() {
        try {
            for (byte[] fileBuffer : FileUtils.getFileChunk(PROB_FILEPATH)) {
                candidates.addAll(ArrayUtils.formatFileBytes(fileBuffer));
            }

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void initElements() {
        try {
            for (byte[] fileBuffer : FileUtils.getFileChunk(WORD_FILEPATH)) {
                words.addAll(ArrayUtils.formatFileBytes(fileBuffer));
            }
            for (byte[] fileBuffer : FileUtils.getFileChunk(NAME_FILEPATH)) {
                names.addAll(ArrayUtils.formatFileBytes(fileBuffer));
            }

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}