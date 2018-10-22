package com.kostenko.coding;

import java.util.ArrayList;

/**
 * Created by Alexander on 04.04.2015.
 */
public class Encoder {
  private final int BIT_IN_BYTE = 7;
  private final Byte[][] CODES_4B = { { 0, 0, 0, 0 }, { 0, 0, 0, 1 }, { 0, 0, 1, 0 }, { 0, 0, 1, 1 }, { 0, 1, 0, 0 }, { 0, 1, 0, 1 }, { 0, 1, 1, 0 }, { 0, 1, 1, 1 }, { 1, 0, 0, 0 }, { 1, 0, 1, 0 },
      { 1, 0, 1, 1 }, { 1, 1, 0, 0 }, { 1, 1, 0, 1 }, { 1, 1, 1, 0 }, { 1, 1, 1, 0 }, { 1, 1, 1, 1 } };
  private final Byte[][] CODES_5B = { { 1, 1, 1, 1, 0 }, { 0, 1, 0, 0, 1 }, { 1, 0, 1, 0, 0 }, { 1, 0, 1, 0, 1 }, { 0, 1, 0, 1, 0 }, { 0, 1, 0, 1, 1 }, { 0, 1, 1, 1, 0 }, { 0, 1, 1, 1, 1 },
      { 1, 0, 0, 1, 0 }, { 1, 0, 0, 1, 1 }, { 1, 0, 1, 1, 0 }, { 1, 0, 1, 1, 1 }, { 1, 1, 0, 1, 0 }, { 1, 1, 0, 1, 1 }, { 1, 1, 1, 0, 0 }, { 1, 1, 1, 0, 1 } };
  private Byte[] binaryFormOfSequence;

  public Encoder(Integer[] array) {
    binaryFormOfSequence = convertDecimalArrayToBinary(array);
  }

  public Byte[] get4Bto5BArray() {
    ArrayList<Byte[]> originSequenceByArrays = getListOfArraysBySize(binaryFormOfSequence, 4);
    ArrayList<Byte> result = new ArrayList<>();
    for (Byte[] array : originSequenceByArrays) {
      for (int i = 0; i < CODES_4B.length; i++) {
        if (compareArrays(array, CODES_4B[i])) {
          for (int j = 0; j < CODES_5B[i].length; j++) {
            result.add(CODES_5B[i][j]);
          }
          break;
        }
      }
    }
    return result.toArray(new Byte[result.size()]);
  }

  public Byte[] getOriginBinarySequence() {
    return binaryFormOfSequence;
  }

  public Byte[] getMLT3Array() {
    boolean toggleByOne = false;
    ArrayList<Byte> result = new ArrayList<>();
    for (Byte element : binaryFormOfSequence) {
      if (element == 0) result.add((byte)0);
      else if (element == 1 && toggleByOne) {
        toggleByOne = false;
        result.add((byte)-1);
      } else {
        toggleByOne = true;
        result.add((byte)1);
      }
    }
    return result.toArray(new Byte[result.size()]);
  }

  private Byte[] convertDecimalArrayToBinary(Integer[] array) {
    ArrayList<Byte> binaryArray = new ArrayList<>();
    for (Integer number : array) {
      binaryArray.addAll(convertDecimalToBinary(number));
    }
    return binaryArray.toArray(new Byte[binaryArray.size()]);
  }

  private ArrayList<Byte> convertDecimalToBinary(int digit) {
    ArrayList<Byte> result = new ArrayList<>();
    for (int i = BIT_IN_BYTE; i >= 0; i--) {
      int res = digit >> i;
      result.add((byte)(res & 1));
    }
    return result;
  }

  private ArrayList<Byte[]> getListOfArraysBySize(Byte[] source, int size) {
    ArrayList<Byte[]> result = new ArrayList<>();
    for (int i = 0; i < source.length; ) {
      Byte[] tmp = new Byte[size];
      for (int j = 0; j < size && i < source.length; j++) {
        tmp[j] = source[i++];
      }
      result.add(tmp);
    }
    return result;
  }

  private boolean compareArrays(Byte[] left, Byte[] right) {
    if (left.length != right.length) throw new IllegalArgumentException("Sizes of arrays is different");
    for (int i = 0; i < left.length; i++) {
      if (left[i] != right[i]) return false;
    }
    return true;
  }

}
