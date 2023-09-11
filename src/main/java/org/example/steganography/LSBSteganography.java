package org.example.steganography;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LSBSteganography implements SteganographyService {
    @Override
    public BufferedImage embedMessage(String message, BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        ArrayList<Integer> messageBits = getMessageBits(message);
        int numOfBits = messageBits.size();

        int pixelIndex = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (pixelIndex < messageBits.size() + 32) {
                    int pixel = image.getRGB(x, y);
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;

                    if (pixelIndex < 32) {
                        red = (red & 0xFE) | numOfBits >> (31 - pixelIndex);
                    } else {
                        int bitToHide = messageBits.get(pixelIndex - 32);
                        red = (red & 0xFE) | bitToHide;
                    }
                    image.setRGB(x, y, (red << 16) | (green << 8) | blue);
                    pixelIndex++;
                } else {
                    break; // Message embedded
                }
            }
        }
        return image;
    }

    @Override
    public String extractMessage(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        ArrayList<Integer> arrayList = new ArrayList<>();
        ArrayList<Integer> numOfBitsArray = new ArrayList<>();

        int pixelIndex = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int bitValue = (pixel >> 16) & 0x01;
                if (pixelIndex < 32) {
                    numOfBitsArray.add(bitValue);
                } else if (getNumOfBits(numOfBitsArray) + 32 > pixelIndex) {
                    arrayList.add(bitValue);
                }
                pixelIndex++;
            }
        }

        ArrayList<Byte> messageBytes = getMessageBytes(arrayList);

        return getExtractedMessage(messageBytes);
    }

    private String getExtractedMessage(ArrayList<Byte> messageBytes) {
        byte[] byteArray = new byte[messageBytes.size()];
        for (int i = 0; i < messageBytes.size(); i++) {
            byteArray[i] = messageBytes.get(i);
        }

        return new String(byteArray);
    }

    private ArrayList<Byte> getMessageBytes(ArrayList<Integer> arrayList) {

        ArrayList<Byte> messageBytes = new ArrayList<>();
        byte charByte = 0;
        int bitIndex = 7;

        for (int i : arrayList) {
            charByte |= (byte) ((i & 1) << bitIndex);
            bitIndex--;

            if (bitIndex < 0) {
                messageBytes.add(charByte);
                bitIndex = 7;
                charByte = 0;
            }
        }
        return messageBytes;
    }

    private ArrayList<Integer> getMessageBits(String message) {
        byte[] msgBytes = message.getBytes();
        ArrayList<Integer> arrayList = new ArrayList<>();

        for (byte b : msgBytes
        ) {
            for (int i = 7; i >= 0; i--) {
                int bitValue = (b >> i) & 0X01;
                arrayList.add(bitValue);
            }
        }
        return arrayList;
    }

    private int getNumOfBits(ArrayList<Integer> numOfCharsList) {
        int numOfChars = 0;
        int bitIndex = 31;
        for (int i : numOfCharsList) {
            numOfChars |= ((i & 1) << bitIndex);
            bitIndex--;
        }
        return numOfChars;
    }
}
