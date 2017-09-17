package com.athena.attacks;

import com.athena.utils.*;
import com.athena.utils.enums.CharSet;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Probabilistic extends Attack {
    private final File PROBFILE = new File("resources/prob.txt");
    private final File WORDFILE = new File("resources/words.txt");
    private final File NAMEFILE = new File("resources/names.txt");

    private ArrayList<byte[]> words;
    private ArrayList<byte[]> names;
    private ArrayList<byte[]> candidates;
    private CounterList<byte[]> candidateElements;

    private int currentIndex = 0;

    public Probabilistic(ArrayList<byte[]> hashes, int hashType) {
        super.setHashType(hashType, hashes);
        super.setHashman(new HashManager(hashes));
        super.initDigestInstance();

        this.candidateElements = new CounterList<>();
        this.candidates = new ArrayList<>();
        this.words = new ArrayList<>();
        this.names = new ArrayList<>();

        initElements();
        initCandidates();
    }

    @Override
    public void attack() {
        while (isMoreCandidates()) {
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
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void parseCandidate(byte[] candidate) {
        byte[] elements = Arrays.copyOfRange(candidate, 0, candidate.length - 2);
        int length = ArrayUtils.byteArrayToInt(Arrays.copyOfRange(candidate, candidate.length - 2, candidate.length));

        int staticChars = 0;
        for (byte b : elements) {
            if (b != (byte) 110 && b != (byte) 119) {
                staticChars++;
            }
        }
        int wordLength = length - staticChars;

        for (byte b : elements) {
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
                    candidateElements.add(names.stream().filter(n -> n.length == (wordLength)).collect(Collectors.toList()));
                    break;
                case 119:
                    candidateElements.add(words.stream().filter(w -> w.length == (wordLength)).collect(Collectors.toList()));
                    break;
                default:
                    break;
            }
        }
    }

    private void initCandidates() {
        try {
            for (byte[] fileBuffer : FileUtils.getFileChunk(PROBFILE)) {
                candidates.addAll(ArrayUtils.formatFileBytes(fileBuffer));
            }

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void initElements() {
        try {
            for (byte[] fileBuffer : FileUtils.getFileChunk(WORDFILE)) {
                words.addAll(ArrayUtils.formatFileBytes(fileBuffer));
            }
            for (byte[] fileBuffer : FileUtils.getFileChunk(NAMEFILE)) {
                names.addAll(ArrayUtils.formatFileBytes(fileBuffer));
            }

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}